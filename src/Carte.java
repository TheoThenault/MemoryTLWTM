import javafx.scene.control.Button;

public class Carte extends Button {

    public int valeur;

    public Carte(String arg, int val)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);
        unselect();
    }

    public void select()
    {
        this.setText("" + valeur);
    }

    public void unselect()
    {
        this.setText("");
    }


    public void supprimer()
    {
        this.setOpacity(0);
    }

}
