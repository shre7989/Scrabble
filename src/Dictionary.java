/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Dictionary - dictionary file for the game
 */
public class Dictionary extends Trie {
    private Scanner scan;

    /**
     * constructor
     * @param location - location for the dictioanry file
     */
    public Dictionary(String location) {
        Path path = Paths.get(location);
        String word;

        try {
            scan = new Scanner(path);
            while (scan.hasNextLine()) {
                word = scan.nextLine();
                this.insert(word);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
