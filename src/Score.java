import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Score extends StackPane{//Toutes les infos mais les unes sur les autres car pas encore agancé

    private String player;
    private int score;

    private static int scoreParDefaut = 0;

    public Score(){

    }

    public Score(String player, int score){
        this.score = score;
        this.player = player;
        this.setPrefSize(150, 50);
        this.setStyle("-fx-border-color: black");
        
        Label playerTexte = new Label(player);
        playerTexte.setStyle("-fx-border-color: black");
    

        Label playerScore = new Label(Integer.toString(score));
        playerScore.setStyle("-fx-border-color: black");

        Text t = new Text("Score");

        StackPane.setAlignment(playerTexte, Pos.CENTER_LEFT );
        StackPane.setAlignment(t, Pos.CENTER );
        StackPane.setAlignment(playerScore, Pos.CENTER_RIGHT);


        this.getChildren().addAll(playerTexte, t,playerScore);

    }

    public Score(String player){
        this(player, scoreParDefaut);
    }

    public void addScore(){
        this.score = this.score + 1;
    }

    public void addScore(int x){
        this.score = this.score + x;
    }

    public void setScore(int x){
        this.score = x;
    }

    public String getName(){
        return this.player;
    }

    public int getScore(){
        return this.score;
    }
}
