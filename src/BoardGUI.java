/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * boardGUI - represents the actual scrabble board in game
 */
public class BoardGUI extends GridPane {
    private ScrabbleBoard board;
    private GridCell[][] grid;
    private String[][] gridBluePrint;
    private Manager manager;
    private ArrayList<Integer> adjacentIndexes;
    private LinkedList<GridCell> selectedGridCells;
    private int width;
    private int selectCount;
    private String[][] anchors;

    public BoardGUI(ScrabbleBoard board, Manager manager){
        this.board = board;
        this.selectCount = 0;
        this.selectedGridCells = new LinkedList<>();
        this.adjacentIndexes = new ArrayList<>();
        this.setHgap(5);
        this.setVgap(5);
        this.manager = manager;
        width = board.getWidth();
        gridBluePrint = board.getGrid();
        this.anchors = new String[width][width];
        setupAnchors();
        drawGrid();
    }

    /**
     * drawGrid - draws the scrabble grid
     */
    public void drawGrid(){
        this.grid = new GridCell[width][width];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                switch (gridBluePrint[i][j]) {
                    case "..":
                        grid[i][j] = new GridCell(i, j, 45, 45, ' ',manager);
                        grid[i][j].setBackground("-fx-background-color: #ffffff;");
                        break;
                    case "2.":
                        grid[i][j] = new GridCell(i, j, 45, 45, ' ',manager);
                        grid[i][j].setBackground("-fx-background-color: #ff66cc; ");
                        grid[i][j].setWordMult(2);
                        break;
                    case ".2":
                        grid[i][j] = new GridCell(i, j, 45, 45, ' ',manager);
                        grid[i][j].setBackground("-fx-background-color: #00b2ff; ");
                        grid[i][j].setMultiplier(2);
                        break;
                    case "3.":
                        grid[i][j] = new GridCell(i, j, 45, 45, ' ',manager);
                        grid[i][j].setBackground("-fx-background-color: #fd1d1d; ");
                        grid[i][j].setWordMult(3);
                        break;
                    case ".3":
                        grid[i][j] = new GridCell(i, j, 45, 45, ' ',manager);
                        grid[i][j].setBackground("-fx-background-color: #1e34eb; ");
                        grid[i][j].setMultiplier(3);
                        break;
                }
                this.add(grid[i][j],j,i);
                setHalignment(grid[i][j], HPos.CENTER);
                setValignment(grid[i][j], VPos.CENTER);
            }
        }
    }

    /**
     * recoverCells: recovers played tiles in case of invalid play
     * @param selectedGridCells - tiles that were placed
     */
    public void recoverCells(LinkedList<GridCell> selectedGridCells){
        int row;
        int col;

        for(GridCell c: selectedGridCells){
            row = c.getRow();
            col = c.getCol();

            grid[row][col].lastState();
            grid[row][col].setTile(null);
            grid[row][col].setLetter(' ');
        }
    }

    /**
     * setupAnchors : sets up the anchors next to words
     */
    private void setupAnchors(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j  < width; j++){
                anchors[i][j] = "**";
            }
        }
    }

    /**
     * updateAnchors: updates anchors after each play
     * @param grid - grid whose anchors are updated
     */
    public void updateAnchors(GridCell[][] grid){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                if(!(grid[i][j].getLetter() == ' ')) anchors[i][j] = "WW";
                else{
                    if((check(i,j,'u',grid)) && (check(i,j,'d',grid))) anchors[i][j] = "MC";
                    else if((check(i,j,'l',grid)) && (check(i,j,'r',grid))) anchors[i][j] = "MR";
                    else if((check(i,j,'l',grid))) anchors[i][j] = "*R";
                    else if((check(i,j,'r',grid))) anchors[i][j] = "L*";
                    else if((check(i,j,'u',grid))) anchors[i][j] = "*D";
                    else if((check(i,j,'a',grid))) anchors[i][j] = "U*";
                }
            }
        }
    }

    public void printAnchors() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(anchors[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * check: performs necessary checks to determine the type of anchor point
     * @param row - row of anchor point
     * @param col - column of anchor point
     * @param type - type of check
     * @param grid - our scrabble grid
     * @return true if necessary checks are valid, and false otherwise
     */
    private boolean check(int row, int col, char type, GridCell[][] grid){
        if(type == 'l'){
            if(col - 1 < 0) return false;
            return !(grid[row][col - 1].getLetter() == ' ');

        }
        else if(type == 'r'){
            if(col + 1 >= width) return false;
            return !(grid[row][col + 1].getLetter() == ' ');
        }
        else if(type == 'u'){
            if(row - 1 < 0) return false;
            return !(grid[row - 1][col].getLetter() == ' ');
        }
        else{
            if(row + 1 >= width) return false;
            return !(grid[row + 1][col].getLetter() == ' ');
        }
    }

    /**
     * getGrid - gets the grid
     * @return - the grid
     */
    public GridCell[][] getGrid(){
        return this.grid;
    }

    /**
     * getBoardWidth - get the boardWidth
     * @return - the board width
     */
    public int getBoardWidth(){
        return this.width;
    }

    /**
     * updateSelectCount - update the select Count by 1
     */
    public void updateSelectCount(){
        this.selectCount += 1;
    }

    /**
     * resetSelectCount: reset the select count
     * reset the select count
     */
    public void resetSelectCount(){
        this.selectCount = 0;
    }

    /**
     * getSelectCount: get the select count
     * @return the selectCount
     */
    public int getSelectCount(){
        return this.selectCount;
    }

    /**
     * clearAdjacentIndexes: clears the adjacent indexes
     */
    public void  clearAdjacentIndexes(){
        this.adjacentIndexes.clear();
    }

    /**
     * getAdjacentindes - return adjacentIndexes
     * @return
     */
    public ArrayList<Integer> getAdjacentIndexes() { return this.adjacentIndexes; }

    /**
     * updateSelectedGridCells: updates the selected grid cells
     * @param cell - cell to be added
     */
    public void updateSelectedGridCells(GridCell cell){
        this.selectedGridCells.add(cell);
    }

    /**
     * getSelectedGridCells - gets the selected grid cells
     * @return the selected grid cells
     */
    public LinkedList<GridCell> getSelectedGridCells(){
        return this.selectedGridCells;
    }

    /**
     * getAnchors - returns a string representation of anchor points
     * @return - the anchor grid
     */
    public String[][] getAnchors() { return this.anchors; }
}