/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author netBeans
 */
public class HelloWorld extends Application {
    
    // start est appelée automatiquement pour lancer l'application, 
    // elle a en parametre la fenetre principale. 
    @Override
    public void start(Stage primaryStage) {
        // On crée le bouton
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        // On indique ce qui se passe quand il y a une Action sur le bouton
        // en passant un gestionnaire d'evenement (que l'on cree à la volee) 
        // a la methode setOnAction du bouton
        btn.setOnAction(new EventHandler<ActionEvent>() {          
            // la méthode handle est appelee automatiquement lors de l'evenement 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        // le panneau root est un StackPane (qui empile tous les objets l'un 
        // sur l'autre. Pour l'exercice 1, il faudra remplacer ce StackPane par un Pane. 
        // Pane root = new Pane();
        StackPane root = new StackPane();
        // on met le bouton dans le panneau root
        root.getChildren().add(btn);
        
        // on cree la scene (qui sera l'interieur de la fenetre, et on y met 
        // le panneau root. La scene fait 300 x 250 pixels. 
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        // On ouvre la fenetre. 
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // lance l'application. 
        launch(args);
      
    }
    
}

