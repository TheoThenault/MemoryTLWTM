import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    
    public final static int    BUTTON_SIZE = 50;

    @Override
    public void start(Stage stage) {
        Plateau plateau = new Plateau();

        StackPane root = new StackPane();
        root.getChildren().add(plateau);

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setMinWidth(plateau.getMinWidth());
        stage.show();

        
    }

    public static void main(String[] args) {
        launch();
    }

}
