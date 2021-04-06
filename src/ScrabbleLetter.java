/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.util.HashMap;

/**
 * ScrabbleLetter - class for scrabble letters in the game
 */
public class ScrabbleLetter{
    private final HashMap<Character,Integer> VALUES;
    private final HashMap<Character,Integer> FREQUENCY;
    private final int[] SCORES;
    private final int[] RECURRENCE;

    /**
     * Constructor
     */
    public ScrabbleLetter(){
        VALUES = new HashMap<>();
        FREQUENCY = new HashMap<>();
        SCORES = new int[] {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
        RECURRENCE = new int[] {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
        mapValues();
    }

    /**
     * mapValues - sets values and frequency for each letter
     */
    private void mapValues(){
        int count = 0;
        for (char i = 'a'; i <= 'z'; i++){
            VALUES.put(i, SCORES[count]);
            FREQUENCY.put(i,RECURRENCE[count]);
            count++;
        }
        FREQUENCY.put('*',2);
    }

    /**
     * getValues - returns a hashmap of character and its values
     * @return - hashmap of values
     */
    public HashMap<Character, Integer> getVALUES(){
        return this.VALUES;
    }

    /**
     * getFREQUENCY - returns a hashmap of characters and it
     * @return - hashmap of frequency
     */
    public HashMap<Character, Integer> getFREQUENCY(){
        return this.FREQUENCY;
    }

    /**
     * getSCORES - gets the score given character index
     * @return - score for the character
     */
    public int[] getSCORES(){
        return this.SCORES;
    }
}
