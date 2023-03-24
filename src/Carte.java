import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carte extends Button {

    public int valeur;
    private ImageView img;

    public Carte(String arg, int val)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);

        try {
            img = new ImageView(new Image(new FileInputStream("/home/theo/Projets/MemoryTLWTM/img/capyu.jpeg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        img.setFitHeight(App.BUTTON_SIZE);
        img.setPreserveRatio(true);

        this.setGraphic(img);
        this.setContentDisplay(ContentDisplay.TOP);

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
