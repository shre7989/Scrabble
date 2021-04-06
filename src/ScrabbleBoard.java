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
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ScrabbleBoard - string representation of scrabble board
 */
public class ScrabbleBoard{
    private int width;
    private String[][] grid;

    /**
     * Constructor
     * @param boardConfig - location for board configuration
     */
    public ScrabbleBoard(String boardConfig){
        makeBoard(boardConfig);
    }

    /**
     * getWidth - get the width for the board
     * @return - the width of the board
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * makeBoard - construct the board from the configuration file
     * @param boardConfig - path to the configuration file
     */
    private void makeBoard(String boardConfig){
        Path path = Paths.get(boardConfig);
        ArrayList<String> temp = new ArrayList<>();
        int index = 0;

        try {
            Scanner scan = new Scanner(path);
            while(scan.hasNext()){
                String block = scan.next();
                if(!block.equals(" ")) temp.add(block);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = Integer.parseInt(temp.get(0));
        temp.remove(0);
        grid = new String[width][width];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++) {
                grid[i][j] = temp.get(index);
                index++;
            }
        }

    }

    /**
     * getGrid - get the grid
     * @return - the grid
     */
    public String[][] getGrid(){
        return this.grid;
    }

}
