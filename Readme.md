# Scrabble

![scrabble](https://user-images.githubusercontent.com/55064602/147198918-f5b7858b-2c2e-4edb-85ca-e4f25f86a859.png)

## Game Rules:
* The scrabble rules are based on [Scrabble Game Rules (Hasbro Official Board Game Rules](https://scrabble.hasbro.com/en-us/rules)

## Initial Setup:
   - 100 tiles in the Tile Bag.
   - Each player automatically draws 7 tile from the tile bag in the start of the game.
   - Tiles are scored based on scrabble_tiles.txt file located on the resource folder.
   - Dictionary.txt is the default dictionary file located in the resource folder.
    
## Gameplay Walkthrough:
### Play:
   - You must select the type of move you are going to play in every turn.
   - You can place tiles sequentially from top to bottom or left to right.
   - Click any tile on the player’s tray , and click on any cell in the scrabble grid to place the tile.
   - The play passes to computer if you make a valid move or if you pressed the pass button.
     
### Exchange:
   - Press the exchange button to swap your tiles from the tile bag. You can only perform this action 2 times per game.
   - If the tile bag contains fewer than 7 tiles, pressing the exchange button will swap exactly the count of tiles remaining in the tile bag.
   
### Pass:
   - Press the pass button to pass the play to the next player.
   - If both players(human and computer), pass turns in succession, the game will end.
   - Pressing the pass button will account your score to 0 for that turn.

## Gameplay Mechanics:
   - The first player(human) combines two or more of his or her letters to form a word and places it on the board to read either across or down with one       letter on the center square. Diagonal words are not allowed.

   - Complete your turn by counting and announcing your score for that turn. Then draw as many new letters as you played; always keep seven tiles on your rack, as long as there are enough tiles left in the bag.

   - Play passes to the computer. You can skip your turn by passing the play to computer using the pass button, however if both player and computer pass turn in succession, the game will end.

   - New words may be formed by:•Adding one or more letters to a word or letters already on the board.•Placing a word at right angles to a word already on the board. The new word must use one of the letters already on the board or must add a letter to it.•Placing a complete word parallel to a word already played so that adjacent letters also form complete words.

   - No tile may be shifted or replaced after it has been played and scored.

   - Blanks: The two blank tiles may be used as any letters. When playing a blank, you must state which letter it represents. It remains that letter for the rest of the game.

   - You may use a turn to exchange all you letters. To do this, press the exchange button. Both the computer and the player can exchange only two times in a game.

   - The game ends when all letters have been drawn and one player uses his or her last letter; or when all possible plays have been made.

## Scoring:

   - The score value of each letter is indicated by a number at the bottom of the tile. The score value of a blank is zero.

   - The score for each turn is the sum of the letter values in each word(s) formed or modified on that turn, plus the additional points obtained from placing letters on Premium Squares.

   - Premium Letter Squares: A light blue square doubles the score of a letter placed onit; a dark blue square triples the letter score.

   - Premium Word Squares: The score for an entire word is doubled when one of its letters is placed on a pink square: it is tripled when one of its letters is placed on a red square. Include premiums for double or triple letter values, if any, before doubling or tripling the word score. If a word is formed that covers two premium word squares, the score is doubled and then re-doubled (4 times the letter count), or tripled and then re-tripled(9 times the letter count). NOTE: the center square is a pink square, which doubles the score for the first word.

   - Letter and word premiums count only on the turn in which they are played. On later turns, letters already played on premium squares count at face value.

   - When a blank tile is played on a pink or red square, the value of the word is doubled or tripled, even though the blank itself has no score value.

   - When two or more words are formed in the same play, each is scored. The common letter is counted (with full premium value, if any) for each word.

   - BINGO! If you play seven tiles on a turn, it’s a Bingo. You score a premium of 50points after totaling your score for the turn.
   
   - Unplayed Letters: When the game ends, each player’s score is reduced by the sum of his or her unplayed letters. In addition, if a player has used all of his or her letters,the sum of the other players’ unplayed letters is added to that player’s score.
    
   - The player with the highest final score wins the game. In case of a tie, the player with the highest score before adding or deducting unplayed letters wins.

## Instructions
     - When cloning the repo, place the .txt dictionary files in the same root folder.
