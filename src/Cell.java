/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Cell - superclass for playable tiles and grid cells
 */
public class Cell extends Button {
    private int row;
    private int col;
    private int multiplier;
    private Tile tile;
    private char letter;
    private char assign;
    private Manager manager;

    /**
     * constructor
     * @param row - row of cell
     * @param col - col of cell
     * @param width - width of cell
     * @param height - height of cell
     * @param text - text value of cell
     * @param manager - game manager
     */
    public Cell(int row, int col, double width, double height, char text, Manager manager){
        super();
        assign = '-';
        this.row = row;
        this.col = col;
        this.multiplier = 1;
        this.letter = text;
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setTextFill(Color.BLACK);
        this.setText(Character.toString(text));
        this.setFont(Font.font("Cambria",FontWeight.BOLD,20));
        this.manager = manager;
    }

    /**
     * setBackground - colors the background
     * @param color - color to color the background
     */
    public void setBackground(String color){
        this.setStyle(color);
    }

    /**
     * getLetter - gets the letter for this cell
     * @return - letter of the cell
     */
    public char getLetter(){
        return this.letter;
    }

    /**
     * setLetter -set the letter for this cell
     * @param letter - value to set the letter
     */
    public void setLetter(char letter){
        this.letter = letter;
        this.setText(Character.toString(letter));
    }

    /**
     * getRow - gets the row of this cell
     * @return - row
     */
    public int getRow(){
        return this.row;
    }

    /**
     * getCol - get the column of this cell
     * @return - column
     */
    public int getCol(){
        return this.col;
    }

    /**
     * getTile - get the tile of this cell
     * @return - Tile
     */
    public Tile getTile(){
        return this.tile;
    }

    /**
     * setTile - set the tile for this cell
     * @param tile - Tile
     */
    public void setTile(Tile tile){
        this.tile = tile;
    }

    /**
     * getMultiplier - get the multiplier value for this cell
     * @return - multiplier
     */
    public int getMultiplier(){
        return this.multiplier;
    }

    /**
     * getManager - gets the manager
     * @return - Manager
     */
    public Manager getManager(){
        return this.manager;
    }

    /**
     * getAssign - gets the assigned value of the cell
     * @return - the assigned value
     */
    public char getAssign(){
        return this.assign;
    }

    /**
     * setAssign - sets the assigned value for this cell
     * @param assign - value to be assigned
     */
    public void setAssign(char assign){
        this.assign = assign;
    }

}
