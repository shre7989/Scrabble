/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.util.ArrayList;

/**
 * tray - tray for the players in the scrabble game
 */
public class Tray {
    private final ArrayList<Tile> rack;

    /**
     * Constructor
     * @param rack - rack of tiles to consruct the tray with
     */
    public Tray (ArrayList<Tile> rack){
        this.rack = rack;
    }

    /**
     * getRack - get the rack of the tray
     * @return - rack of tiles
     */
    public ArrayList<Tile> getRack(){
        return this.rack;
    }

    /**
     * getStringRack - get a String rack given the tray
     * @return - arraylist of characters representing the rack
     */
    public ArrayList<Character> getStringRack(){
        ArrayList<Character> charRack = new ArrayList<>();
        for(Tile t: rack){
            charRack.add(t.getLetter());
        }
        return charRack;
    }

}
