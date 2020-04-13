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
    @FXML private TextField userName;
    @FXML private TextField name;
    @FXML private TextField lastName;
    @FXML private Label userNameTakenError;
    @FXML private Label userNameEmptyError;
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

        String addUserQuery = "INSERT INTO users(userName,firstName,lastName,password,type) VALUES(?,?,?,1234,?)";
        connection = DBManager.DBConnection();
        if(name.getText().isEmpty()) firstNameError.setVisible(true);
        else firstNameError.setVisible(false);
        if(lastName.getText().isEmpty()) lastNameError.setVisible(true);
        else lastNameError.setVisible(false);
        if(typeComboBox.getSelectionModel().isEmpty()) typeError.setVisible(true);
        else typeError.setVisible(false);
        if(userName.getText().isEmpty()) userNameEmptyError.setVisible(true);
        else userNameEmptyError.setVisible(false);
        if(!name.getText().isEmpty()
                && !lastName.getText().isEmpty()
                && !typeComboBox.getSelectionModel().isEmpty()
                && !userName.getText().isEmpty()
        ) {
            try{
                userNameTakenError.setVisible(false);
                PreparedStatement tableCheck = connection.prepareStatement((sql));
                tableCheck.executeUpdate();
                PreparedStatement userNameCheck = connection.prepareStatement(addUserQuery);
                userNameCheck.setString(1,userName.getText());
                userNameCheck.setString(2,name.getText());
                userNameCheck.setString(3,lastName.getText());
                userNameCheck.setString(4,typeComboBox.getSelectionModel().getSelectedItem());
                userNameCheck.executeUpdate();
                pst = connection.prepareStatement(query);
                pst.setString(1,name.getText());
                pst.setString(2,lastName.getText());
                pst.setString(3,typeComboBox.getSelectionModel().getSelectedItem());
                pst.setInt(4,0);
                pst.setInt(5,0);
                pst.executeUpdate();
                successLabel.setText(name.getText() + " " + lastName.getText() + " " + (successLabel.getText()));
                successLabel.setVisible(true);
                name.clear();
                lastName.clear();
                userName.clear();
                typeComboBox.valueProperty().set(null);
            }
            catch(SQLException e){
                userNameTakenError.setVisible(true);
            }
            catch(Exception e ){
                System.out.println("Error has occured here: " + e);
            }

        }


    }
}
