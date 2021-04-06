/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * InvertedDictionary - a backward dictionary for a given dictionary, extends trie
 */
public class InvertedDictionary extends Trie{
    Scanner scan;

    /**
     * Constructor
     * @param location - location of the dictionary file
     */
    public InvertedDictionary(String location) {
        Path path = Paths.get(location);
        String word;

        try {
            scan = new Scanner(path);
            while (scan.hasNextLine()) {
                word = scan.nextLine();
                this.insert(reverseWord(word));
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
