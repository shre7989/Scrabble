/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

/**
 * TrayGUI - GUI for player's tray, extends VBox
 */
public class TrayGUI extends VBox {
    private final Tray tray;
    private final GridPane tiles;
    private TrayCell[] playerTiles;
    private final Manager manager;
    private final LinkedList<TrayCell> selectedTrayCells;

    /**
     * Constructor
     * @param playerTray - player's tray
     * @param manager - game manager
     */
    public TrayGUI(Tray playerTray, Manager manager){
        super(20);
        this.selectedTrayCells = new LinkedList<>();
        this.tray = playerTray;
        this.tiles = new GridPane();
        this.manager = manager;
        this.setPrefHeight(100);
        this.setPrefWidth(700);
        this.setAlignment(Pos.CENTER);
        setup();
    }

    /**
     * setup - sets up the GUI components for player's tray
     */
    private void setup(){

        tiles.setHgap(10);
        drawTray(tray);

        HBox space = new HBox(20);
        space.setAlignment(Pos.CENTER);
        space.getChildren().add(tiles);

        Label playerTiles = new Label("PLAYER TILES");
        playerTiles.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 20px");

        this.getChildren().add(space);
        this.getChildren().add(playerTiles);
        tiles.relocate(50,0);
    }

    /**
     * drawTray - draws the tray given the player's tray
     * @param tray - tray to draw
     */
    public void drawTray(Tray tray){
        int length = tray.getRack().size();
        Tile tile;
        playerTiles = new TrayCell[length];
        char letter;

        for(int i = 0; i < length; i++){
            letter = tray.getRack().get(i).getLetter();
            tile = tray.getRack().get(i);
            if(letter == '*') letter = ' ';
            playerTiles[i] = new TrayCell(0,i,45,45,letter, manager);
            playerTiles[i].setTile(tile);
            playerTiles[i].setBackground("-fx-background-color: #ffffff;");
            tiles.add(playerTiles[i],i,0);
        }
    }

    /**
     * recoverCells - helps to recover tray cells in case of invalid move
     * @param selectedTrayCells - cells that were selected
     */
    public void recoverCells(LinkedList<TrayCell> selectedTrayCells){
        int col;
        for(Cell c: selectedTrayCells){
            col = c.getCol();
            playerTiles[col].lastState();
            playerTiles[col].setTile(c.getTile());
            playerTiles[col].setLetter(c.getLetter());
        }
    }

    /**
     * updateSelectedTrayCells - add selected cells
     * @param cell - cell to be added
     */
    public void updateSelectedTrayCells(TrayCell cell){
        this.selectedTrayCells.add(cell);
    }

    /**
     * getSelectedTrayCells - returns a list of selected tray cells
     * @return - list of selected tray cells
     */
    public LinkedList<TrayCell> getSelectedTrayCells(){
        return this.selectedTrayCells;
    }

}
