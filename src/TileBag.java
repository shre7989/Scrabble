/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import java.util.ArrayList;
import java.util.Random;

/**
 * TileBag - tile bag for the scrabble game
 */
public class TileBag {
    private final ArrayList<Tile> tileBag;
    private final ScrabbleLetter letter;

    /**
     * Constructor
     * @param letter - scrabble letters
     */
    public TileBag(ScrabbleLetter letter){
        tileBag = new ArrayList<>();
        this.letter = letter;
        init();
    }

    /**
     * init - construct the tile bag
     */
    private void init(){
        int frequency;
        int score;
        int f = 0;

        for(char i = 'a'; i <= 'z'; i++){
            frequency = letter.getFREQUENCY().get(i);
            score = letter.getVALUES().get(i);

            for(int j = 0; j < frequency; j++) {
                f = f + 1;
                tileBag.add(new Tile(i,score));
            }
        }


        frequency = letter.getFREQUENCY().get('*');
        for(int i = 0; i < frequency; i++){
            tileBag.add(new Tile('*',0));
        }
    }

    /**
     * distributeTiles - distribute tiles
     * @param total - amount of tiles to distribute
     * @return - arraylist of tiles
     */
    public ArrayList<Tile> distributeTiles(int total){
        ArrayList<Tile> random = new ArrayList<>();
        Tile temp;
        if(getSize() < total) total = getSize();
        for(int i = 0; i < total; i++){
            temp = tileBag.get(new Random().nextInt(tileBag.size()));
            tileBag.remove(temp);
            random.add(temp);
        }
        return random;
    }

    /**
     * getSize - get the size for tile bag
     * @return - the tile bag size
     */
    public int getSize(){
        return this.tileBag.size();
    }

    /**
     * exchange - helps to swap tiles
     * @param player - player in the scrabble game
     */
    public void exchange(Player player){
        ArrayList<Tile> rack = player.getTray().getRack();

        if(this.tileBag.size() < 7){
            int size = this.tileBag.size();
            while(size > 0) {
                player.getTray().getRack().remove(size);
                size--;
            }
            size = this.tileBag.size();
            this.tileBag.addAll(rack);
            player.getTray().getRack().addAll(this.distributeTiles(size));
        }
        else{
            this.tileBag.addAll(rack);
            player.getTray().getRack().clear();
            player.getTray().getRack().addAll(this.distributeTiles(7));
        }
        player.updateExchangeCount();
    }

}
