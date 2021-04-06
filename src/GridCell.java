/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.scene.control.Alert;

/**
 * GridCell - individual cells of the scrabble grid
 */
public class GridCell extends Cell{
    private boolean isSet;
    private int multiplier;
    private int wordMult;

    /**
     * Constructor
     * @param row - row for the cell
     * @param col - column for the cell
     * @param width - width for the cell
     * @param height - height for the cell
     * @param text - text value for the cell
     * @param manager - manager for the game
     */
    public GridCell(int row, int col, double width, double height, char text, Manager manager) {
        super(row, col, width, height, text, manager);
        this.isSet = false;
        this.multiplier = 1;
        this.wordMult = 1;

        this.setOnAction(event -> {
            if(!isSet) performGridAction();
        });
    }

    /**
     * performGridAction - performs the necessary actions when a grid cell is clicked
     */
    public void performGridAction(){
        Manager manager = this.getManager();
        char assignedVal = manager.getAssignedVal();
        Tile assignedTile = manager.getAssignedTile();
        char letter;
        boolean start = manager.isStart();
        boolean select = manager.isSelect();
        boolean isFirstMove = manager.isFirstMove();
        int row = this.getRow();
        int col = this.getCol();
        String constraint = manager.getConstraint();
        BoardGUI boardGUI = manager.getBoardGUI();

        if(start && select){
            if(constraint.equals("ROW")) {
                boardGUI.updateSelectCount();
                manager.setFirstCell(this);
                manager.setIndexConstraint(row);
                manager.setFirstMove(false);
                manager.setAssignedVal('-');
                letter = assignedVal;
                this.setLetter(letter);
                this.setTile(assignedTile);
                boardGUI.updateSelectedGridCells(this);
                isSet = true;
                manager.setPlay(true);
            }
            else if(constraint.equals("COL")){
                boardGUI.updateSelectCount();
                manager.setFirstCell(this);
                manager.setIndexConstraint(col);
                manager.setFirstMove(false);
                manager.setAssignedVal('-');
                letter = assignedVal;
                this.setLetter(letter);
                this.setTile(assignedTile);
                boardGUI.updateSelectedGridCells(this);
                isSet = true;
                manager.setPlay(true);
            }
        }
        else if(!start && select && isFirstMove  && constraint.equals("ROW") && !(assignedVal == '-') ){
            boardGUI.updateSelectCount();
            manager.setFirstCell(this);
            manager.setIndexConstraint(row);
            manager.setFirstMove(false);
            manager.setAssignedVal('-');
            letter = assignedVal;
            this.setLetter(letter);
            this.setTile(assignedTile);
            boardGUI.updateSelectedGridCells(this);
            isSet = true;
            manager.setSelect(false);
            manager.setPlay(true);
        }
        else if(!start && select && isFirstMove && constraint.equals("COL") && !(assignedVal == '-')){
            boardGUI.updateSelectCount();
            manager.setFirstCell(this);
            manager.setIndexConstraint(col);
            manager.setFirstMove(false);
            manager.setAssignedVal('-');
            letter = assignedVal;
            this.setLetter(letter);
            this.setTile(assignedTile);
            boardGUI.updateSelectedGridCells(this);
            isSet = true;
            manager.setSelect(false);
            manager.setPlay(true);
        }
        else if(!start && !(assignedVal == '-')){
            if(constraint.equals("ROW") && (manager.getIndexConstraint() == row)){
                boardGUI.updateSelectCount();
                manager.setAssignedVal('-');
                letter = assignedVal;
                this.setLetter(letter);
                this.setTile(assignedTile);
                boardGUI.updateSelectedGridCells(this);
                isSet = true;
            }
            else if(constraint.equals("COL") && (manager.getIndexConstraint() == col)){
                boardGUI.updateSelectCount();
                manager.setAssignedVal('-');
                letter = assignedVal;
                this.setLetter(letter);
                this.setTile(assignedTile);
                boardGUI.updateSelectedGridCells(this);
                isSet = true;
            }
            else{
                Alert illegalMove = new Alert(Alert.AlertType.WARNING);
                illegalMove.getDialogPane().setContentText("Illegal move intended!!");
                illegalMove.setTitle("Illegal Move");
                illegalMove.show();

            }
        }
        System.out.println("This is the select count" + manager.getBoardGUI().getSelectCount());
        if(!manager.isSelect()) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.getDialogPane().setContentText("Please select the type of move you want to make first!!");
            notSelected.setTitle("Illegal Move");
            notSelected.show();
        }
    }

    /**
     * lastState - helps to return to previous grid state
     */
    public void lastState(){
        setAssign('-');
        isSet = false;
        this.setText(Character.toString(getLetter()));
    }

    /**
     * setLetter - sets the letter for the grid
     * @param letter - value to set the letter
     */
    @Override
    public void setLetter(char letter){
        if(!isSet){
            super.setLetter(letter);
        }
    }

    /**
     * setMultiplier - sets the multiplier value for the cell
     * @param multiplier - value to set with
     */
    public void setMultiplier(int multiplier){
        this.multiplier = multiplier;
    }

    /**
     * getMultiplier - gets the multiplier value for the cell
     * @return - the multiplier value
     */
    public int getMultiplier(){
        return this.multiplier;
    }

    /**
     * getWordMult - gets the wordMultiplier value
     * @return - the word multiplier
     */
    public int getWordMult(){
        return this.wordMult;
    }

    /**
     * setWordMult - sets the word multiplier value
     * @param mult - value to set with
     */
    public void setWordMult(int mult){
        this.wordMult = mult;
    }

}
