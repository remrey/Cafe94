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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Booking;
import sample.DBManager;
import sample.UserDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * A controller for the Customer Booking FXML that allows the
 * creation and viewing of bookings by a customer.
 * @author Lorcan, George
 * @version 1.0
 */

public class CustomerCreateBookingController implements Initializable {

    @FXML DatePicker bookingDate;
    @FXML ComboBox<String> bookingExtended;
    @FXML ComboBox<String> bookingTime;
    @FXML ComboBox<Integer> bookingNumberOfGuests;
    @FXML Button bookingButton;
    @FXML Label dateLabel;
    @FXML Label extendedLabel;
    @FXML Label timeLabel;
    @FXML Label numberLabel;
    @FXML TableView<Booking> customerBookings;
    @FXML TableColumn<Booking, LocalDate> dateColumn;
    @FXML TableColumn<Booking, String> timeColumn;
    @FXML TableColumn<Booking, Integer> guestsColumn;
    @FXML TableColumn<Booking, Boolean> approvedColumn;
    @FXML TableColumn<Booking, Integer> bookingIDColumn;


    private ObservableList<String> timeList = FXCollections.observableArrayList(
            "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
            "21:00");
    private ObservableList<String> extendedList = FXCollections.observableArrayList("Yes", "No");
    private ObservableList<Integer> numberOfGuestsList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    /**
     * Finds the customer details from the users database.
     * @throws SQLException Throws if SQLite query fails.
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        bookingTime.setItems(timeList);
        bookingExtended.setItems(extendedList);
        bookingNumberOfGuests.setItems(numberOfGuestsList);
        try{
            //showBookings();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Button press to show bookings.
     * @throws IOException Throws if input fails.
     * @throws SQLException Throws if SQLite query fails.
     * @param event
     */
    public void showBookings(ActionEvent event) throws IOException, SQLException {
        int userID = sample.UserDetails.getInstance().getUserID();
        String query = "SELECT date, timePeriod, numberOfGuests, approved, bookingID FROM booking WHERE customerID = ? AND date > ? ";
        LocalDate date = LocalDate.now();
        Connection connection = DBManager.DBConnection();
        ResultSet rs = null;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, UserDetails.getInstance().getUserID());
            pst.setDate(2, Date.valueOf(date));
            rs = pst.executeQuery();
            ObservableList<Booking> bookings = getBookingList(rs);
            dateColumn.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("date"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<Booking, String>("timePeriod"));
            guestsColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("numberOfGuests"));
            approvedColumn.setCellValueFactory(new PropertyValueFactory<Booking, Boolean>("approved"));
            bookingIDColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingID"));
            customerBookings.setItems(bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            pst.close();
            connection.close();
            rs.close();
        }
    }

    /**
     * Button press to create a booking.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void createBooking(ActionEvent event) throws IOException, SQLException {
        String query = "INSERT INTO booking (date, timePeriod, numberOfGuests, extended, approved, checked, customerID) VALUES (?,?,?,?,0,0,?);";

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
                pst.setInt(5, UserDetails.getInstance().getUserID());
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
            finally {

            }
        }
    }

    /**
     *
     * @param event used to get information in current scene
     * @throws IOException Throws if input fails
     * @throws SQLException Throws if SQLite query fails.
     */
    public void deleteBooking(ActionEvent event) throws IOException, SQLException {
        connection = DBManager.DBConnection();
        if (customerBookings.getSelectionModel().getSelectedCells().isEmpty()) {
            //do nothing
        } else {
            int id = customerBookings.getSelectionModel().getSelectedItem().getBookingID();
            String deleteBooking = "DELETE FROM booking where bookingID = " + id;
            PreparedStatement pr = null;
            try{
                pr = connection.prepareStatement(deleteBooking);
                pr.executeUpdate();
                showBookings(event);
            } catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                pr.close();
                connection.close();
                rs.close();
            }
        }
    }

    /**
     * Gets the list of bookings in the system.
     * @param rsBooking Result of the query.
     * @return Result of the query.
     * @throws SQLException Throws if SQLite query fails.
     */
    private ObservableList<Booking> getBookingList(ResultSet rsBooking) throws SQLException {
        ObservableList<Booking> tempBookingList = FXCollections.observableArrayList();
        while(rsBooking.next()){
            Booking temp = new Booking();
            temp.setDate(rsBooking.getString("date"));
            temp.setTimePeriod(rsBooking.getString("timePeriod"));
            temp.setNumberOfGuests(rsBooking.getInt("numberOfGuests"));
            temp.setApproved(rsBooking.getBoolean("approved"));
            temp.setBookingID(rsBooking.getInt("bookingID"));
            tempBookingList.add(temp);
        }
        return tempBookingList;
    }

    /**
     * The following function act as a home button to take the customer to home screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * The following function can be used to go to the menu screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void menuButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * The following function can be used to go to profile screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void profileButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/personOverview.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * The following function can be used to sign out of the system.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
