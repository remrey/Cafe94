package sample.CustomerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.DBManager;
import sample.Item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A controller for the customer's Menu screen. This allows the customer
 * to view items on the menu.
 * @author George, Emre
 * @version 1.0
 */

public class MenuController {

    @FXML private ComboBox<String> tableList;
    @FXML private ComboBox<Item> dailyList;
    @FXML private ComboBox<Item> starterList;
    @FXML private ComboBox<Item> mainList;
    @FXML private ComboBox<Item> sideList;
    @FXML private ComboBox<Item> dessertList;
    @FXML private ComboBox<Item> drinkList;


    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public ObservableList<Item> dailyObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> starterObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> mainObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> sideObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> dessertObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> drinkObservableList = FXCollections.observableArrayList();
    public ObservableList<Item> resultList = FXCollections.observableArrayList();

    /**
     * Initialize function connects to the database and sets the list of
     * viewable items from the menu.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void initialize() throws SQLException {
        String query = "SELECT * FROM menu";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            fillMenuLists(rs);
            dailyList.setItems(dailyObservableList);
            starterList.setItems(starterObservableList);
            mainList.setItems(mainObservableList);
            sideList.setItems(sideObservableList);
            dessertList.setItems(dessertObservableList);
            drinkList.setItems(drinkObservableList);
        } catch (Exception e ) {
            System.out.println("Reason of the problem is: " + e);
        }

    }
    /**
     * Fills the menu lists based in the menu database.
     * @param rs Result of the query.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void fillMenuLists(final ResultSet rs) throws SQLException {
        while (rs.next()) {
            Item temp = new Item();
            temp.setId(rs.getInt("id"));
            temp.setItemName(rs.getString("name"));
            temp.setPrice(rs.getDouble("price") );
            String type = rs.getString("type");
            temp.setType(type);
            if (type.equals("dailySpecial")) {
                dailyObservableList.add(temp);
            } else if (type.equals("starter")) {
                starterObservableList.add(temp);
            } else if (type.equals("main")) {
                mainObservableList.add(temp);
            } else if (type.equals("side")) {
                sideObservableList.add(temp);
            } else if (type.equals("dessert")) {
                dessertObservableList.add(temp);
            } else if (type.equals("drink")) {
                drinkObservableList.add(temp);
            }
        }
    }

    /**
     * Button press to take customer to Takeaway order screen
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void placeTakeawayOrderButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/TakeawayOrder.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * Button press to take customer to Delivery order screen
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void placeDeliveryOrderButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/DeliveryOrder.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * The following function can be used to go to the home screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void homeButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/CustomerHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * The following function can be used to go to the booking screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void bookingButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/CustomerCreateBooking.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * The following function can be used to go to the profile screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void profileButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/PersonOverview.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * The following function can be used to sign out of the system.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void logoutButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
