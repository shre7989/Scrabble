/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

/**
 * Tile - scrabble tile
 */
public class Tile {
    private final int score;
    private final char letter;

    /**
     * Constructor
     * @param letter - letter for tile
     * @param score - score for tile
     */
    public Tile(char letter, int score){
        this.score = score;
        this.letter = letter;
    }

    /**
     * getLetter - get the letter for the tile
     * @return - the letter
     */
    public char getLetter(){
        return this.letter;
    }

    /**
     * getScore - get the score for the game
     * @return - the score
     */
    public int getScore(){
        return this.score;
    }

}
