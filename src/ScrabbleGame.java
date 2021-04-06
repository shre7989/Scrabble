/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ScrabbleGame - GUI for the scrabble game
 */
public class ScrabbleGame extends Scene{
    private final Manager manager;
    private final ScoreBoard scoreBoard;
    private final Dictionary scrabbleDictionary;
    private final TileBag tileBag;
    private final TrayGUI playerTrayGUI;
    private final Player human;
    private final Computer computer;
    private final BoardGUI boardGUI;
    private ComboBox<String> moveBox;

    /**
     * Constructor
     * @param root - layout
     * @param width - width of the scene
     * @param height - height of the scene
     */
    public ScrabbleGame(Parent root, int width, int height){
        super(root,width,height);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Dictionary.txt","Sowpods.txt","Animals.txt");
        dialog.getDialogPane().setContentText("Please choose a dictionary file from the following list");
        dialog.showAndWait();
        String dictionaryPath;

        if(dialog.getSelectedItem().equals("Dictionary.txt")) dictionaryPath = "res/dictionary.txt";
        else if(dialog.getSelectedItem().equals("Sowpods.txt")) dictionaryPath = "res/sowpods.txt";
        else dictionaryPath = "res/animals.txt";

        ScrabbleLetter scrabbleLetter = new ScrabbleLetter();
        tileBag = new TileBag(scrabbleLetter);
        scrabbleDictionary = new Dictionary(dictionaryPath);
        ScrabbleBoard scrabbleBoard = new ScrabbleBoard("res/scrabble_board.txt");
        this.manager = new Manager(scrabbleDictionary,tileBag);
        manager.setDictionaryPath(dictionaryPath);
        manager.setScrabbleLetter(scrabbleLetter);
        boardGUI = new BoardGUI(scrabbleBoard, manager);
        manager.setBoardGUI(boardGUI);
        int MAX_TILES = 7;
        human = new Player(new Tray(tileBag.distributeTiles(MAX_TILES)));
        computer = new Computer(new Tray(tileBag.distributeTiles(MAX_TILES)), manager);
        playerTrayGUI = new TrayGUI(human.getTray(),manager);
        manager.setTrayGUI(playerTrayGUI);
        scoreBoard = new ScoreBoard(human,computer,tileBag);
        setupGUI((VBox) root);
    }

    /**
     * setupGUI - sets up the necessary GUI componenets for the ga,e
     * @param layout - main layout for GUI
     */
    public void setupGUI(VBox layout){

        layout.setStyle("-fx-background-color: #7719aa;");

        moveBox = new ComboBox<>();
        moveBox.getItems().addAll("Horizontal", "Vertical");
        moveBox.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-size: 20px");
        moveBox.setPromptText("Select Move");
        moveBox.setOnAction(event -> {
            manager.setSelect(true);
            switch (moveBox.getValue()){
                case "Horizontal":
                    manager.setConstraint("ROW");
                    break;
                case "Vertical":
                    manager.setConstraint("COL");
                    break;
            }
        });

        Button play = new Button("PLAY");
        play.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 20px");
        play.setOnAction(event -> this.play());

        Button exchangeRack = new Button("EXCHANGE RACK");
        exchangeRack.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 20px");
        exchangeRack.setOnAction(event -> {
            if(tileBag.getSize() != 0 && (human.getExchangeCount() != 0)){
                tileBag.exchange(human);
                playerTrayGUI.drawTray(human.getTray());
            }
            else{
                if(tileBag.getSize() == 0) {
                    String message = "Tile Bag is empty my Friend!";
                    String header = "Empty Tile Bag!!";
                    manager.showAlert(Alert.AlertType.ERROR, message, header);
                }
                else {
                    String message = "You have used all your exchange tokens!!";
                    String header = "Exchange tokens used up!";
                    manager.showAlert(Alert.AlertType.ERROR, message, header);
                }
            }
        });

        Button pass = new Button("PASS TURN");
        pass.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 20px");
        pass.setOnAction(event -> {
            manager.setPass(true);
            if(!manager.isStart()) {
                if (!boardGUI.getSelectedGridCells().isEmpty()) {
                    playerTrayGUI.recoverCells(playerTrayGUI.getSelectedTrayCells());
                    boardGUI.recoverCells(boardGUI.getSelectedGridCells());
                    manager.resetCellsPlayed(boardGUI, playerTrayGUI);
                }
                manager.playComputer(computer, scoreBoard, tileBag);
                boardGUI.updateAnchors(boardGUI.getGrid());
                if(manager.isGameOver(human,computer,tileBag,scoreBoard)) Platform.exit();
                manager.setPass(false);
                computer.setPass(false);
            }
            else{
                String message = "Please start the play first!!!";
                String header = "Invalid action intended!";
                manager.showAlert(Alert.AlertType.ERROR,message,header);
            }
        });

        Label scrabbleLogo = new Label("   SCRABBLE");
        scrabbleLogo.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-stroke: black;");
        scrabbleLogo.setFont(new Font("Sans Serif",50));

        HBox topBox = new HBox(100);
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.getChildren().addAll(scrabbleLogo);
        topBox.setStyle("-fx-background-color: #7719aa;");

        HBox middleBox = new HBox(50);
        VBox rightBox = new VBox(20);
        rightBox.setStyle("-fx-background-color: #7719aa;");
        rightBox.setPrefHeight(1);
        rightBox.setPrefWidth(1);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.setPrefHeight(100);
        buttonBox.setPrefWidth(1000);
        buttonBox.getChildren().addAll(moveBox,play,exchangeRack,pass);

        Canvas boardCanvas = new Canvas(780,775);
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(0,0,767,775,20,20);


        HBox blankBox = new HBox(5);
        blankBox.getChildren().add(new Label(" "));

        VBox blankBox2 = new VBox(0);
        blankBox2.getChildren().add(new Label(""));
        blankBox2.getChildren().add(boardGUI);
        blankBox.getChildren().add(blankBox2);

        StackPane boardGroup = new StackPane();
        boardGroup.getChildren().add(boardCanvas);
        boardGroup.getChildren().add(blankBox);

        middleBox.getChildren().add(scoreBoard);
        middleBox.getChildren().add(boardGroup);
        middleBox.getChildren().add(rightBox);
        middleBox.setAlignment(Pos.CENTER_RIGHT);
        middleBox.setStyle("-fx-background-color: #7719aa;");

        bottomBox.getChildren().add(playerTrayGUI);
        bottomBox.getChildren().add(buttonBox);
        bottomBox.setStyle("-fx-background-color: #7719aa;");
        layout.getChildren().add(topBox);
        layout.getChildren().add(middleBox);
        layout.getChildren().add(bottomBox);

    }

