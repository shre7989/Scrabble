/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main - main class for the scrabble game
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox layout = new VBox(20);
        ScrabbleGame scrabble = new ScrabbleGame(layout,1400,1000);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scrabble);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
