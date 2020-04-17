package sample.CustomerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBManager;
import sample.Order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * A controller for the customer profile screen.
 * This allows the customer to view
 * order history as well as their ID and details.
 * @author George, Li, Luis
 * @version 1.0
 */

public class PersonOverviewController {

    @FXML private TableView<Order> orderHistoryView;
    @FXML private TableColumn<Order, LocalDateTime> orderDateColumn;
    @FXML private TableColumn<Order, Integer> orderNoColumn;
    @FXML private TableColumn<Order, String> orderTypeColumn;
    @FXML private TableColumn<Order, String> itemColumn;
    @FXML private Label userNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label customerIDLabel;

    Connection connection = null;
    ResultSet rsOrders = null;
    PreparedStatement pst = null;

    /**
     * The following function can be used to go to the home screen.
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
     * The following function can be used to go to the booking screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void bookingButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerCreateBooking.fxml"));
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

    /**
     * Finds the customer details from the users database.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void initialize() throws SQLException {
        int userID = sample.UserDetails.getInstance().getUserID();

        sample.UserDetails.getInstance().getUserID();
        userNameLabel.setText(sample.UserDetails.getInstance().getUsern());
        firstNameLabel.setText(sample.UserDetails.getInstance().getUserFirst());
        lastNameLabel.setText(sample.UserDetails.getInstance().getUserLast());
        customerIDLabel.setText("" + userID);


        String query = "SELECT orderNo, itemName, orderType FROM orders WHERE customerID =" + userID;
        connection = DBManager.DBConnection();
        pst = connection.prepareStatement(query);
        rsOrders = pst.executeQuery();
        ObservableList<Order> itemList = getOrderList(rsOrders);
        itemColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
        orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
        orderHistoryView.setItems(itemList);
    }

    /**
     * Gets the list of orders from specific customer and adds it to the database.
     * @param rsOrders Result of the query.
     * @return Result of the query.
     * @throws SQLException Throws if SQLite query fails.
     */
    private ObservableList<Order> getOrderList(ResultSet rsOrders) throws SQLException {
        ObservableList<Order> tempItemList = FXCollections.observableArrayList();
        while(rsOrders.next()){
            Order temp = new Order();
            temp.setOrderNo(rsOrders.getInt("orderNo"));
            temp.setItemName(rsOrders.getString("itemName"));
            temp.setOrderType(rsOrders.getString("orderType"));
            tempItemList.add(temp);
        }
        return tempItemList;
    }
}
