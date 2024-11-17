import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import java.awt.*;

public class PantallaInicial extends Application {
    private ComboBox<Integer> jugadoresComboBox;
    private Button iniciarButton;
    private Stage primaryStage; // Guardar la referencia de la ventana principal

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Asignar referencia
        primaryStage.setTitle("Dominio Global");

        // Cargar la imagen de fondo
        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/resources/wallpaper.jpg")));
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1080);
        backgroundImageView.setPreserveRatio(false);

        // Título
        Text tituloLabel = new Text("Dominio Global");
        tituloLabel.setFill(Color.WHITE); // Cambiar el color de las letras a blanco
        tituloLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;"); // Aumentar el tamaño del título

        // Texto de instrucciones
        Text instruccionesLabel = new Text("¿Cuántos jugadores van a jugar?");
        instruccionesLabel.setFill(Color.WHITE); // Cambiar el color de las letras a blanco
        instruccionesLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px 0;");

        // ComboBox para seleccionar el número de jugadores
        jugadoresComboBox = new ComboBox<>();
        jugadoresComboBox.getItems().addAll(2, 3, 4);
        jugadoresComboBox.setValue(2); // Valor predeterminado
        jugadoresComboBox.setStyle(
                "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-padding: 5; " +
                        "-fx-font-size: 16px;"
        );

        // Botón para iniciar el juego
        iniciarButton = new Button("Iniciar Juego");
        iniciarButton.setOnAction(e -> iniciarJuego());
        iniciarButton.setStyle(
                "-fx-background-color: #333; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 20; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 16px; " +
                        "-fx-border-color: #555; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 20;"
        );

        // Layout de controles
        VBox controlsLayout = new VBox(20, tituloLabel, instruccionesLabel, jugadoresComboBox, iniciarButton);
        controlsLayout.setStyle("-fx-padding: 20;");
        controlsLayout.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), new CornerRadii(10), Insets.EMPTY)));
        controlsLayout.setMaxWidth(400);
        controlsLayout.setMaxHeight(300);

        // Ajustar manualmente la posición del formulario para centrarlo
        controlsLayout.setLayoutX(960 - 200); // 960 es la mitad de 1920, y 200 es la mitad de 400 (ancho del formulario)
        controlsLayout.setLayoutY(540 - 150); // 540 es la mitad de 1080, y 150 es la mitad de 300 (alto del formulario)

        // Contenedor principal
        StackPane root = new StackPane(backgroundImageView, controlsLayout);
        root.setPadding(new Insets(0));
        root.setStyle("-fx-background-color: #808080;"); // Establecer el fondo gris

        // Scene
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        primaryStage.setScene(scene);

        // Configurar la ventana para abrirse en pantalla completa
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    private void iniciarJuego() {
        int numeroJugadores = jugadoresComboBox.getValue();
        Juego nuevoJuego = new Juego(numeroJugadores);
        nuevoJuego.iniciar(); // Iniciar la lógica del juego

        // Cerrar la ventana actual
        primaryStage.close();

        // Abrir la nueva ventana para el juego
        Stage ventanaJuego = new Stage();
        ventanaJuego.setTitle("GlobalDomination");
        ventanaJuego juegoUI = new ventanaJuego(nuevoJuego); // Clase VentanaJuego usando JavaFX
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene juegoScene = new Scene(juegoUI.getLayout(), screenSize.getWidth(), screenSize.getHeight());
        ventanaJuego.setScene(juegoScene);
        ventanaJuego.setFullScreen(true); // Abrir en pantalla completa
        ventanaJuego.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
