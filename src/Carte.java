import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Carte extends Button {

    public int valeur;
    private Image img;

    public Carte(String arg, int val)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);

        try {
            img = new Image(this.getClass().getResource("capyu.jpeg").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        BackgroundImage bImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            new BackgroundSize(this.getWidth(), this.getHeight(), true, true, true, false));

        Background backGround = new Background(bImage);
        this.setBackground(backGround);
        this.setPadding(new Insets(0));
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
