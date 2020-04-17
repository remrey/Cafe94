package sample.ChefScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBManager;
import java.sql.*;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * A controller for the screen for the chef to change the properties of an item.
 * @author Lorcan, George
 * @version 1.0
 */
public class ChefChangeItemController {
    @FXML private TextField itemID;
    @FXML private TextField itemName;
    @FXML private TextField itemPrice;
    @FXML private TextField itemType;
    @FXML private Label itemIDEmptyError;
    @FXML private Label itemNameEmptyError;
    @FXML private Label itemPriceError;
    @FXML private Label successLabel;
    @FXML private Label validIDError;
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    /**
     * A method to check whether a given input is a decimal.
     * @param strNum the input entered by the chef
     * @return whether the input is a decimal
     */
    private boolean isDecimal(final String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    /**
     * A method that checks whether the input is an integer.
     * @param text the input entered by the chef
     * @return whether
     */
    private boolean isInt(final String text) {
        return text.matches("[0-9]*");
    }

    /**
     * A method that validates whether an input
     * is valid or not and then updates the menu.
     * @param event the button click of the user
     * @throws IOException
     * @throws SQLException
     */
    public void onPressButton(final ActionEvent event) throws IOException, SQLException {
        itemIDEmptyError.setVisible(false);
        itemNameEmptyError.setVisible(false);
        itemPriceError.setVisible(false);
        validIDError.setVisible(false);
        successLabel.setVisible(false);
        String query = "UPDATE menu SET name = ?, price = ?, type = ? WHERE id = ?;";
        connection = DBManager.DBConnection();
        if (!isInt(itemID.getText())) {
            validIDError.setText("Please enter a valid id");
            validIDError.setVisible(true);
        } else {
            itemIDEmptyError.setVisible(false);
        }
        if (itemID.getText().isEmpty()) {
            itemIDEmptyError.setText("This field cannot be left blank");
            itemIDEmptyError.setVisible(true);
        } else {
            if (isInt(itemID.getText())) {
                if (Integer.parseInt(itemID.getText()) < 1 ||           // number 1 represents the lowest itemID in database
                        Integer.parseInt(itemID.getText()) > 20) {       // number 20 represents highest itemID in the database
                    validIDError.setText("Please enter a valid id");
                    validIDError.setVisible(true);
                } else {
                    itemIDEmptyError.setVisible(false);
                }
            }
        }
        if (itemName.getText().isEmpty()) {
            itemNameEmptyError.setVisible(true);
        } else {
            itemNameEmptyError.setVisible(false);
        }
        if (itemPrice.getText().isEmpty()) {
            itemPriceError.setText("This field cannot be left blank");
            itemPriceError.setVisible(true);
        } else {
            itemPriceError.setVisible(false);
        }
        if (!isDecimal(itemPrice.getText())) {
            itemPriceError.setText("Please enter numeric value");
            itemPriceError.setVisible(true);
        } else {
            itemPriceError.setVisible(false);
        }
        if (!itemName.getText().isEmpty()
                && !itemID.getText().isEmpty()
                && !itemPrice.getText().isEmpty()
                && !itemType.getText().isEmpty()
                && isInt(itemID.getText())
                && isDecimal(itemPrice.getText())
                && Integer.parseInt(itemID.getText()) > 0       // checks whether itemID is valid between 0 and 20
                && Integer.parseInt(itemID.getText()) < 21
        ) {
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, itemName.getText());
                pst.setDouble(2, Double.parseDouble(itemPrice.getText()));
                pst.setString(3, itemType.getText());
                pst.setInt(4, Integer.parseInt(itemID.getText()));
                pst.executeUpdate();
                successLabel.setText(itemName.getText()
                        + " has been updated on the system");
                successLabel.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * A method to return to the previous screen.
     * @param event button click that returns user to main screen
     * @throws IOException
     */
    public void returnButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/ChefScreen/chefMainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
