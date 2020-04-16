package sample.ManagerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class editEmployeeController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private Label successLabel;

    private mainScreenController ms;
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String type;
    int id;
    ObservableList<String> typeComboBoxList = FXCollections.observableArrayList("Chef","Delivery Driver","Waiter");

    @FXML
    public void initialize() throws SQLException {
        successLabel.setVisible(false);
        successLabel.setStyle("-fx-text-fill: green");
        typeComboBox.setItems(typeComboBoxList);
        id = ms.employeeIdFromTable;

        String query = "SELECT * from staff where id = ?;";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            rs = pst.executeQuery();

            firstName.setText(rs.getString("firstName"));
            lastName.setText(rs.getString("lastName"));
            type = rs.getString("type");

        }catch (Exception e){
            System.out.println("Problem is in here; " + e);
        }finally {
            pst.close();
            connection.close();
        }
    }

    public void onButtonPressed(ActionEvent event) throws SQLException {
        String editFirstName = firstName.getText();
        String editLastName = lastName.getText();
        String editType = typeComboBox.getSelectionModel().getSelectedItem();
        int id = ms.employeeIdFromTable;
        String query = "UPDATE staff SET firstName = ? , lastName = ?, type = ? WHERE id = ?;";


        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setString(1,editFirstName);
            pst.setString(2,editLastName);
            pst.setString(3,editType);
            pst.setInt(4,id);
            pst.executeUpdate();
            successLabel.setVisible(true);

        }catch (Exception e){
            System.out.println("Problem is in here; " + e);
        }finally {
            pst.close();
            connection.close();

        }


    }
}
