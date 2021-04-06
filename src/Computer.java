/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Computer - computer player for the game, extends player
 */
public class Computer extends Player{
    private final Manager manager;
    private final InvertedDictionary invertedDictionary;
    private LinkedList<GridCell> currMove;
    private LinkedList<GridCell> highMove;
    private int scoreCount;
    private final Dictionary dictionary;
    private final ScrabbleLetter letter;
    private final GridCell[][] gridCells;
    private boolean pass;

    /**
     * Constructor
     * @param tray - tray for the computer player
     * @param manager - manager for the computer
     */
    public Computer(Tray tray, Manager manager) {
        super(tray);
        this.currMove = new LinkedList<>();
        this.highMove = new LinkedList<>();
        this.scoreCount = 0;
        this.manager = manager;
        this.pass = false;
        this.invertedDictionary = new InvertedDictionary(manager.getDictionaryPath());
        this.dictionary = manager.getDictionary();
        this.letter =  manager.getScrabbleLetter();
        this.gridCells = manager.getBoardGUI().getGrid();
    }

    /**
     * play - tries to make the highest scoring move possible
     * @param boardGUI - scrabble grid for the game
     * @param dictionary - dictionary for the ga,e
     * @param manager - manager of the game
     * @param isComputer - check to ensure the computer is a player
     * @return - true if playable, and false otherwise
     */
    public boolean play(BoardGUI boardGUI, Dictionary dictionary, Manager manager,boolean isComputer){
        int width = boardGUI.getBoardWidth();
        String[][] grid = boardGUI.getAnchors();
        GridCell[][] gridCells = boardGUI.getGrid();

        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                if(!(grid[i][j].equals("*")) && !(grid[i][j].equals("W"))){
                    System.out.println(" We are in : " + " "+  i + " " + j);
                    switch (grid[i][j]) {
                        case "L*": {
                            int limit;
                            Trie.TrieNode node;
                            String reverse;
                            ArrayList<Object> data;
                            String word;
                            int partScore;

                            data = manager.getPlayedWord(j + 1, i, dictionary, boardGUI, "ROW", true);
                            word = (String) data.get(0);
                            partScore = (int) data.get(1);
                            System.out.println("The fucking fucking word is: " + word);

                            if (word.length() == 1) reverse = word;
                            else reverse = dictionary.reverseWord(word);

                            node = invertedDictionary.getLastNode(reverse);

                            limit = configureLimit('L',boardGUI,i,j);

                            playRow(reverse, node, limit, i, j, this.getStringRack(),false);
                            if (!currMove.isEmpty() && (currMove.get(0) != null)) {
                                int moveScore = partScore + updateScore(currMove);
                                if(moveScore > scoreCount || highMove.isEmpty()){
                                    scoreCount = moveScore;
                                    highMove = currMove;
                                }
                                currMove = new LinkedList<>();

                            }
                            break;
                        }
                        case "*R": {
                            int limit;
                            Trie.TrieNode node;
                            ArrayList<Object> data;
                            String word;
                            int partScore;

                            int startIndex = manager.configureStartIndex(i, gridCells[i][j - 1], boardGUI, "ROW");

                            System.out.println("I am in right!!!!! and the start index is: " + startIndex);
                            data = manager.getPlayedWord(startIndex, i, dictionary, boardGUI, "ROW", true);
                            partScore = (int) data.get(1);
                            word = (String) data.get(0);
                            node = dictionary.getLastNode(word);

                            limit = configureLimit('R',boardGUI,i,j);

                            playRow(word, node, limit, i, j, this.getStringRack(),true);
                            if (!currMove.isEmpty() && (currMove.get(0) != null)){
                                int moveScore = partScore + updateScore(currMove);
                                if(moveScore > scoreCount || highMove.isEmpty()){
                                    scoreCount = moveScore;
                                    highMove = currMove;
                                }
                                currMove = new LinkedList<>();
                            }
                            break;
                        }
                        case "U*": {
                            int limit;
                            Trie.TrieNode node;
                            String reverse;
                            ArrayList<Object> data;
                            String word;
                            int partScore;

                            data = manager.getPlayedWord(i + 1, j, dictionary, boardGUI, "COL", true);
                            word = (String) data.get(0);
                            partScore = (int) data.get(1);

                            System.out.println("Here babababababab");
                            if (word.length() == 1) reverse = word;
                            else reverse = dictionary.reverseWord(word);

                            System.out.println("The word is: " + word);
                            node = invertedDictionary.getLastNode(reverse);

                            limit = configureLimit('U',boardGUI,i,j);

                            playCol(reverse, node, limit, i, j, this.getStringRack(),false);
                            if (!currMove.isEmpty() && (currMove.get(0) != null)) {

                                int moveScore = partScore + updateScore(currMove);
                                if(moveScore > scoreCount || highMove.isEmpty()){
                                    scoreCount = moveScore;
                                    highMove = currMove;
                                }
                                currMove = new LinkedList<>();
                            }
                            break;
                        }
                        case "*D": {
                            int limit;
                            Trie.TrieNode node;
                            ArrayList<Object> data;
                            int startIndex = manager.configureStartIndex(j, gridCells[i - 1][j], boardGUI, "COL");
                            System.out.println("The start index is : " + startIndex);
                            String word;
                            int partScore;

                            data = manager.getPlayedWord(startIndex, j, dictionary, boardGUI, "COL", true);
                            word = (String) data.get(0);
                            partScore = (int) data.get(1);

                            System.out.println(" I am in down and this is the partial word: " + word);

                            node = dictionary.getLastNode(word);

                            limit = configureLimit('D',boardGUI,i,j);

                            playCol(word, node, limit, i, j, this.getStringRack(),false);
                            if (!currMove.isEmpty() && (currMove.get(0) != null)) {
                                int moveScore = partScore + updateScore(currMove);
                                if(moveScore > scoreCount || highMove.isEmpty()){
                                    scoreCount = moveScore;
                                    highMove = currMove;
                                }
                                currMove = new LinkedList<>();

                            }
                            break;
                        }



                    }
                }
            }
        }
        Alert notPLayable = new Alert(Alert.AlertType.ERROR);
        notPLayable.setContentText("Computer passed its turn! because it does not have playable tiles!");
        notPLayable.setTitle("Computer not playable!");
        System.out.println("The scoreCount is: " + scoreCount );
        if(highMove.isEmpty()) {
            if(isComputer) {
                if (this.getExchangeCount() > 0 && (manager.getTileBag().getSize() > 0)) {
                    manager.getTileBag().exchange(this);
                    this.play(boardGUI, dictionary, manager, true);
                } else {
                    notPLayable.show();
                    this.setPass(true);
                }
            }
            return false;
        }
        else {
            if(isComputer) {
                this.updateScore(scoreCount);
                manager.colorSelectedCells(highMove, boardGUI);
                setGrid(highMove);
                configureTray(highMove);
                highMove.clear();
                currMove.clear();
                scoreCount = 0;
            }
            else {
                highMove.clear();
                currMove.clear();
                scoreCount = 0;
            }
            return true;
        }
    }

    /**
     * playRow - tries to make a horizontal move starting from the anchor point
     * @param partialWord - word adjacent to the anchor cells
     * @param node - current node for the partial word in our trie
     * @param limit - play limit for the move
     * @param anchorX - anchor row
     * @param anchorY - anchor column
     * @param rack - rack to make the play
     * @param forwardMove - check to see if the anchor is in left or right
     * @return - true if playable and false otherwise
     */
    private boolean playRow(String partialWord,Trie.TrieNode node, int limit, int anchorX, int anchorY,
                             ArrayList<Character> rack, boolean forwardMove){

        if(limit > 0){
            for(int i = 0; i < 26; i++){
                char c = (char) ('a' + i);
                Trie.TrieNode curr = node.getChild(i);
                Character alphabet = c;
                if(curr != null && (rack.contains(alphabet) || rack.contains('*'))) {
                    boolean isWild = false;
                    GridCell cell;

                    if (crossCheck(manager.getBoardGUI(), alphabet, anchorX, anchorY, "ROW", manager)) {

                        Tile t = new Tile(alphabet, letter.getSCORES()[i]);
                        cell = new GridCell(anchorX, anchorY, 1, 1, alphabet, manager);
                        cell.setMultiplier(gridCells[anchorX][anchorY].getMultiplier());
                        cell.setTile(t);
                        if (!(rack.contains(alphabet))) {
                            Character blank = '*';
                            rack.remove(blank);
                            isWild = true;
                        } else rack.remove(alphabet);

                        boolean recurse;
                        if (forwardMove) {
                            recurse = playRow(partialWord + alphabet, curr, limit - 1, anchorX, anchorY + 1,
                                    rack, true);
                        } else {
                            recurse = playRow(partialWord + alphabet, curr, limit - 1, anchorX, anchorY - 1,
                                    rack, false);
                        }
                        if (curr.isCompleteWord || recurse) {
                            currMove.add(cell);
                            return true;
                        } else {
                            currMove.remove(cell);
                            if (isWild) {
                                rack.add('*');
                            } else {
                                rack.add(alphabet);
                            }
                        }

                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * playCol - tries to make a vertical move starting from the anchor point
     * @param partialWord - word adjacent to the anchor cells
     * @param node - current node for the partial word in our trie
     * @param limit - play limit for the move
     * @param anchorX - anchor row
     * @param anchorY - anchor column
     * @param rack - rack to make the play
     * @param forwardMove - check to see if the anchor is in top or bottom
     * @return - true if playable and false otherwise
     */
    private boolean playCol(String partialWord,Trie.TrieNode node, int limit, int anchorX, int anchorY,
                           ArrayList<Character> rack, boolean forwardMove){

        if(limit > 0){
            for(int i = 0; i < 26; i++){
                char c = (char) ('a' + i);
                Trie.TrieNode curr = node.getChild(i);
                Character alphabet = c;
                if(curr != null && (rack.contains(alphabet) || rack.contains('*'))) {
                    boolean isWild = false;
                    GridCell cell;

                    if (crossCheck(manager.getBoardGUI(), alphabet, anchorX, anchorY, "COL", manager)) {

                        Tile t = new Tile(alphabet, letter.getSCORES()[i]);
                        cell = new GridCell(anchorX, anchorY, 1, 1, alphabet, manager);
                        cell.setMultiplier(gridCells[anchorX][anchorY].getMultiplier());
                        cell.setTile(t);
                        if (!(rack.contains(alphabet))) {
                            Character blank = '*';
                            rack.remove(blank);
                            isWild = true;
                        } else rack.remove(alphabet);

                        boolean recurse;
                        if (forwardMove) {
                            recurse = playCol(partialWord + alphabet, curr, limit - 1, anchorX + 1, anchorY, rack, true);
                        } else {
                            recurse = playCol(partialWord + alphabet, curr, limit - 1, anchorX - 1, anchorY, rack, false);
                        }
                        if (curr.isCompleteWord || recurse) {
                            currMove.add(cell);
                            return true;
                        } else {
                            currMove.remove(cell);
                            if (isWild) {
                                rack.add('*');
                            } else {
                                rack.add(alphabet);
                            }
                        }

                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * setGrid - sets the grid cell for computer move
     * @param cells - cells to set
     */
    private void setGrid(LinkedList<GridCell> cells){
        for(GridCell c: cells){
            int row = c.getRow();
            int col = c.getCol();

            gridCells[row][col].setLetter(c.getLetter());
            gridCells[row][col].setMultiplier(1);
            gridCells[row][col].setWordMult(1);
            gridCells[row][col].setTile(c.getTile());
        }
    }

    /**
     * updateScore - calculates the score for the given move
     * @param cells - cells that were played
     * @return - score for the move
     */
    private int updateScore(LinkedList<GridCell> cells){
        int wordMultiplier = 1;
        int score = 0;
        int length = cells.size();
        if(length == 7) score += 50;
        for(GridCell c : cells){
            wordMultiplier = c.getWordMult();
            int multiplier = c.getMultiplier();
            int scores = c.getTile().getScore();
            score += multiplier * scores;
        }
        return score * wordMultiplier;

    }

    /**
     * configureTray - removes the played tiles from the tray
     * @param cells - cells to remove
     */
    private void configureTray(LinkedList<GridCell> cells){

        System.out.println("This is the computer size before: " + this.getTray().getRack().size());
        for(GridCell c: cells){
            char ch = c.getLetter();
            Tile t = this.getTile(ch);
            this.getTray().getRack().remove(t);
        }
        System.out.println("This is the computer size after: " + this.getTray().getRack().size());
    }

    /**
     * crossCheck - performs necessary crosscheck for each tile played
     * @param boardGUI - scrabble grid of the game
     * @param letter - letter we are trying to play
     * @param anchorX - row of anchor cell
     * @param anchorY - column of anchor cell
     * @param constraint - type of move
     * @param manager - manager of the game
     * @return - true if satisfies the crosschecks and false otherwise
     */
    private boolean crossCheck(BoardGUI boardGUI, Character letter, int anchorX, int anchorY, String constraint,
                               Manager manager){
        GridCell[][] grid = boardGUI.getGrid();
        Cell cell;
        String word = "";
        boolean between = false;
        if(constraint.equals("ROW") && safeBounds(anchorX,anchorY,'R')){
            System.out.println("The anchors are: " + anchorX + " " + anchorY);
            cell = grid[anchorX - 1][anchorY];
            if(!(grid[anchorX - 1][anchorY].getLetter() == ' ')){
                int startIndex = manager.configureStartIndex(anchorY, cell, boardGUI, "COL");
                word = (String) manager.getPlayedWord(startIndex,anchorY,dictionary,boardGUI,"COL",true).get(0);
                word = word + letter;
                between = true;
            }
            if(!(grid[anchorX + 1][anchorY].getLetter() == ' ')){
                if (!between) {
                    word += letter;
                }
                word += (String) manager.getPlayedWord(anchorX + 1,anchorY,dictionary,boardGUI,"COL",true).get(0);
            }
            return dictionary.search(word);

        }
        else if(constraint.equals("COL") && safeBounds(anchorX, anchorY, 'C')){
            System.out.println("The anchors are: " + anchorX + " " + anchorY);
            cell = grid[anchorX][anchorY - 1];
            if(!(grid[anchorX - 1][anchorY].getLetter() == ' ')){
                int startIndex = manager.configureStartIndex(anchorX, cell, boardGUI, "ROW");
                word = (String) manager.getPlayedWord(startIndex,anchorX,dictionary,boardGUI,"ROW",true).get(0);
                word = word + letter;
                between = true;
            }
            if(!(grid[anchorX + 1][anchorY].getLetter() == ' ')){
                if (!between) {
                    word += letter;
                }
                word += (String) manager.getPlayedWord(anchorY + 1,anchorX,dictionary,boardGUI,"ROW",true).get(0);
            }
            return dictionary.search(word);
        }
        return true;
    }

    /**
     * safeBounds - maintains bound constraint of the scrabble grid for the move
     * @param x - row
     * @param y - column
     * @param constraint - type of move
     * @return - true if safe and false otherwise
     */
    private boolean safeBounds(int x, int y, char constraint){
        if(x < 0 || x >= 15) return false;
        if(y < 0 || y >= 15) return false;
        if(constraint == 'R'){
            if(x - 1 < 0) return false;
            return x + 1 < 15;
        }
        else if(constraint == 'C') {
            if (y - 1 < 0) return false;
            return y + 1 < 15;
        }
        return true;
    }

    /**
     * configureLimit - configure the limit for the move
     * @param constraint - type of move
     * @param boardGUI - scrabble grid for the game
     * @param x - starting row
     * @param y - starting column
     * @return - limit for the move
     */
    private int configureLimit(char constraint, BoardGUI boardGUI, int x, int y){
        String[][] grid = boardGUI.getAnchors();
        int size = this.getTray().getRack().size();

        if(constraint == 'L'){
            int count = 0;
            while(((y - 1) > 0) && count <= size && !grid[x][y - 1].equals("W")){
                y = y - 1;
                count++;

            }
            if((y - 1 >= 0) && !grid[x][y - 1].equals("W")) return count;
            else return count - 1;
        }
        else if(constraint == 'R'){
            int count = 0;
            while(((y + 1) < 15) && count <= size && !grid[x][y + 1].equals("W") ){
                y = y + 1;
                count++;
            }
            if((y + 1 < 15) && !grid[x][y + 1].equals("W")) return count;
            else return count - 1;
        }
        else if(constraint == 'U'){
            int count = 0;
            while(((x - 1) > 0) && count <= size && !grid[x - 1][y].equals("W")){
                x = x - 1;
                count++;
            }
            if((x - 1 >= 0)  && !grid[x - 1][y].equals("W")) return count;
            else return count - 1;
        }
        else{
            int count = 0;
            while(((x + 1) < 15) && count <= size && !grid[x + 1][y].equals("W")){
                x = x + 1;
                count++;
            }
            if((x + 1 < 15) && !grid[x + 1][y].equals("W")) return count;
            else return count - 1;
        }
    }

    /**
     * isPass - checks if the computer passed
     * @return - pass
     */
    public boolean isPass(){
        return this.pass;
    }

    /**
     * setPass - set if computer passed or didn't passed
     * @param flag - value to set
     */
    public void setPass(boolean flag){
        this.pass = flag;
    }

    /**
     * getHighMove - get the high scoring move sequence
     * @return - highest scoring move made
     */
    public LinkedList<GridCell> getHighMove(){
        return this.highMove;
    }

    /**
     * setHighMove - set current high move
     * @param move - move to set with
     */
    public void setHighMove(LinkedList<GridCell> move){
        this.highMove = move;
    }

    /**
     * getScoreCount - get the score count
     * @return - the score count
     */
    public int getScoreCount(){
        return this.scoreCount;
    }

    /**
     * setScoreCount - set the score count
     * @param count - value to set with
     */
    public void setScoreCount(int count){
        this.scoreCount = count;
    }

}