    /**
     * play - logic for playing the game
     */
    public void play(){

        int startIndex;
        int selectCount;
        boolean isMoveSequential;
        ArrayList<Object> data;
        boolean isValidMove;
        LinkedList<TrayCell> selectedTrayCells = playerTrayGUI.getSelectedTrayCells();
        LinkedList<GridCell> selectedGridCells = boardGUI.getSelectedGridCells();
        String constraint = manager.getConstraint();
        GridCell firstCell = manager.getFirstCell();
        selectCount = boardGUI.getSelectCount();
        int indexConstraint = manager.getIndexConstraint();

        if(!(selectCount == 0) && manager.startCheck(selectedGridCells)) {

            boolean multiple = manager.touchMultipleCells(selectedGridCells, boardGUI,constraint);
            if(multiple) {

                isValidMove = manager.configureMultipleWords(boardGUI,scrabbleDictionary, constraint);

                if(isValidMove){

                    isMoveSequential = manager.isMoveSequential(indexConstraint,selectedGridCells,boardGUI,constraint);
                    startIndex = manager.configureStartIndex(indexConstraint, firstCell, boardGUI, constraint);
                    data = manager.getPlayedWord(startIndex,indexConstraint,scrabbleDictionary,boardGUI,constraint,
                            false);

                    if(!data.get(0).equals(" ") && isMoveSequential) {
                        manager.playHuman(human,(int) (data.get(1)), scoreBoard, playerTrayGUI, tileBag);
                        manager.configureMultiplier(selectedGridCells);
                        boardGUI.updateAnchors(boardGUI.getGrid());
                        boardGUI.printAnchors();
                        manager.playComputer(computer,scoreBoard,tileBag);
                        boardGUI.updateAnchors(boardGUI.getGrid());
                    }
                    else{
                        playerTrayGUI.recoverCells(selectedTrayCells);
                        boardGUI.recoverCells(selectedGridCells);
                        String message = "Please cross check your tiles while you play!!";
                        String header = "Cross Check";
                        manager.showAlert(Alert.AlertType.WARNING,message,header);
                    }
                }
                boardGUI.clearAdjacentIndexes();
            }
            else{
                isMoveSequential = manager.isMoveSequential(indexConstraint,selectedGridCells,boardGUI,constraint);
                startIndex = manager.configureStartIndex(indexConstraint, firstCell, boardGUI, constraint);
                data = manager.getPlayedWord(startIndex,indexConstraint,scrabbleDictionary,boardGUI,constraint,false);

                if(isMoveSequential && !data.get(0).equals(" ")) {
                    System.out.println("Hello there!!!");
                    manager.playHuman(human,(int) (data.get(1)), scoreBoard, playerTrayGUI, tileBag);
                    manager.configureMultiplier(selectedGridCells);
                    boardGUI.updateAnchors(boardGUI.getGrid());
                    boardGUI.printAnchors();
                    manager.playComputer(computer,scoreBoard,tileBag);
                    boardGUI.updateAnchors(boardGUI.getGrid());
                }
                else{
                    playerTrayGUI.recoverCells(selectedTrayCells);
                    boardGUI.recoverCells(selectedGridCells);
                    String message = "The word placed is not valid. Try Again!!";
                    String header = "Invalid word!";
                    manager.showAlert(Alert.AlertType.ERROR,message,header);
                }
            }
            boardGUI.resetSelectCount();
            manager.resetFlags();
            manager.resetCellsPlayed(boardGUI,playerTrayGUI);
        }
        else{
            if(selectCount == 0) {
                String message = "Please place tiles on the grid to play!!";
                String header = "No tiles placed!";
                manager.showAlert(Alert.AlertType.ERROR,message,header);
            }
            else {
                String message = "Please start the play by placing one tile on center!";
                String header = "No tile in center!";
                manager.showAlert(Alert.AlertType.ERROR,message,header);
                boardGUI.resetSelectCount();
                manager.resetFlags();
                playerTrayGUI.recoverCells(selectedTrayCells);
                boardGUI.recoverCells(selectedGridCells);
                manager.resetCellsPlayed(boardGUI,playerTrayGUI);
            }
        }
        boardGUI.printAnchors();
        manager.setPlay(false);
        if(manager.isGameOver(human,computer,tileBag,scoreBoard)) Platform.exit();
    }

    /**
     * getScrabbleDictionary - gets the dictionary used in game
     * @return - the dictionary
     */
    public Dictionary getScrabbleDictionary(){
        return this.scrabbleDictionary;
    }

}
