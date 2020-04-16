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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class addHourController {

    @FXML ComboBox<String> hourComboBox;
    @FXML TextField changeHoursToWorkLabel;
    @FXML Label hourToWorkChanged;
    @FXML Label hourAdded;

    private mainScreenController ms;
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int id;
    String firstName;
    String lastName;
    int totalHoursWorked;


    ObservableList<String> typeComboBoxList = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");


    @FXML
    public void initialize() throws SQLException {
        hourComboBox.setItems(typeComboBoxList);
        id = ms.employeeIdFromTable;

        String query = "SELECT * from staff where id = ?;";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            totalHoursWorked = rs.getInt("totalHoursWorked");

            hourAdded.setStyle("-fx-text-fill: red");
            hourAdded.setText(firstName + " " + lastName);

        }catch (Exception e){
            System.out.println("Problem is in here; " + e);
        }finally {
            pst.close();
            connection.close();
        }
    }

    public void onPressCancel(ActionEvent event) throws IOException, SQLException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    public void onChangePressButton(ActionEvent event){
        int hoursToWork = 0;
        try{
            hoursToWork= Integer.parseInt(changeHoursToWorkLabel.getText());
        }
        catch (Exception e){
            hourToWorkChanged.setText("Please input an real value !");
            hourToWorkChanged.setStyle("-fx-text-fill: red");
        }
        String query = "UPDATE staff SET hoursToWork = ? WHERE id =?; ";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,hoursToWork);
            pst.setInt(2,id);
            pst.executeUpdate();
            hourToWorkChanged.setText("New hours to work for  " + firstName + " " + lastName
                    + "is " +hoursToWork);
            hourToWorkChanged.setStyle("-fx-text-fill: green");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddHourPressButton(ActionEvent event) throws SQLException, IOException {
        int hoursToAdd= Integer.parseInt(hourComboBox.getSelectionModel().getSelectedItem());
        totalHoursWorked += hoursToAdd;
        String query = "UPDATE staff SET totalHoursWorked = ? WHERE id =?; ";

        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,totalHoursWorked);
            pst.setInt(2,id);
            pst.executeUpdate();
            hourAdded.setStyle("-fx-text-fill: green");
            hourAdded.setText("Total of " + hoursToAdd
                    + " hours has been added to " + firstName + " " + lastName );


        }catch (Exception e){
            System.out.println("Problem is in here; " + e);
        }finally {
            pst.close();
            connection.close();
        }

    }
}
