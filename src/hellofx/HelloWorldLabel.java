/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Antoine Bergey
 */
public class HelloWorldLabel extends Application {
    
    // start est appelée automatiquement pour lancer l'application, 
    // elle a en parametre la fenetre principale. 
    @Override
    public void start(Stage primaryStage) {
        // On crée l'étiquette  
        Label monLabel = new Label();
        monLabel.setText("'Hello World'");
        
        // root, qui sera la racine de la fenêtre est un Pane (panneau sans 
        // gestionnaire de placement)
        Pane root = new Pane();
        // on place l'étiquette monLabel à l'intérieur de root
        root.getChildren().add(monLabel); 
    
        
        // On crée la scène (la zone intérieure de la fenètre)
        // elle a le Pane root comme intérieur, et on dit qu'elle fait 300 x 250 pixels.  
        Scene scene = new Scene(root, 300, 250);
        
        // On indique le titre de la fenêtre principale 
        primaryStage.setTitle("Hello World!");
        // On met la scene dans la fenêtre principale
        primaryStage.setScene(scene);
        // On montre la fenêtre principale
        primaryStage.show();
    }

    /**
     * Fonction main par défaut 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // lance l'application 
        launch(args);
      
    }
    
}

