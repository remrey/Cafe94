package sample.CustomerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBManager;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class customerCreateBookingController implements Initializable {

    @FXML DatePicker bookingDate;
    @FXML ComboBox<String> bookingExtended;
    @FXML ComboBox<String> bookingTime;
    @FXML ComboBox<Integer> bookingNumberOfGuests;
    @FXML Button bookingButton;
    @FXML Label dateLabel;
    @FXML Label extendedLabel;
    @FXML Label timeLabel;
    @FXML Label numberLabel;

    ObservableList<String> timeList = FXCollections.observableArrayList(
            "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
            "21:00");

    ObservableList<String> extendedList = FXCollections.observableArrayList("Yes", "No");

    ObservableList<Integer> numberOfGuestsList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override public void initialize(URL url, ResourceBundle rb) {
        bookingTime.setItems(timeList);
        bookingExtended.setItems(extendedList);
        bookingNumberOfGuests.setItems(numberOfGuestsList);
    }

    // need to change below for customerID
    public void createBooking(ActionEvent event) throws IOException, SQLException {
        String query = "INSERT INTO booking (date, timePeriod, numberOfGuests, extended, approved, checked, customerID) VALUES (?,?,?,?,0,0,0);";

        if (bookingDate.getValue() == null) {
            dateLabel.setText("Please enter booking date");
            dateLabel.setVisible(true);
        } else {
            dateLabel.setVisible(false);
        }
        if (bookingExtended.getValue() == null) {
            extendedLabel.setVisible(true);
        } else {
            extendedLabel.setVisible(false);
        }
        if (bookingTime.getValue() == null) {
            timeLabel.setVisible(true);
        } else {
            timeLabel.setVisible(false);
        }
        if (bookingNumberOfGuests.getValue() == null) {
            numberLabel.setVisible(true);
        } else {
            numberLabel.setVisible(false);
        }
        if (bookingDate.getValue() != null
                && bookingExtended.getValue() != null
                && bookingTime.getValue() != null
                && bookingNumberOfGuests != null) {
            try {
                connection = DBManager.DBConnection();
                pst = connection.prepareStatement(query);
                LocalDate date = bookingDate.getValue();
                pst.setObject(1, date);
                pst.setString(2, bookingTime.getValue());
                pst.setInt(3, (Integer) bookingNumberOfGuests.getValue());
                if(bookingExtended.getValue().equals("Yes")){
                    pst.setBoolean(4, TRUE);
                }else{
                    pst.setBoolean(4, FALSE);
                }
                if(bookingDate.getValue().isBefore(LocalDate.now())){
                    dateLabel.setText("Date must be for future booking");
                    dateLabel.setVisible(true);
                }else if(bookingTime.getValue().equals("21:00") && bookingExtended.getValue().equals("Yes")){
                    extendedLabel.setText("Cannot extend, restaurant closes at 22:00");
                    extendedLabel.setVisible(true);
                }
                else {
                    pst.executeUpdate();
                    pst.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void menuButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void profileButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/personOverview.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}