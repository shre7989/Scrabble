/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * PLayer -  player of the scrabble game
 */
public class Player {
    private int score;
    private Tray tray;
    private int exchangeCount = 2;

    /**
     * Constructor
     * @param tray - constructs the player given the tray
     */
    public Player(Tray tray){
        this.tray = tray;
        this.score = 0;
    }

    /**
     * removeTiles - helps to remove tiles from the player's tray
     * @param playedTiles - tiles played by player
     */
    public void removeTiles(LinkedList<TrayCell> playedTiles){
        Tile tile;
        for(TrayCell c: playedTiles){
            tile = c.getTile();
            this.tray.getRack().remove(tile);
        }
    }

    /**
     * getTile - gets the tile given the letter
     * @param letter - letter of the tile
     * @return - tile that contains the letter
     */
    public Tile getTile(char letter){
        ArrayList<Tile> rack = this.tray.getRack();
        for(Tile t: rack){
            if(t.getLetter() == letter) return t;
        }
        return null;
    }

    /**
     * getTrayValue - gets the score value for the player's tray
     * @return - score for the tray
     */
    public int getTrayValue(){
        ArrayList<Tile> rack = this.tray.getRack();
        if(rack.size() == 0) return 0;
        int trayValue = 0;
        for(Tile t: rack){
            trayValue += t.getScore();
        }
        return  trayValue;
    }

    /**
     * getStringRack - helps to get a character representation of the player's rack
     * @return - string rack
     */
    public ArrayList<Character> getStringRack(){
        ArrayList<Tile> rack = this.tray.getRack();
        ArrayList<Character> stringRack =  new ArrayList<>();
        for(Tile t: rack){
            stringRack.add(t.getLetter());
        }
        return stringRack;
    }

    /**
     * decScore - helps to decrement player's score by the given amount
     * @param count - value to decrement by
     */
    public void decScore(int count){
        this.score -= count;
    }

    /**
     * - getters and setters for instance variables
     */
    public int getScore(){
        return this.score;
    }

    public void setTray(Tray tray){
        this.tray = tray;
    }

    public Tray getTray(){
        return this.tray;
    }

    public void getTile(TileBag tileBag){
        tileBag.distributeTiles(1);
    }

    public void updateScore(int moveScore){
        score += moveScore;
    }

    public void updateExchangeCount(){
        this.exchangeCount -= 1;
    }

    public int getExchangeCount(){
        return this.exchangeCount;
    }

}
