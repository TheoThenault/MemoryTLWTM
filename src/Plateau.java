import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class Plateau extends TilePane{
    private Carte cartes[][];
    private EventHandler<MouseEvent> clickHandler;

    private VBox scoreVBox = new VBox();

    public int mColumn;
    public int mLigne;

    public Carte premiereCarte = null;
    public Carte deuxiemeCarte = null;
    public boolean playerChanged = true;

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

    private void swap(Carte a, Carte b)
    {
        int v = a.valeur;
        a.valeur = b.valeur;
        b.valeur = v;

        CarteBackground c = a.image;
        a.image = b.image;
        b.image = c;
    }

    private void initClickHandler()
    {
        clickHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                Carte c = (Carte)arg0.getSource();

                if(playerChanged)
                {
                    if(premiereCarte != null)
                        premiereCarte.unselect();
                    if(deuxiemeCarte != null)
                        deuxiemeCarte.unselect();

                    premiereCarte = null;
                    deuxiemeCarte = null;
                    playerChanged = false;
                }

                if(premiereCarte == null)
                {
                    premiereCarte = c;   
                    premiereCarte.select();
                }else if(c != premiereCarte && deuxiemeCarte == null){ 
                    deuxiemeCarte = c;
                    deuxiemeCarte.select();

                    if(App.swapMode)
                    {
                        swap(premiereCarte, deuxiemeCarte);
                        premiereCarte.unselect();
                        deuxiemeCarte.unselect();
                        premiereCarte = null;
                        deuxiemeCarte = null;
                        App.swapMode = false;
                    }else{
                        if(premiereCarte.valeur == deuxiemeCarte.valeur)
                        {
                            premiereCarte.supprimer();
                            deuxiemeCarte.supprimer();
                            App.paireTrouvé(scoreVBox);
                        }else{
                            App.paireNonTrouvé(scoreVBox);
                        }
                    }
                }

            }
        };
    }
}
