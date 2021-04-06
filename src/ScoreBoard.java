/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Scoreboard - scoreboard for the game, helps to display scores
 */
public class ScoreBoard extends VBox {
    private Label humanScore;
    private Label computerScore;
    private final Player human;
    private final Player computer;
    private Label tilesRemaining;
    private final TileBag tileBag;

    /**
     * Constructor
     * @param human - human player
     * @param computer - computer player
     * @param tileBag - tile bag for the game
     */
    public ScoreBoard(Player human, Player computer, TileBag tileBag){
        super(20);
        this.human = human;
        this.computer = computer;
        this.setPrefHeight(300);
        this.setPrefWidth(500);
        this.tileBag = tileBag;
        this.setStyle("-fx-background-color: #7719aa;");
        setup();
    }

    /**
     * setup - sets up the GUI for the scoreboard
     */
    public void setup(){
        Label hScore = new Label("Human:");
        hScore.setFont(new Font(25));
        hScore.setTextFill(Color.BLACK);

        humanScore = new Label("0");
        humanScore.setFont(new Font(30));
        humanScore.setTextFill(Color.BLACK);

        Label cScore = new Label("Computer:");
        cScore.setFont(new Font(25));
        cScore.setTextFill(Color.WHITE);

        computerScore = new Label("0");
        computerScore.setFont(new Font(30));
        computerScore.setTextFill(Color.WHITE);

        tilesRemaining = new Label(Integer.toString(tileBag.getSize()));
        tilesRemaining.setFont(new Font(60));
        tilesRemaining.setTextFill(Color.WHITE);

        HBox humanBox = new HBox(10);
        humanBox.setStyle("-fx-background-color: #ffffff;");
        humanBox.setAlignment(Pos.CENTER);

        HBox computerBox = new HBox(20);
        computerBox.setStyle("-fx-background-color: #000000;");
        computerBox.setAlignment(Pos.CENTER);

        VBox updates = new VBox(10);
        updates.setPrefHeight(310);
        updates.setAlignment(Pos.TOP_LEFT);

        Canvas humanCanvas = new Canvas(200,200);
        GraphicsContext humanLogo = humanCanvas.getGraphicsContext2D();
        humanLogo.setStroke(Color.BLACK);
        humanLogo.setFill(Color.BLACK);
        humanLogo.strokeOval(0,30,150,150);
        humanLogo.strokeOval(5,35,140,140);
        humanLogo.fillOval(50,70,50,50);
        humanLogo.strokeLine(85,120,125,150);
        humanLogo.strokeLine(65,120,25,155);

        Canvas tileCanvas = new Canvas(500,310);
        GraphicsContext tileLogo = tileCanvas.getGraphicsContext2D();
        tileLogo.setFill(Color.BLACK);
        tileLogo.fillRect(0,0,500,150);
        tileLogo.setFill(Color.WHITE);
        tileLogo.fillRect(0,150,500,150);
        tileLogo.setFill(Color.rgb(119,25,170));
        tileLogo.fillOval(150,50,200,200);


        StackPane group = new StackPane();
        group.getChildren().addAll(tileCanvas,tilesRemaining);

        Canvas computerCanvas = new Canvas(200,200);
        GraphicsContext computerLogo = computerCanvas.getGraphicsContext2D();
        computerLogo.setStroke(Color.WHITE);
        computerLogo.setFill(Color.WHITE);
        computerLogo.strokeOval(0,30,150,150);
        computerLogo.strokeOval(5,35,140,140);
        computerLogo.fillRoundRect(50,70,50,50,5,5);
        computerLogo.strokeLine(85,120,125,150);
        computerLogo.strokeLine(65,120,25,155);


        humanBox.getChildren().addAll(humanCanvas, hScore, humanScore);
        computerBox.getChildren().addAll(computerCanvas,cScore,computerScore);
        this.getChildren().addAll(computerBox,group,humanBox);
    }

    /**
     * updateScore- updates the score for player and computer
     */
    public void updateScore(){
        humanScore.setText(Integer.toString(human.getScore()));
        computerScore.setText(Integer.toString(computer.getScore()));
    }

    /**
     * updateTilesRemaining - shows the tiles remaining in the tile bag
     */
    public void updateTilesRemaining(){
        tilesRemaining.setText(Integer.toString(tileBag.getSize()));
    }

    /**
     * setHumanScore - set the human score
     * @param score - score to set
     */
    public void setHumanScore(int score){
        this.humanScore.setText(Integer.toString(score));
    }

    /**
     * setCmputerScore - set the computer score
     * @param score - score to set
     */
    public void setComputerScore(int score){
        this.humanScore.setText(Integer.toString(score));
    }
}
