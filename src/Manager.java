/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Manager - Game manager for the scrabble game
 */
public class Manager {
    private boolean select;
    private boolean validMove;
    private String constraint;
    private boolean start;
    private boolean firstMove ;
    private GridCell firstCell;
    private char assignedVal;
    private Tile assignedTile;
    private int indexConstraint;
    private BoardGUI boardGUI;
    private TrayGUI trayGUI;
    private final Dictionary dictionary;
    private ScrabbleLetter letter;
    private final TileBag tileBag;
    private final Alert alert;
    private boolean pass;
    private String dictionaryPath;
    private boolean play;
    /**
     * Constructor
     * @param dictionary - dictionary file for the game
     * @param tileBag - tile bag for the game
     */
    public Manager(Dictionary dictionary, TileBag tileBag){
        this.select = false;
        this.play = false;
        this.validMove = false;
        this.constraint = "N";
        this.start = true;
        this.firstMove = false;
        this.assignedVal = '-';
        this.assignedTile = null;
        this.indexConstraint = -1;
        this.firstCell = null;
        this.dictionary = dictionary;
        this.pass = false;
        this.tileBag = tileBag;
        this.alert = new Alert(Alert.AlertType.WARNING);
    }

    /**
     * playHuman - helps to configure human move
     * @param human - human player
     * @param score - score of the move
     * @param scoreBoard - scoreboard for the game
     * @param playerTrayGUI - GUI for the player's tray
     * @param tileBag - tile bag for the game
     */
    public void playHuman(Player human, int score, ScoreBoard scoreBoard, TrayGUI playerTrayGUI, TileBag tileBag){
        int total;
        System.out.println("This is the score of the move: " + score);
        if(boardGUI.getSelectedGridCells().size() == 7) score += 50;
        human.updateScore(score);
        scoreBoard.setHumanScore(human.getScore());
        human.removeTiles(trayGUI.getSelectedTrayCells());
        total = 7 - human.getTray().getRack().size();
        System.out.println("this is total" + total);
        human.getTray().getRack().addAll(tileBag.distributeTiles(total));
        playerTrayGUI.drawTray(human.getTray());
        scoreBoard.updateTilesRemaining();
        colorSelectedCells(boardGUI.getSelectedGridCells(), boardGUI);
    }

    /**
     * playComputer - helps to configure computer moves
     * @param computer  - computer player
     * @param scoreBoard - scoreboard for the game
     * @param tileBag - tileBag for the game
     */
    public void playComputer(Computer computer, ScoreBoard scoreBoard, TileBag tileBag){
        int total;
        computer.play(boardGUI,dictionary,this,true);
        scoreBoard.setComputerScore(computer.getScore());
        total = 7 - computer.getTray().getRack().size();
        computer.getTray().getRack().addAll(tileBag.distributeTiles(total));
        scoreBoard.updateTilesRemaining();
        scoreBoard.updateScore();
    }

