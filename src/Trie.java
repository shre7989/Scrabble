/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

/**
 * Trie - a prefix binary tree to store our dictionary words
 */
public class Trie {

    static final int TOTAL_ALPHABETS = 26;

    /**
     * TrieNode - inner class, to store each words as nodes
     */
    static class TrieNode{
        TrieNode[] dictionary = new TrieNode[TOTAL_ALPHABETS];
        boolean isCompleteWord;

        /**
         * Constructor
         */
        public TrieNode(){
            isCompleteWord = false;
            for(int i = 0; i < TOTAL_ALPHABETS; i++ ){
                dictionary[i] = null;
            }
        }

        /**
         * setChild - set the node as child given the index
         * @param node - child node
         * @param index - parent index
         */
        public void setChild(TrieNode node, int index){
            dictionary[index] = node;
        }

        /**
         * getChild - get the child given the index
         * @param index - index of ndoe
         * @return - the node
         */
        public TrieNode getChild(int index){
            return dictionary[index];
        }

        /**
         * setCompleteWord - set if word is complete
         * @param isCompleteWord - boolean flag
         */
        public void setCompleteWord(Boolean isCompleteWord){
            this.isCompleteWord = isCompleteWord;
        }

        /**
         * isCompleteWord - check if the word is completer
         * @return - true if complete and false otherwise
         */
        public boolean isCompleteWord(){
            return this.isCompleteWord;
        }
    }

    private final TrieNode rootNode;

    public Trie(){
        rootNode = new TrieNode();
    }

    /**
     * getRootNode - returns the root node
     * @return - root node
     */
    public TrieNode getRootNode(){
        return this.rootNode;
    }

    /**
     * insert -inserts the given word
     * @param word - word to be inserted
     */
    public void insert(String word){
        TrieNode curr = rootNode;
        int index;

        for(char c: word.toCharArray()){
            index = c - 'a';
            if(curr.dictionary[index] == null) curr.setChild(new TrieNode(),index);
            curr = curr.getChild(index);
        }
        curr.setCompleteWord(true);
    }

    /**
     * search - search the given word
     * @param word - word to be searched
     * @return - true if word exists, false otherwise
     */
    public boolean search(String word){
        TrieNode curr = rootNode;
        int index;

        for (char c : word.toCharArray()){
            index = c - 'a';
            if(curr.getChild(index) == null) return false;
            curr = curr.getChild(index);
        }
        return curr.isCompleteWord;
    }

    /**
     * reverseWord - reverse the given word
     * @param word - word to be reversed
     * @return - reversed word
     */
    public String reverseWord(String word){
        StringBuilder words = new StringBuilder();
        int length = word.length() - 1;
        for (int i = length; i >= 0; i--) {
                words.append(word.charAt(i));
        }
        return words.toString();
    }

    /**
     * getLastNode - get the last node of the word
     * @param word - word to get last node of
     * @return - the last node of the given word
     */
    public TrieNode getLastNode(String word){
        TrieNode curr = rootNode;
        int index = word.charAt(0) - 'a';
        System.out.println("The character index is: " + index);
        if(word.length() == 1) return curr.getChild(word.charAt(0) - 'a');
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int j = c - 'a';
            System.out.println("This is c :" + c);
            System.out.println("This is i: "+ j);
            if(curr.getChild(j) == null) break;
            else curr = curr.getChild(j);
        }
        return curr;
    }
}
