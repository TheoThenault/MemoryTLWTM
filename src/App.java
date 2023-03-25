import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class App extends Application {

    public final static int    BUTTON_SIZE = 80;
    public static int nbJoueur = 1;
    public int nbPaires = 15;
    public List<File> fichierChoisi;
    public Label alerte;
    private static int currentJoueurIndex = 0;
    private static ArrayList<Score> scores = new ArrayList<Score>(); 
    public static boolean swapMode = false;
    private static Plateau plateau;
    public VBox scoreVBox;

    @Override
    public void start(Stage stage) {
        alerte = new Label();
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

//Création du bouton pour échanger 2 cartes

        Button btnSwap = new Button("Echanger 2 cartes");
        btnSwap.setId("swap");
        btnSwap.setFont(Font.font("Verdana", 14));
        btnSwap.setVisible(false);

//Création du bouton pour la bibliotheque d'image

        Button btnAjouterImage = new Button("Choisir des cartes à ajouter");
        btnAjouterImage.setFont(Font.font("Verdana", 14));

//Création du bouton pour choisir les images à utiliser

        Button btnChoisirImages = new Button("Choisir les cartes pour la partie");
        btnChoisirImages.setFont(Font.font("Verdana", 14));


//Gestion de la combobox nbpaire

        cBnbPaires.setOnAction(event -> {
            nbPaires = cBnbPaires.getValue();
        });

//Gestion du bouton choisir image

        btnChoisirImages.setOnAction(e-> {
            alerte.setText("");
            choisirImages();
        });

//Gestion du bouton Biblio

        btnAjouterImage.setOnAction(e ->{
            ajoutImage();
        });

        
// Gestion du bouton Prochain joueur
        btnProchainJoueur.setOnAction(e ->{
            if(plateau.premiereCarte != null && plateau.deuxiemeCarte != null)
            {
                joueurSuivant(scoreVBox);// Pour que le bouton marche mais ne vérifie pas si le prochian joueur est le plus faible en point
            }
        });

// Gestion du bouton Quitter
        btnQuitte.setOnAction(e ->{
            stage.close();
        });

// Gestion du bouton échange 2 cartes
        btnSwap.setOnAction(e ->{
            //échange
            swapMode = true;
            plateau.premiereCarte.unselect();
            plateau.deuxiemeCarte.unselect();
            plateau.premiereCarte = null;
            plateau.deuxiemeCarte = null;
            btnSwap.setVisible(false);
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

            //VBox qui contient les scores de tous les joueurs de la partie en cours
            scoreVBox = new VBox();
            scoreVBox.setAlignment(Pos.TOP_RIGHT);
            scoreVBox.setSpacing(10);
            scoreVBox.setPadding(new Insets(10));
         
            HBox btnHbox = new HBox();
            btnHbox.setAlignment(Pos.BOTTOM_RIGHT);
            btnHbox.setSpacing(10);
            btnHbox.setPadding(new Insets(10));


            if(nbJoueur == 1){//Si 1 joueur alors ne doit pas y avoir de bouton pour changer de joueur
                btnProchainJoueur.setText("Tour suivant");
            }

            btnHbox.getChildren().addAll(btnProchainJoueur, btnQuitte);        

            scoreVBox.getChildren().addAll(scores);
            scoreVBox.getChildren().addAll(btnHbox, btnSwap);


            //lancement de partie une fois que tous les parametres sont entrés et valides
            
            plateau = new Plateau();
 
            switch(cBnbPaires.getValue()){

                case 2:
                    plateau = new Plateau(2,2,scoreVBox, fichierChoisi);
                    break;

                case 15:
                    plateau = new Plateau(5,6,scoreVBox, fichierChoisi);
                    break;

                case 18:
                    plateau = new Plateau(6,6,scoreVBox, fichierChoisi);
                    break;

                case 21:
                    plateau = new Plateau(6,7,scoreVBox, fichierChoisi);
                    break;

                case 28:
                    plateau = new Plateau(8,7,scoreVBox, fichierChoisi);
                    break;
            }


            HBox root = new HBox();
            root.getChildren().add(plateau);
    
            root.getChildren().add(scoreVBox);

            Scene scene = new Scene(root, 
                plateau.mColumn*App.BUTTON_SIZE + 325, 
                plateau.mLigne*App.BUTTON_SIZE + 20);
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


        root.getChildren().addAll(btnValidation, btnAjouterImage, btnChoisirImages);

        root.getChildren().add(alerte);
        
        Scene scene = new Scene(root, 500, 450);
 
        stage.setTitle("Parametre de la partie");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void joueurSuivant(VBox vbox){
        App.swapMode = false;

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

        int min = scores.get(plusPetitScoreIndex()).getScore();
        int count = 0;
        for (Score s : scores) {
            if(s.getScore() == min)
                count++;
        }
        if(currentJoueurIndex == plusPetitScoreIndex() && count == 1 && nbJoueur > 1){
            //mettre la visibilité du bouton à true
            vbox.lookup("#swap").setVisible(true);
        }else{
            vbox.lookup("#swap").setVisible(false);
        }

        plateau.premiereCarte.unselect();
        plateau.deuxiemeCarte.unselect();
        plateau.premiereCarte = null;
        plateau.deuxiemeCarte = null;

        plateau.playerChanged = true;
        System.out.println("Tour de " + scores.get(currentJoueurIndex).getName());
    }

    public static void paireTrouvé(VBox vbox){
       scores.get(currentJoueurIndex).addScore(1);
       //mettre à jour l'affichage du score pour ce joueur
       //joueurSuivant(vbox); 
    }

    public static void paireNonTrouvé(VBox vbox){
        //joueurSuivant(vbox);
    }

    public static int plusPetitScoreIndex(){
        int min = scores.get(0).getScore();
        int indexJoueurPetitScore = 0;
        for( int i = 0; i < nbJoueur; i++){
            if( scores.get(i).getScore() < min){
                min = scores.get(i).getScore();
                indexJoueurPetitScore = i;
            }
        }
        return indexJoueurPetitScore;
    }

    public void ajoutImage(){

        FileChooser fileChooser = new FileChooser();

        // Définir le titre de la fenêtre
        fileChooser.setTitle("Choisir des images");

        // Ajouter des filtres pour ne montrer que certains types de fichiers
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );

        // Afficher la fenêtre de choix de fichier
        File fichierChoisi = fileChooser.showOpenDialog(new Stage());

        // Récupérer le chemin de l'image sélectionné
        if (fichierChoisi != null) {
            Path chemin = fichierChoisi.toPath();
            System.out.println("Fichier sélectionné : " + chemin);
            try {
                Files.copy(chemin,Paths.get("img/" + chemin.getFileName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void choisirImages(){

        FileChooser fileChooser = new FileChooser();


        // Définir le titre de la fenêtre
        fileChooser.setTitle("Choisir des images, " + nbPaires);

        //detecter le nombre d'images à mettre 

        // Ajouter des filtres pour ne montrer que certains types de fichiers
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
        fileChooser.setInitialDirectory(new File("img"));

        // Afficher la fenêtre de choix de fichier
        fichierChoisi = fileChooser.showOpenMultipleDialog(new Stage());

        /*
        for (File file : fichierChoisi) {
            System.out.println(file.getName());
        }
        */

        // Récupérer le chemin des images sélectionnées
        
        //Vérification du nombre d'images
        if(fichierChoisi != null && fichierChoisi.size() < nbPaires){
            alerte.setText("Nombre d'image trop petit");
        }else if(fichierChoisi != null && fichierChoisi.size() > nbPaires){
            alerte.setText("Nombre d'image trop grand");
        }
    }

}
