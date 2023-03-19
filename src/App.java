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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public final static int    BUTTON_SIZE = 50;
    public static int nbJoueur = 1;
    public static int nbPaires ;

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

//Création du champs de saisie pour le nombre de paires de cartes
        TextField nbPairesTF = new TextField();
        nbPairesTF.setPromptText("Entrer le nombre de paire pour la partie");
        nbPairesTF.setPrefColumnCount(20);
        nbPairesTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nbPairesTF.setText(newValue.replaceAll("[^\\d]", ""));
                
            }
        });

//Création du bouton de validation
        Button btn = new Button("Lancer la partie");
        btn.setFont(Font.font("Verdana", 14));


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
        btn.setOnAction(e ->{

            alerte.setText("");// remet le label qui sert d'alerte à zéro des que le bouton et cliqué

            for (int i = 0; i <= nbJoueur - 1; i++){
                if (nomJoueurs[i].getText().equals("")){
                    alerte.setText("Veuillez entrer tous les noms");
                    return;
                }
            }
            
            if(nbPairesTF.getText().equals("")){
                alerte.setText("Veuillez entrer un nombre de paire");
                return;
            }

            nbPaires = Integer.parseInt(nbPairesTF.getText());

            if(nbPaires > 20){
                alerte.setText("Nombre de paire trop élevé, ne doit pas être suppérieur à 15");
                return;
            }
            
            //lancement de partie une fois que tous les parametres sont entrés et valide
            Plateau plateau = new Plateau();

            StackPane root = new StackPane();
            root.getChildren().add(plateau);

            Scene scene = new Scene(root, 640, 480);
            stage.setScene(scene);
            stage.setMinWidth(plateau.getMinWidth());
            stage.show();

        });

//Gestion de la fenetre
        VBox root = new VBox();// Surement ça a changer pour la disposition de la fenetre

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

        root.getChildren().add(nbPairesTF);
        root.getChildren().add(btn);

        root.getChildren().add(alerte);
        
        Scene scene = new Scene(root, 500, 400);
 
        stage.setTitle("Parametre de la partie");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
