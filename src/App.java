import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public final static int    BUTTON_SIZE = 50;
    public static int nbJoueur = 1;
    public int nbPaires ;
    private static int currentJoueurIndex = 0;
    private static ArrayList<Score> scores = new ArrayList<Score>(); 

    @Override
    public void start(Stage stage) {

        Label alerte = new Label();
        alerte.setFont(Font.font("Verdana", 16));
        alerte.setStyle("-fx-font-weight: bold");
        
//Création des champs de saisie pour les noms
        TextField [] nomJoueurs = new TextField[4];// Créer 4 TextField (4 Joueurs max ) qui ne sont pas visible (ils seront visible si le nombre de joueur séléctioné corrsepond)
        for(int i = 0; i < 4; i++){
            nomJoueurs[i] = new TextField();
            nomJoueurs[i].setPromptText("Entrer votre nom");
            nomJoueurs[i].setVisible(false);
            nomJoueurs[i].setPrefColumnCount(20);
            nomJoueurs[i].getText();
        }
        nomJoueurs[0].setVisible(true);// Le premier est visible car il y a au moins 1 joueur

//Création de la comboBox pour le nombre de paires de cartes

        Label nbPaireLabel = new Label("Choisir le nombre de paire pour la partie");
        ComboBox<Integer> cBnbPaires = new ComboBox<>();
        cBnbPaires.getItems().addAll(15,2,18,21,28);
        cBnbPaires.getSelectionModel().selectFirst();


//Création du bouton de validation
        Button btnValidation = new Button("Lancer la partie");
        btnValidation.setFont(Font.font("Verdana", 14));

//Création du bouton Prochain joueur
        Button btnProchainJoueur = new Button("Joueur suivant");
        btnProchainJoueur.setFont(Font.font("Verdana", 14));

//Création du bouton pour quitter le jeu

        Button btnQuitte = new Button("Quitter la partie");
        btnQuitte.setFont(Font.font("Verdana", 14));


// Gestion du bouton Prochain joueur
        btnProchainJoueur.setOnAction(e ->{
            joueurSuivant();
        });

// Gestion du bouton Quitter
        btnQuitte.setOnAction(e ->{
            stage.close();
        });


// Gestion de la séléction du nombre de joueur
        Label label = new Label("Choisir le nombre de joueur ");
        ComboBox<Integer> cb = new ComboBox<>();
        cb.getItems().addAll(1, 2, 3, 4);
        cb.getSelectionModel().selectFirst();
        cb.setOnAction(e -> {
            nbJoueur = (int) cb.getValue();

            for(int i = 0; i < 4; i++){
                if(i <= nbJoueur - 1){
                    nomJoueurs[i].setVisible(true);
                }else{
                    nomJoueurs[i].setVisible(false);
                }             
            }  
        });


//Gestion du bouton de validation
        btnValidation.setOnAction(e ->{

            alerte.setText("");// remet le label qui sert d'alerte à zéro des que le bouton et cliqué

            for (int i = 0; i <= nbJoueur - 1; i++){
                if (nomJoueurs[i].getText().equals("")){
                    alerte.setText("Veuillez entrer tous les noms");
                    return;
                }
            }


            //scores contient la liste de tous les scores de la partie en cours
            for(int i = 0; i < nbJoueur; i++){
                scores.add(new Score(nomJoueurs[i].getText()));
                
            }
            scores.get(0).setStyle("-fx-background-color: yellow; -fx-border-color: black;");



            //lancement de partie une fois que tous les parametres sont entrés et valide
            
            Plateau plateau = new Plateau();

            switch(cBnbPaires.getValue()){

                case 2:
                    plateau = new Plateau(2,2);
                    break;

                case 15:
                    plateau = new Plateau(5,6);
                    break;

                case 18:
                    plateau = new Plateau(6,6);
                    break;

                case 21:
                    plateau = new Plateau(6,7);
                    break;

                case 28:
                    plateau = new Plateau(8,7);
                    break;
            }

            //VBox qui contient les scores de tous les joueurs de la partie en cours
            VBox scoreVBox = new VBox();
            scoreVBox.setAlignment(Pos.TOP_RIGHT);
            scoreVBox.setSpacing(10);
            scoreVBox.setPadding(new Insets(10));

            
            
            
            HBox btnHbox = new HBox();
            btnHbox.setAlignment(Pos.BOTTOM_RIGHT);
            btnHbox.setSpacing(10);
            btnHbox.setPadding(new Insets(10));


            if(nbJoueur == 1){//Si 1 joueur alors ne doit pas y avoir de bouton pour changer de joueur
                btnProchainJoueur.setVisible(false);
            }
            btnHbox.getChildren().addAll(btnProchainJoueur, btnQuitte);        

            scoreVBox.getChildren().addAll(scores);
            scoreVBox.getChildren().add(btnHbox);
            

            HBox root = new HBox();
            root.getChildren().add(plateau);
    

            root.getChildren().add(scoreVBox);

            Scene scene = new Scene(root, 640, 480);
            stage.setTitle("Jeu de Memory");
            stage.setScene(scene);
            stage.setMinWidth(plateau.getMinWidth());
            stage.show();

        });

//Gestion de la fenetre
        VBox root = new VBox();

        root.setSpacing(10);
        root.setPadding(new Insets(15,20, 10,10));
        root.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(label, cb);// Permet de mettre cote à cote le label et la comboBox
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15,20, 10,10));
        root.getChildren().add(hbox);
        

        for (TextField nomJoueur : nomJoueurs) {
            root.getChildren().add(nomJoueur);
        }


        Separator separator = new Separator();
        separator.setPrefWidth(root.getWidth());
        root.getChildren().add(separator);


        HBox hboxNbPaires = new HBox(nbPaireLabel, cBnbPaires);// Permet de mettre cote à cote le label et la comboBox pour le choix du nombre de paire
        hboxNbPaires.setSpacing(10);
        hboxNbPaires.setPadding(new Insets(15,20, 10,10));
        root.getChildren().add(hboxNbPaires);


        root.getChildren().add(btnValidation);

        root.getChildren().add(alerte);
        
        Scene scene = new Scene(root, 500, 400);
 
        stage.setTitle("Parametre de la partie");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void joueurSuivant(){
        currentJoueurIndex ++;
        if (currentJoueurIndex >= nbJoueur){
            currentJoueurIndex = 0;
        }
        //reset le fond de tous les joueurs
        scores.forEach(elt->{
            elt.setStyle("-fx-border-color: black;");
        });

        //mettre le fond en jaune du joueur actuel
        scores.get(currentJoueurIndex).setStyle("-fx-background-color: yellow; -fx-border-color: black;");

        System.out.println("Tour de " + scores.get(currentJoueurIndex).getName());
    }

    public static void paireTrouvé(){
       scores.get(currentJoueurIndex).addScore(1);
       //mettre à jour l'affichage du score pour ce joueur
       joueurSuivant(); 
    }

    public static void paireNonTrouvé(){
        joueurSuivant();
    }


}
