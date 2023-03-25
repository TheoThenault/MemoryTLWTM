import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

public class Carte extends Button {

    public int valeur;

    private static CarteBackground arriere = new CarteBackground("arriere.png");
    private        CarteBackground image   = null;

    public Carte(String arg, int val)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);
        this.setPadding(new Insets(0));
        this.setContentDisplay(ContentDisplay.TOP);

        this.setText("");
        
        image = new CarteBackground("goat.gif");
        
        unselect();
    }

    public void select()
    {
        this.setBackground(image.get());
    }

    public void unselect()
    {
        this.setBackground(arriere.get());
    }


    public void supprimer()
    {
        this.setOpacity(0);
    }

}
