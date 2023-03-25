import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class CarteBackground {

    private static final File imageFolder = new File("img"); 
    private static ArrayList<CarteBackground> liste = new ArrayList<CarteBackground>();

    private String          nom   = null;
    private File            file  = null;
    private Image           img   = null;
    private BackgroundImage bImg  = null;
    private Background      bck   = null;

    CarteBackground(String n)
    {
        nom = n;
        
        int index = exist(n);
        if(index != -1)
        {
            bck = liste.get(index).get();
        }else{
            file = new File(imageFolder, nom);
            try {
                img = new Image(new FileInputStream(file.getAbsolutePath()));
                bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, 
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                    new BackgroundSize(App.BUTTON_SIZE, App.BUTTON_SIZE, true, true, true, false));
                bck = new Background(bImg);
                liste.add(this);
            } catch (FileNotFoundException e) {
                System.out.println("Erreur création image: " + nom);
                e.printStackTrace();
            }
        }
    }

    public Background get()
    {
        return bck;
    }

    public String getNom()
    {
        return nom;
    }

    private static int exist(String nom)
    {
        for(int i = 0; i < liste.size(); i++)
        {
            if(liste.get(i).getNom().equals(nom))
            {
                return i;
            }            
        }
        return -1;
    }

}
