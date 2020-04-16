package sample.ChefScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import sample.DBManager;

import java.sql.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.regex.Pattern;

public class chefChangeItemController {

    @FXML private TextField itemID;
    @FXML private TextField itemName;
    @FXML private TextField itemPrice;
    @FXML private Label itemIDEmptyError;
    @FXML private Label itemNameEmptyError;
    @FXML private Label itemPriceError;
    @FXML private CheckBox dailySpecial;
    @FXML private Label successLabel;
    @FXML private Label validIDError;
    @FXML private Button addButton;



    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void onPressCancel(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private boolean isDecimal(String strNum){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isInt(String text) {
        return text.matches("[0-9]*");
    }


    public void onPressButton(ActionEvent event) throws IOException, SQLException {
        itemIDEmptyError.setVisible(false);
        itemNameEmptyError.setVisible(false);
        itemPriceError.setVisible(false);
        validIDError.setVisible(false);
        successLabel.setVisible(false);

        String query = "UPDATE menu SET name = ?, price = ?, dailySpecial = ? WHERE id = ?;";
        String updateTable = "Update menu SET dailySpecial = FALSE WHERE dailySpecial = TRUE;";
        connection = DBManager.DBConnection();

        if (itemID.getText().isEmpty()) {
            itemIDEmptyError.setText("This field cannot be left blank");
            itemIDEmptyError.setVisible(true);
        } else {
            if (Integer.parseInt(itemID.getText()) < 1 || Integer.parseInt(itemID.getText()) > 20){
                validIDError.setText("Please enter a valid id");
                validIDError.setVisible(true);
            } else {
                itemIDEmptyError.setVisible(false);
            }
        }
        if (!isInt(itemID.getText())){
            validIDError.setText("Please enter a valid id");
            itemIDEmptyError.setVisible(true);
        } else {
            itemIDEmptyError.setVisible(false);
        }
        if (itemName.getText().isEmpty()){
            itemNameEmptyError.setVisible(true);
        } else {
            itemNameEmptyError.setVisible(false);
        }
        if (itemPrice.getText().isEmpty()){
            itemPriceError.setText("This field cannot be left blank");
            itemPriceError.setVisible(true);
        } else {
            itemPriceError.setVisible(false);
        }
        if(!isDecimal(itemPrice.getText())){
            itemPriceError.setText("Please enter numeric value");
            itemPriceError.setVisible(true);
        } else {
            itemPriceError.setVisible(false);
        }
        if(!itemName.getText().isEmpty()
                && !itemID.getText().isEmpty()
                && !itemPrice.getText().isEmpty()
                && isInt(itemID.getText())
                && isDecimal(itemPrice.getText())
                && Integer.parseInt(itemID.getText()) > 0
                && Integer.parseInt(itemID.getText()) < 21
        ) {
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, itemName.getText());
                pst.setDouble(2, Double.parseDouble(itemPrice.getText()));
                pst.setBoolean(3, dailySpecial.isSelected());
                pst.setInt(4, Integer.parseInt(itemID.getText()));
                if (dailySpecial.isSelected()) {
                    PreparedStatement update = connection.prepareStatement(updateTable);
                    update.executeUpdate();
                }
                pst.executeUpdate();
                successLabel.setText(itemName.getText() + " has been updated on the system");
                successLabel.setVisible(true);

            }
            catch (SQLException e){
                e.printStackTrace();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}