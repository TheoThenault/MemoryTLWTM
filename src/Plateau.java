import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class Plateau extends TilePane{
    private Carte cartes[][];
    private EventHandler<MouseEvent> clickHandler;

    private VBox scoreVBox = new VBox();

    public int mColumn;
    public int mLigne;

    private Carte premiereCarte = null;
    private Carte deuxiemeCarte = null;

    public Plateau()
    {
        this(5, 6);
    }

    public Plateau(int lines, int columns, VBox vBoxScore)
    {
        super();
        mLigne = lines;
        mColumn = columns;
        scoreVBox = vBoxScore;

        this.setHgap(2);
        this.setVgap(2);
        this.setPrefColumns(columns);
        this.setMaxWidth(App.BUTTON_SIZE * columns + (this.getHgap() * (columns-1)));
        this.setMinWidth(App.BUTTON_SIZE * columns + (this.getVgap() * (columns-1)));

        this.initClickHandler();

        int[][] valeurs = new int[lines][];
        for(int ligne = 0; ligne < lines; ligne++)
        {
            valeurs[ligne] = new int[columns];
            for(int colonne = 0; colonne < columns; colonne++)
            {
                valeurs[ligne][colonne] = (int) Math.floor(ligne* ((int)Math.ceil(columns/2)) + colonne / 2);
            }
        }
        for(int ligne = 0; ligne < lines; ligne++)
        {
            for(int colonne = 0; colonne < columns; colonne++)
            {
                int newLin = (int) (Math.random() * lines);
                int newCol = (int) (Math.random() * columns);
                
                int inter = valeurs[ligne][colonne];
                valeurs[ligne][colonne] = valeurs[newLin][newCol];
                valeurs[newLin][newCol] = inter;
            }
        }

        cartes = new Carte[lines][];
        for(int ligne = 0; ligne < lines; ligne++)
        {
            cartes[ligne] = new Carte[columns];
            for(int colonne = 0; colonne < columns; colonne++)
            {
                cartes[ligne][colonne] = new Carte(ligne + " " + colonne, valeurs[ligne][colonne]);
                cartes[ligne][colonne].setOnMouseClicked(clickHandler);
                this.getChildren().add(cartes[ligne][colonne]);
            }
        }
    }

    public Plateau(int i, int j) {
    }

    private void initClickHandler()
    {
        clickHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                Carte c = (Carte)arg0.getSource();

                if(premiereCarte == null)
                {
                    premiereCarte = c;
                    premiereCarte.select();
                    if(c.getOpacity() == 0.0){// vérifie si la carte séléctionné n'a pas deja été trouvée
                        premiereCarte.unselect();
                        premiereCarte = null;
                        return;
                    }
                }else{
                    if(deuxiemeCarte == null)
                    {
                        if(c != premiereCarte)
                        {
                            deuxiemeCarte = c;
                            deuxiemeCarte.select();
                            if(c.getOpacity() == 0.0){// vérifie si la carte séléctionné n'a pas deja été trouvée
                                deuxiemeCarte.unselect();  
                                deuxiemeCarte = null;
                                return;
                            }
                            if(c.valeur == premiereCarte.valeur)
                            {
                                // TODO GAGNER DES POINTS
                                App.paireTrouvé(scoreVBox);
                                c.supprimer();
                                premiereCarte.supprimer();
                            }else{
                                App.paireNonTrouvé(scoreVBox);
                            }
                        }
                    }else{

                        deuxiemeCarte.unselect();  
                        deuxiemeCarte = null;
                        
                        premiereCarte.unselect();  
                        premiereCarte = c;
                        premiereCarte.select(); 

                    }
                }
            }
            
        };
    }
}
