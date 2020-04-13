package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signUpController {
    @FXML private TextField signUpFirstName;
    @FXML private TextField signUpLastName;
    @FXML private TextField signUpPassword;
    @FXML private TextField signUpUserName;
    @FXML private Label userNameEmptyError;
    @FXML private Label userNameTakenError;
    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label passwordError;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void onPressButton(ActionEvent event) throws SQLException {
        String query = "INSERT INTO users(userName,firstName, lastName, password,type) VALUES(?,?,?,?,'Customer')";
        connection = DBManager.DBConnection();


        if(signUpFirstName.getText().isEmpty()) firstNameError.setVisible(true);
        else firstNameError.setVisible(false);
        if(signUpLastName.getText().isEmpty()) lastNameError.setVisible(true);
        else lastNameError.setVisible(false);
        if(signUpPassword.getText().isEmpty()) passwordError.setVisible(true);
        else passwordError.setVisible(false);
        if(signUpUserName.getText().isEmpty()) userNameEmptyError.setVisible(true);
        else userNameEmptyError.setVisible(false);
        userNameTakenError.setVisible(false);

        if(!signUpFirstName.getText().isEmpty()
                && !signUpLastName.getText().isEmpty()
                && !signUpPassword.getText().isEmpty()
                && !signUpUserName.getText().isEmpty()){
            try{
                pst = connection.prepareStatement(query);
                pst.setString(1,signUpUserName.getText());
                pst.setString(2,signUpFirstName.getText());
                pst.setString(3,signUpLastName.getText());
                pst.setString(4,signUpPassword.getText());
                pst.executeUpdate();
                pst.close();
                connection.close();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch(SQLException e) {
                userNameTakenError.setVisible(true);
            }
            catch(Exception e){
                System.out.println("Problem is here " + e);
            }

        }

    }

}