    /**
     * isMoveSequential - checks if the move made sequential
     * @param indexConstraint - index value given the constraint
     * @param selectedGridCells - grid cells selected
     * @param boardGUI - scrabble grid for the game
     * @param constraint - type of move
     * @return - true if sequential and false otherwise
     */
    public boolean isMoveSequential(int indexConstraint, LinkedList<GridCell> selectedGridCells, BoardGUI boardGUI,
                                    String constraint){
        int length = selectedGridCells.size();
        GridCell [][] grid = boardGUI.getGrid();
        Alert illegalMove = new Alert(Alert.AlertType.WARNING);
        illegalMove.setContentText("Place tiles sequentially from left to right, or top to bottom!!!");
        illegalMove.setTitle("Illegal Move");

        if(constraint.equals("ROW")){
            sortList(selectedGridCells,"row");
            for(int i = 0; i < length; i++){
                if(i > 0) {
                    int currCol = selectedGridCells.get(i).getCol();
                    int prevCol = selectedGridCells.get(i - 1).getCol();
                    int diff = currCol - prevCol;
                    if(diff != 1) {
                        if(grid[indexConstraint][currCol - 1].getText().equals(" ")) {
                            setValidMove(false);
                            illegalMove.show();
                            return false;
                        }
                    }
                }
            }
        }
        else if(constraint.equals("COL")){
            sortList(selectedGridCells,"col");
            for(int i = 0; i < length; i++){
                if(i > 0) {
                    int currRow = selectedGridCells.get(i).getRow();
                    int prevRow = selectedGridCells.get(i - 1).getRow();

                    int diff = currRow - prevRow;
                    if(diff != 1) {
                        if(grid[currRow - 1][indexConstraint].getText().equals(" ")) {
                            setValidMove(false);
                            illegalMove.show();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * configureStartIndex - helps to configure the start index of the word on board
     * @param indexConstraint - index value given the constraint
     * @param firstCell - a cell of the word
     * @param boardGUI - scrabble grid for the game
     * @param constraint - type of move
     * @return - the start index
     */
    public int configureStartIndex(int indexConstraint, Cell firstCell, BoardGUI boardGUI, String constraint){
        int index;
        String c;
        index = indexConstraint;
        Cell[][] grid = boardGUI.getGrid();
        if(constraint.equals("ROW")){
            int col = firstCell.getCol();
            c = grid[index][col].getText();
            while(!c.equals(" ")){
                col -=1;
                try{
                    c = grid[index][col].getText();
                }
                catch(ArrayIndexOutOfBoundsException e){
                    c = grid[index][col + 1].getText();
                    col = col + 1;
                    break;
                }
            }
            index = col + 1;
        }
        else if(constraint.equals("COL")){
            int row = firstCell.getRow();
            c = grid[row][index].getText();
            while(!c.equals(" ")){
                row -= 1;
                try {
                    c = grid[row][index].getText();
                }
                catch(ArrayIndexOutOfBoundsException e){
                    c =grid[row + 1][index].getText();
                    row = row + 1;
                    break;
                }
            }
            index = row + 1;
        }
        return index;
    }

    /**
     * configureMultipleWords - helps to configure multiple words
     * @param boardGUI - scrabble grid for the game
     * @param dictionary - dictionary file for the game
     * @param constraint - type of move
     * @return - true if multiple words are valid and false other wise
     */
    public boolean configureMultipleWords(BoardGUI boardGUI, Dictionary dictionary, String constraint){
        int start;

        ArrayList<Integer> adjacentIndexes = boardGUI.getAdjacentIndexes();
        ArrayList<Object> data = new ArrayList<>();
        GridCell firstCell = getFirstCell();

        if(constraint.equals("ROW")){
            for(int i: adjacentIndexes){
                System.out.println("Here in configureMultipleWords!!!! in row!! with col" + i );
                start = configureStartIndex(i,firstCell,boardGUI,"C");
                System.out.println("The start is!!" + start);
                data.addAll(getPlayedWord(start,i,dictionary,boardGUI,"C",false));
                if((int) data.get(1) == 0) return false;
            }
        }
        else if(constraint.equals("COL")){
            for(int i: adjacentIndexes){
                System.out.println("Here in configureMultipleWords!!!! in col!! with row " + i );
                start = configureStartIndex(i,firstCell,boardGUI,"R");
                System.out.println("The start is!!" + start);
                data.addAll(getPlayedWord(start,i,dictionary,boardGUI,"R",false));
                if((int) data.get(1) == 0) return false;
            }
        }

        return true;
    }

    /**
     * touchMultipleCells - checks if the move made touches other cells
     * @param move - move made
     * @param boardGUI - scrabble grid for the game
     * @param constraint - type of move
     * @return - true if touches multiple cells and false otherwise
     */
    public Boolean touchMultipleCells(LinkedList<GridCell> move, BoardGUI boardGUI, String constraint){
        boolean isTouch = false;
        Cell[][] grid = boardGUI.getGrid();
        ArrayList<Integer> adjacentIndexes = boardGUI.getAdjacentIndexes();
        for(Cell c: move){
            System.out.println("Move is-> String: " + c.getText() + " " + c.getRow() + " " + c.getCol());
        }
        if(constraint.equals("ROW")){
            for(Cell c: move){
                System.out.println("I am here-------------------------------- in row");
                System.out.println("String: " + c.getText() + c.getCol());
                try {
                    if (!(grid[c.getRow() - 1][c.getCol()].getLetter() == ' ') || !(grid[c.getRow() + 1][c.getCol()].getLetter() == ' ')) {
                        System.out.println("the col is:" + c.getCol());
                        adjacentIndexes.add(c.getCol());
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){
                    if(c.getRow() - 1 < 0) {
                        if(!(grid[c.getRow()][c.getCol()].getLetter() == ' ')) adjacentIndexes.add(c.getCol());
                    }
                    if(c.getRow() + 1 > 15){
                        if(!(grid[c.getRow()][c.getCol()].getLetter() == ' ')) adjacentIndexes.add(c.getCol());
                    }
                }
            }
        }
        else if(constraint.equals("COL")){
            for(Cell c: move){
                System.out.println("I am here-------------------------------- in col");
                System.out.println("String: " + c.getText() + c.getRow());
                try {
                    if (!grid[c.getRow()][c.getCol() - 1].getText().equals(" ") || !grid[c.getRow()][c.getCol() + 1].getText().equals(" ")) {
                        System.out.println("the row is:" + c.getRow());
                        adjacentIndexes.add(c.getRow());
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){
                    if(c.getCol() - 1 < 0) {
                        if(!grid[c.getRow()][c.getCol() - 1].equals(" ")) adjacentIndexes.add(c.getRow());
                    }
                    if(c.getCol() + 1 > 15){
                        if(!grid[c.getRow()][c.getCol() + 1].equals(" ")) adjacentIndexes.add(c.getRow());
                    }
                }
            }
        }
        if(!adjacentIndexes.isEmpty()) isTouch = true;
        else System.out.println("Duffer");
        return isTouch;
    }

    /**
     * getPlayedWord - returns the word played, along with its score value
     * @param startIndex - start index of the word
     * @param indexConstraint - index value given the constraint
     * @param dictionary - dictionary file for the game
     * @param boardGUI - scrabble grid for the game
     * @param constraint - type of move
     * @param isComp - true if computer made the move and false otherwise
     * @return - data and statistics for the move made
     */
    public ArrayList<Object> getPlayedWord(int startIndex, int indexConstraint, Dictionary dictionary,
                                           BoardGUI boardGUI, String constraint, boolean isComp){
        ArrayList<Object> data = new ArrayList<>(3);
        int index;
        GridCell cell;
        String currWord = "";
        int currMoveScore = 0;
        int wordMultiplier = 1;
        index = indexConstraint;
        int width = boardGUI.getBoardWidth();
        GridCell[][] grid = boardGUI.getGrid();

        if(constraint.equals("ROW")){
            for(int col = startIndex; col < width; col++){
                cell = grid[index][col];
                if(isComp) System.out.println("Row: " + cell.getRow() + " Col: " + cell.getCol() + " word is: " + cell.getText());
                if(cell.getText().equals(" ")) break;
                currWord += cell.getText();
                wordMultiplier *= cell.getWordMult();
                currMoveScore += cell.getMultiplier() * cell.getTile().getScore();
            }
            currMoveScore *= wordMultiplier;
        }
        else if(constraint.equals("COL")){
            for(int row = startIndex; row < width; row++){
                cell = grid[row][index];
                if(isComp) System.out.println("Row: " + cell.getRow() + " Col: " + cell.getCol() + " word is: " + cell.getText());
                if(cell.getText().equals(" ")) break;
                currWord += cell.getText();
                wordMultiplier *= cell.getWordMult();
                currMoveScore += cell.getMultiplier() * cell.getTile().getScore();
            }
            currMoveScore *= wordMultiplier;
        }

        data.add(currWord);
        data.add(currMoveScore);
        data.add(true);

        System.out.println("This is the word: " + currWord);
        if(!isComp) {
            if (!dictionary.search(currWord)) {
                data.set(1, 0);
                data.set(2, false);
                data.set(0, " ");
            }
        }

        return data;
    }

    /**
     * resetCellsPlayed - resets the cells played
     * @param boardGUI - scrabble grid for the game
     * @param trayGUI - gui for players tray
     */
    public void resetCellsPlayed(BoardGUI boardGUI, TrayGUI trayGUI){
        trayGUI.getSelectedTrayCells().clear();
        boardGUI.getSelectedGridCells().clear();
    }

    /**
     * resetFlags - resets flags
     */
    public void resetFlags(){
        setFirstMove(true);
        setAssignedVal('-');
        setAssignedTile(null);
    }

    /**
     * sortList - sorts the list
     * @param list - list to be sorted
     * @param constraint - type of move
     */
    public void sortList(LinkedList<GridCell> list, String constraint){
        list.sort((o1, o2) -> {
            if (constraint.equals("row")) return o1.getCol() - o2.getCol();
            else return o1.getRow() - o2.getRow();
        });
    }

    /**
     * configureMultiplier - set the multipliers for played cells to 1
     * @param selectedGridCells - selected grid cells
     */
    public void configureMultiplier(LinkedList<GridCell> selectedGridCells){
        for(GridCell c: selectedGridCells){
            c.setMultiplier(1);
            c.setWordMult(1);
        }
    }

    /**
     * updateAnchors - updates the anchors after each valid move
     * @param anchorType - type of anchor
     * @param boardGUI - scrabble grid for the game
     * @param type - type of move made
     */
    public void updateAnchors(HashMap<Integer,LinkedList<Integer>> anchorType, BoardGUI boardGUI, String type){
        GridCell[][] grid = boardGUI.getGrid();
        if(type.equals("ROW")){
            for(int i = 0; i < 15; i++){
                LinkedList<Integer> list = anchorType.get(i);
                int size = list.size();
                if(!list.isEmpty()) {
                    for (int j = 0; j < size - 1; j++) {
                        if (!(grid[i][list.get(j)].getLetter() == ' ')) list.remove(j);
                    }
                }
            }
        }
        else if(type.equals("COL")){
            for(int i = 0; i < 15; i++){
                LinkedList<Integer> list = anchorType.get(i);
                int size = list.size();
                if(!list.isEmpty()) {
                    for (int j = 0; j < size - 1; j++) {
                        if (!(grid[list.get(j)][i].getLetter() == ' ')) list.remove(j);
                    }
                }
            }
        }
    }

    /**
     *
     * @param selectedGridCells
     * @param boardGUI
     * @param constraint
     * @return
     */
    public boolean validPlacement(GridCell[] selectedGridCells, BoardGUI boardGUI,String constraint){
        GridCell[][] grid = boardGUI.getGrid();
        if(constraint.equals("ROW")){
        }
        else if(constraint.equals("COL")){

        }
        return false;
    }

    /**
     * isGameOver - checks game over conditions
     * @param human - human player
     * @param computer - computer player
     * @param tileBag - tile bag for the game
     * @param scoreBoard - scoreboard for the game
     * @return - true if game over and false otherwise
     */
    public boolean isGameOver(Player human, Computer computer, TileBag tileBag, ScoreBoard scoreBoard){
        if(tileBag.getSize() == 0 || (computer.isPass() == true && this.isPass() == true)) {
            determineWinner(human, computer, scoreBoard);
            return true;
        }
        if(human.getTray().getRack().size() == 0 || computer.getTray().getRack().size() == 0) {
            determineWinner(human,computer,scoreBoard);
            return true;
        }
        if(tileBag.getSize() < 7){
            if(!isPlayableCheck(human)){
                determineWinner(human,computer,scoreBoard);
                return true;
            }
        }
        return false;
    }

    /**
     * startCheck - check if the starting move has a tile on the center
     * @param selectedGridCells - move made
     * @return - true if move satisfies check anf false otherwise
     */
    public boolean startCheck(LinkedList<GridCell> selectedGridCells){
        if(this.start) {
            for (GridCell c : selectedGridCells) {
                if (c.getCol() == 7 && c.getRow() == 7) {
                    this.setStart(false);
                    return true;
                }
            }
            return false;
        }
        else return true;
    }

    /**
     * determineWinner - helps to determine the winner of the ga,e
     * @param human - human player
     * @param computer - computer player
     * @param scoreBoard - scoreboard for the game
     */
    private void determineWinner(Player human, Computer computer, ScoreBoard scoreBoard){
        human.decScore(human.getTrayValue());
        computer.decScore(computer.getTrayValue());
        human.updateScore(computer.getTrayValue());
        computer.updateScore(human.getTrayValue());
        scoreBoard.updateScore();
        String winner;
        String scores = "\n Human Score: " + human.getScore() + "    Computer Score: " + computer.getScore();

        if(human.getScore() >= computer.getScore()) winner = "Human!";
        else winner = "Computer";

        String message = "Game Over! The winner is: " + winner + scores ;
        String header = "Game Over!!";
        showAlert(Alert.AlertType.CONFIRMATION,message,header);
    }

    /**
     * colorSelectedCells - helps to color selected grid cells for a valid move
     * @param selectedGridCells - valid move
     * @param boardGUI - scrabble grid for the game
     */
    public void colorSelectedCells(LinkedList<GridCell> selectedGridCells,BoardGUI boardGUI){
        GridCell[][] grid = boardGUI.getGrid();
        for(GridCell c: selectedGridCells){
            int row = c.getRow();
            int col = c.getCol();
            grid[row][col].setBackground("-fx-background-color: #bb8141;");
        }
    }

    /**
     * showAlert - helps to shoe alerts
     * @param type - type of alert
     * @param message - message of the alert
     * @param header - header of the alert
     */
    public void showAlert(Alert.AlertType type, String message, String header){
        this.alert.setAlertType(type);
        this.alert.setContentText(message);
        this.alert.setHeaderText(header);
        this.alert.showAndWait();
    }

    /**
     * getters and setters for instance variables
     */

    public boolean isPlayableCheck(Player player){
        Computer computer = new Computer(player.getTray(),this);
        return computer.play(boardGUI, dictionary, this, false);
    }
    public TileBag getTileBag() {
        return this.tileBag;
    }

    public boolean isValidMove(){
        return this.validMove;
    }

    public void setValidMove(boolean flag){
        this.validMove = flag;
    }

    public int getIndexConstraint(){
        return this.indexConstraint;
    }

    public void setIndexConstraint(int indexConstraint){
        this.indexConstraint = indexConstraint;
    }

    public boolean isStart(){
        return this.start;
    }

    public void setStart(boolean flag){
        this.start = flag;
    }

    public boolean isFirstMove(){
        return this.firstMove;
    }

    public void setFirstMove(boolean flag){
        this.firstMove = flag;
    }

    public char getAssignedVal(){
        return this.assignedVal;
    }

    public Tile getAssignedTile(){
        return this.assignedTile;
    }

    public void setAssignedTile(Tile tile){
        this.assignedTile = tile;
    }

    public void setFirstCell(GridCell cell){
        this.firstCell = cell;
    }

    public boolean isSelect(){
        return this.select;
    }

    public void setSelect(boolean flag){
        this.select = flag;
    }

    public String getConstraint(){
        return this.constraint;
    }

    public void setConstraint(String constraint){
        this.constraint = constraint;
    }

    public BoardGUI getBoardGUI(){
        return this.boardGUI;
    }

    public void setAssignedVal(char c) {
        this.assignedVal = c;
    }

    public TrayGUI getTrayGUI() {
        return this.trayGUI;
    }

    public void setBoardGUI(BoardGUI boardGUI){
        this.boardGUI = boardGUI;
    }

    public void setTrayGUI(TrayGUI trayGUI){
        this.trayGUI = trayGUI;
    }

    public GridCell getFirstCell(){
        return this.firstCell;
    }

    public boolean isPass(){
        return this.pass;
    }

    public void setPass(boolean flag){
        this.pass = flag;
    }

    public Dictionary getDictionary(){
        return this.dictionary;
    }

    public void setScrabbleLetter(ScrabbleLetter letter){
        this.letter = letter;
    }

    public ScrabbleLetter getScrabbleLetter(){
        return this.letter;
    }

    public void setDictionaryPath(String path){
        this.dictionaryPath = path;
    }

    public String getDictionaryPath(){
        return this.dictionaryPath;
    }

    public void setPlay(boolean flag){
        this.play = flag;
    }

    public boolean isPlay(){
        return this.play;
    }
}
