package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class addEmployeeController {
    @FXML private TextField username;
    @FXML private TextField lastName;
    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label typeError;
    @FXML private Label successLabel;
    @FXML private ComboBox<String> typeComboBox;


    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<String>  typeComboBoxList = FXCollections.observableArrayList("Chef","Delivery Driver","Waiter");

    @FXML
    public void initialize(){
        typeComboBox.setItems(typeComboBoxList);
    }

    public void onPressCancel(ActionEvent event) throws IOException{
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onPressButton(ActionEvent event) throws IOException, SQLException {

        String query = "INSERT INTO staff(firstName, lastName,type, hoursToWork, totalHoursWorked) VALUES(?,?,?,?,?)";
        String sql = "CREATE TABLE IF NOT EXISTS staff(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName varchar(255) NOT NULL,\n"
                + "	lastName varchar(255) NOT NULL, \n"
                + " type varchar(255) NOT NULL, \n"
                + " hoursToWork int NOT NULL, \n"
                + " totalHoursWorked int NOT NULL \n"
                + ");";
        connection = DBManager.DBConnection();
        if(username.getText().isEmpty()) firstNameError.setVisible(true);
        else firstNameError.setVisible(false);
        if(lastName.getText().isEmpty()) lastNameError.setVisible(true);
        else lastNameError.setVisible(false);
        if(typeComboBox.getSelectionModel().isEmpty()) typeError.setVisible(true);
        else typeError.setVisible(false);
        if(!username.getText().isEmpty() && !lastName.getText().isEmpty() && !typeComboBox.getSelectionModel().isEmpty()) {
            try{
                PreparedStatement tableCheck = connection.prepareStatement((sql));
                tableCheck.executeUpdate();
                pst = connection.prepareStatement(query);
                pst.setString(1,username.getText());
                pst.setString(2,lastName.getText());
                pst.setString(3,typeComboBox.getSelectionModel().getSelectedItem());
                pst.setInt(4,0);
                pst.setInt(5,0);
                pst.executeUpdate();
                successLabel.setText(username.getText() + " " + lastName.getText() + " " + (successLabel.getText()));
                successLabel.setVisible(true);
                username.clear();
                lastName.clear();
                typeComboBox.valueProperty().set(null);
            }
            catch(Exception e){
                System.out.println("Error occured here: " + e);
            }
            finally {
                pst.close();
                connection.close();
            }
        }


    }
}
