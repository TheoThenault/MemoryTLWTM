import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class Plateau extends TilePane{
    private Carte cartes[][];
    private EventHandler<MouseEvent> clickHandler;

    private int lignes   = 5;
    private int colonnes = 6;

    private Carte premiereCarte = null;
    private Carte deuxiemeCarte = null;

    public Plateau()
    {
        this(5, 6);
    }

    public Plateau(int lines, int columns)
    {
        super();
        lignes = lines;
        colonnes = columns;

        this.setHgap(2);
        this.setVgap(2);
        this.setPrefColumns(columns);
        this.setMaxWidth(App.BUTTON_SIZE * columns + (this.getHgap() * (columns-1)));
        this.setMinWidth(App.BUTTON_SIZE * columns + (this.getVgap() * (columns-1)));

        this.initClickHandler();

        cartes = new Carte[lines][];
        for(int ligne = 0; ligne < lines; ligne++)
        {
            cartes[ligne] = new Carte[columns];
            for(int colonne = 0; colonne < columns; colonne++)
            {
                cartes[ligne][colonne] = new Carte(ligne + " " + colonne, ligne + colonne);
                cartes[ligne][colonne].setOnMouseClicked(clickHandler);
                this.getChildren().add(cartes[ligne][colonne]);
            }
        }
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
                }else{
                    if(deuxiemeCarte == null)
                    {
                        if(c != premiereCarte)
                        {
                            deuxiemeCarte = c;
                            deuxiemeCarte.select();  
                            if(c.valeur == premiereCarte.valeur)
                            {
                                // TODO GAGNER DES POINTS
                                c.supprimer();
                                premiereCarte.supprimer();
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