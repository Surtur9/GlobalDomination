import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.geometry.Bounds;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.scene.text.Font;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ventanaJuego {
    private Juego juego;
    private Pane layout;
    private Map<String, String> pathToCountryMap;
    private Text turnoActualText;
    private Pane infoColoresPane;

    // Constructor que acepta un objeto Juego
    public ventanaJuego(Juego juego) {
        this.juego = juego;
        this.layout = crearLayout();
        otorgarPuntosJugadorActual(); // Otorgar puntos al jugador inicial
        actualizarInfoColores();
        mostrarBannerTurno(); // Mostrar el banner del turno inicial al crear la ventana
    }

    public Pane getLayout() {
        return layout;
    }

    private Pane crearLayout() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #808080; -fx-padding: 0;"); // Establecer el fondo gris y sin relleno

        inicializarPathToCountryMap(); // Inicializar el mapeo de path IDs a nombres de países
        List<SVGPath> svgPaths = cargarSVGPaths("/resources/mapaMundi.svg"); // Crear y cargar todos los SVGPath
        if (svgPaths != null) {
            for (SVGPath svgPath : svgPaths) {
                String countryName = pathToCountryMap.get(svgPath.getId());
                svgPath.setScaleX(1.0); // Escalado en X
                svgPath.setScaleY(1.0); // Escalado en Y
                svgPath.setLayoutX(200); // Desplazar a la derecha
                svgPath.setLayoutY(150); // Desplazar hacia abajo
                if (countryName != null) {
                    Pais pais = buscarPais(countryName);
                    if (pais != null) {
                        Jugador jugador = juego.getJugadorDePais(pais);
                        if (jugador != null) {
                            int jugadorIndex = juego.getJugadores().indexOf(jugador);
                            switch (jugadorIndex) {
                                case 0 -> svgPath.setFill(Color.LIGHTCORAL); // Jugador 1: Rojo
                                case 1 -> svgPath.setFill(Color.LIGHTBLUE);  // Jugador 2: Azul
                                case 2 -> svgPath.setFill(Color.LIGHTGREEN); // Jugador 3: Verde
                                case 3 -> svgPath.setFill(Color.PLUM);       // Jugador 4: Morado
                            }
                        }
                    }
                } else {
                    svgPath.setFill(Color.LIGHTGRAY); // Establecer el color de relleno predeterminado
                }
                svgPath.setStroke(Color.BLACK);   // Establecer el color del borde

                // Añadir evento de clic para cambiar el color a rojo y mostrar panel de control
                svgPath.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    System.out.println(svgPath.getId());
                    if (countryName != null) {
                        Pais pais = buscarPais(countryName);
                        Jugador jugadorActual = juego.getJugadorActual();
                        if (pais != null && jugadorActual.getPaises().contains(pais)) {
                            mostrarPanelControl(pais, juego.getPaises());
                        }
                    } else {
                        System.out.println("Path ID no encontrado en el mapa de países: " + svgPath);
                    }
                });

                root.getChildren().add(svgPath);  // Añadir el SVGPath al layout
            }

            // Calcular los límites del mapa para ocupar toda la pantalla
            root.setScaleX(1.0);
            root.setScaleY(1.0);
            root.setLayoutX(0);
            root.setLayoutY(0);
        }

        // Añadir el título del juego
        Text titulo = new Text("GlobalDomination");
        titulo.setFill(Color.WHITE);
        titulo.setFont(Font.font("Times New Roman", 72));
        titulo.setX(Screen.getPrimary().getBounds().getWidth() / 2 - 220); // Centrar el título horizontalmente
        titulo.setY(100); // Posicionar el título en la parte superior

        root.getChildren().add(titulo);


        // Crear un panel para mostrar el turno actual
        Pane turnoPanel = new Pane();
        Rectangle turnoBackground = new Rectangle(300, 50);
        turnoBackground.setFill(Color.rgb(0, 0, 0, 0.8)); // Fondo negro con opacidad
        turnoBackground.setArcWidth(20); // Bordes redondeados
        turnoBackground.setArcHeight(20);
        turnoBackground.setX(40);
        turnoBackground.setY(10);

        turnoActualText = new Text("Turno de: " + juego.getJugadorActual().getNombre());
        turnoActualText.setFill(Color.WHITE);
        turnoActualText.setFont(new Font("Arial", 24));
        turnoActualText.setX(50);
        turnoActualText.setY(40);

        turnoPanel.getChildren().addAll(turnoBackground, turnoActualText);
        root.getChildren().add(turnoPanel);

        // Crear el panel para mostrar la información de los colores y puntos de los jugadores
        infoColoresPane = new Pane();
        int panelHeight = 40 + (juego.getJugadores().size() * 25); // Ajustar la altura según el número de jugadores
        Rectangle infoColoresBackground = new Rectangle(300, panelHeight); // Fondo negro para la información
        infoColoresBackground.setFill(Color.rgb(0, 0, 0, 0.8)); // Color negro con opacidad
        infoColoresBackground.setArcWidth(20); // Bordes redondeados
        infoColoresBackground.setArcHeight(20);
        infoColoresBackground.setX(40);
        infoColoresBackground.setY(70);
        infoColoresPane.getChildren().add(infoColoresBackground);

        // Añadir texto para mostrar el color de cada jugador junto con los puntos que tiene
        actualizarInfoColores(); // Inicializa la información de los puntos de los jugadores

        root.getChildren().add(infoColoresPane);

        // Añadir botón para pasar el turno justo debajo de la caja de información
        Button pasarTurnoButton = new Button("Pasar Turno");
        pasarTurnoButton.setPrefSize(200, 50); // Hacer el botón más grande
        pasarTurnoButton.setLayoutX(50);
        pasarTurnoButton.setLayoutY(70 + panelHeight + 20); // Justo debajo de la caja de información
        pasarTurnoButton.setStyle("-fx-background-radius: 20; -fx-font-size: 16px;"); // Bordes redondeados y fuente más grande
        pasarTurnoButton.setOnAction(e -> {
            juego.pasarTurno();
            otorgarPuntosJugadorActual(); // Otorgar puntos al jugador actual al inicio del turno
            actualizarTurnoText();
            mostrarBannerTurno();
            actualizarInfoColores(); // Actualizar la información de los puntos de los jugadores
        });

        root.getChildren().add(pasarTurnoButton);

        return root;
    }

    private void inicializarPathToCountryMap() {
        pathToCountryMap = new HashMap<>();
        pathToCountryMap.put("path15", "Japón");
        pathToCountryMap.put("path11", "India");
        pathToCountryMap.put("path93", "Francia");
        pathToCountryMap.put("path211", "China");
        pathToCountryMap.put("style1", "Portugal");
        pathToCountryMap.put("path12", "Brasil");
        pathToCountryMap.put("path5", "España");
        pathToCountryMap.put("path25", "Inglaterra");
        pathToCountryMap.put("path19", "Grecia");
        pathToCountryMap.put("path27", "Canadá");
        pathToCountryMap.put("path35", "Turquía");
        pathToCountryMap.put("path216", "Rusia");
        pathToCountryMap.put("path45", "Marruecos");
        pathToCountryMap.put("path87", "Argentina");
        pathToCountryMap.put("path91", "Arabia Saudita");
        pathToCountryMap.put("path7", "Pakistán");
        pathToCountryMap.put("path92", "Alemania");
        pathToCountryMap.put("path118", "Bélgica");
        pathToCountryMap.put("path119", "Corea del Norte");
        pathToCountryMap.put("path121", "Corea del Sur");
        pathToCountryMap.put("path122", "Siria");
        pathToCountryMap.put("path164", "Ucrania");
        pathToCountryMap.put("path79", "Israel");
        pathToCountryMap.put("path189", "Australia");
        pathToCountryMap.put("path147", "Egipto");
        pathToCountryMap.put("path163", "EEUU");
    }

    private List<SVGPath> cargarSVGPaths(String resourcePath) {
        List<SVGPath> svgPaths = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                System.err.println("No se pudo encontrar el archivo SVG en el recurso especificado.");
                return null;
            }

            // Leer el contenido del archivo SVG
            String content = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Extraer todos los elementos <path> y crear SVGPath para cada uno
            List<String> pathContents = extractAllPathData(content);
            for (String pathContent : pathContents) {
                SVGPath svgPath = new SVGPath();
                svgPath.setContent(pathContent);

                // Extraer el ID del path y establecerlo en el SVGPath
                String pathId = extractPathId(content, pathContent);
                if (pathId != null) {
                    svgPath.setId(pathId);
                }

                svgPaths.add(svgPath);
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar el archivo SVG: " + e.getMessage());
        }
        return svgPaths;
    }

    private String extractPathId(String svgContent, String pathContent) {
        int pathIndex = svgContent.indexOf(pathContent);
        if (pathIndex != -1) {
            int idIndex = svgContent.lastIndexOf("id=\"", pathIndex);
            if (idIndex != -1) {
                int idStart = idIndex + 4;
                int idEnd = svgContent.indexOf("\"", idStart);
                return svgContent.substring(idStart, idEnd);
            }
        }
        return null;
    }

    private List<String> extractAllPathData(String svgContent) {
        List<String> paths = new ArrayList<>();
        int index = 0;
        while ((index = svgContent.indexOf("<path", index)) != -1) {
            int start = svgContent.indexOf("d=\"", index) + 3;
            int end = svgContent.indexOf("\"", start);
            if (start >= 3 && end > start) {
                paths.add(svgContent.substring(start, end));
            }
            index = end;
        }
        return paths;
    }

    private Pais buscarPais(String nombrePais) {
        for (Jugador jugador : juego.getJugadores()) {
            for (Pais pais : jugador.getPaises()) {
                if (pais.getNombre().equalsIgnoreCase(nombrePais)) {
                    return pais;
                }
            }
        }
        return null;
    }

    private void mostrarPanelControl(Pais pais, List<Pais> todosLosPaises) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel de Control");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true); // Hacer que el panel de control esté siempre encima de la ventana de juego
            frame.add(new panelControl(pais, todosLosPaises));
            frame.setVisible(true);
        });
    }

    private void otorgarPuntosJugadorActual() {
        Jugador jugadorActual = juego.getJugadorActual();
        int puntos = jugadorActual.getPaises().size(); // 1 punto por cada país que posee
        jugadorActual.añadirPuntos(puntos);
    }

    private void mostrarBannerTurno() {
        Jugador jugadorActual = juego.getJugadorActual();
        int puntosOtorgados = jugadorActual.getPaises().size();
        int puntosTotales = jugadorActual.getPuntos();
    }



    private void actualizarTurnoText() {
        turnoActualText.setText("Turno de: " + juego.getJugadorActual().getNombre());
    }

    private void actualizarInfoColores() {
        // Limpiar el panel antes de añadir los textos actualizados
        infoColoresPane.getChildren().clear();

        
        
    }

    public void mostrar(Stage stage) {
        Scene scene = new Scene(layout, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        stage.setScene(scene);
        stage.setTitle("Ventana de Juego");
        stage.setFullScreen(true);
        stage.show();
    }
}
