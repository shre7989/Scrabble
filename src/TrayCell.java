/**
 * @author: Mausam Shrestha
 * @project: Scrabble
 * @Date: 05/04/2021
 * @UNMId: 101865530
 */

package scrabble;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;

/**
 * TrayCell - individual cell of player's tray
 */
public class TrayCell extends Cell{
    private boolean clicked;

    /**
     * Constructor
     * @param row - tray row
     * @param col - tray col (always 0)
     * @param width - width for the cell
     * @param height - height for the cell
     * @param text - text value for the cell
     * @param manager - game manager
     */
    public TrayCell(int row, int col, double width, double height, char text, Manager manager) {
        super(row, col, width, height, text, manager);
        this.clicked = false;
        this.setOnAction(event -> {
            performTrayAction();
        });
    }

    /**
     * performTrayAction - performs necessary action when a tray cell is clicked
     */
    public void performTrayAction(){
        Manager manager = this.getManager();
        boolean select = manager.isSelect();
        char assignedVal = manager.getAssignedVal();
        char letter = getLetter();
        char assign = getAssign();
        TrayGUI trayGUI = manager.getTrayGUI();

        if(!clicked && select && (assignedVal == '-')){
            if(this.getLetter() == ' ') configureBlank();
            clicked = true;
            assign = getLetter();
            this.setText("-");
            manager.setAssignedVal(assign);
            manager.setAssignedTile(getTile());
            trayGUI.updateSelectedTrayCells(this);
        }
        if(!select) {
            Alert notSelected = new Alert(Alert.AlertType.INFORMATION);
            notSelected.getDialogPane().setContentText("Please select the type of move you want to make first!!");
            notSelected.setTitle("Illegal Move");
            notSelected.show();
        }
    }

    /**
     * configureBlank - helps to configure blank tiles
     */
    public void configureBlank(){
        char letter;
        BooleanBinding isInvalid;

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Configure blank tile!!");
        inputDialog.setHeaderText("Input any character [a - z]");
        Button ok = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = inputDialog.getEditor();
        isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
        ok.disableProperty().bind(isInvalid);
        inputDialog.showAndWait();
    }

    /**
     * isInvalid - function for boolean binding that checks if the input is a letter
     * @param text - text in the input field
     * @return - true if letter and false otherwise
     */
    private Boolean isInvalid(String text) {
        char letter;
        if(text.isEmpty()) return true;
        if(text.length() > 1) return true;
        letter = text.charAt(0);
        if(letter < 'a' || letter > 'z') return true;
        this.setLetter(letter);
        return false;
    }

    /**
     * lastState - helps to revert to last state
     */
    public void lastState(){
        clicked = false;
        setAssign('-');
        this.setText(Character.toString(getLetter()));
    }

    /**
     * setClicked - sets if tray cell was clicked
     * @param flag - flag that determines if the cell was clicked
     */
    public void setClicked(boolean flag){
        this.clicked = flag;
    }


}
