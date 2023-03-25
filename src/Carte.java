import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

public class Carte extends Button {

    public int valeur;

    private static CarteBackground arriere = new CarteBackground("arriere.png");
    public         CarteBackground image   = null;

    public Carte(String arg, int val)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);
        this.setPadding(new Insets(0));
        this.setContentDisplay(ContentDisplay.TOP);

        this.setText("");
        
        File[] list = CarteBackground.imageFolder.listFiles();
        String[] list_noms = new String[list.length-1];
        int indexNoms = 0;
        for(int i = 0; i < list.length; i++)
        {
            String n = list[i].getName();
            if(n.equals("arriere.png") == false)
            {
                list_noms[indexNoms++] = n;
            }
        }
        image = new CarteBackground(list_noms[val%list_noms.length]);
        
        unselect();
    }

    //faire un autre constructeur de carte avec les cartes choisi par l'utilisateur
    public Carte(String arg, int val, List<File> list)
    {
        super(arg);
        valeur = val;
        this.setPrefWidth(App.BUTTON_SIZE);
        this.setPrefHeight(App.BUTTON_SIZE);
        this.setPadding(new Insets(0));
        this.setContentDisplay(ContentDisplay.TOP);

        this.setText("");
        
        String[] list_noms = new String[list.size()];
        int indexNoms = 0;
        for(int i = 0; i < list.size(); i++)
        {
            String n = list.get(i).getName();
            if(n.equals("arriere.png") == false)
            {
                list_noms[indexNoms++] = n;
            }
        }
        image = new CarteBackground(list_noms[val%list_noms.length]);
        
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
