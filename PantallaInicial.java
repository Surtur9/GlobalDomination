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
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dominio Global");

        // Sirve para cargar la imagen de fondo
        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/resources/wallpaper.jpg")));
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1080);
        backgroundImageView.setPreserveRatio(false);

        Text tituloLabel = new Text("Dominio Global");
        tituloLabel.setFill(Color.WHITE);
        tituloLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        Text instruccionesLabel = new Text("¿Cuántos jugadores van a jugar?");
        instruccionesLabel.setFill(Color.WHITE);
        instruccionesLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px 0;");

        // Seleccionar el número de jugadores
        jugadoresComboBox = new ComboBox<>();
        jugadoresComboBox.getItems().addAll(2, 3, 4);
        jugadoresComboBox.setValue(2);

        iniciarButton = new Button("Iniciar Juego");
        iniciarButton.setOnAction(e -> iniciarJuego());

        VBox controlsLayout = new VBox(20, tituloLabel, instruccionesLabel, jugadoresComboBox, iniciarButton);
        controlsLayout.setStyle("-fx-padding: 20;");
        controlsLayout.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), new CornerRadii(10), Insets.EMPTY)));
        controlsLayout.setMaxWidth(400);
        controlsLayout.setMaxHeight(300);

        // Sirve para ajustar manualmente la posición del formulario
        controlsLayout.setLayoutX(960 - 200);
        controlsLayout.setLayoutY(540 - 150);

        StackPane root = new StackPane(backgroundImageView, controlsLayout);
        root.setPadding(new Insets(0));
        root.setStyle("-fx-background-color: #808080;");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    private void iniciarJuego() {
        int numeroJugadores = jugadoresComboBox.getValue();
        Juego nuevoJuego = new Juego(numeroJugadores);
        nuevoJuego.iniciar();

        primaryStage.close();

        Stage ventanaJuego = new Stage();
        ventanaJuego.setTitle("GlobalDomination");
        ventanaJuego juegoUI = new ventanaJuego(nuevoJuego);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene juegoScene = new Scene(juegoUI.getLayout(), screenSize.getWidth(), screenSize.getHeight());
        ventanaJuego.setScene(juegoScene);
        ventanaJuego.setFullScreen(true);
        ventanaJuego.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

