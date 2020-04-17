package sample.ChefScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBManager;
import sample.Order;
import sample.Item;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class which shows the main screen for the chef.
 * @author George, Luis, Emre, Lorcan.
 * @version 1.0
 */
public class ChefMainScreenController {

    @FXML private ComboBox<Item> dailyList;
    @FXML private ComboBox<Item> starterList;
    @FXML private ComboBox<Item> mainList;
    @FXML private ComboBox<Item> sideList;
    @FXML private ComboBox<Item> dessertList;
    @FXML private ComboBox<Item> drinkList;
    @FXML private Button refreshButton;
    @FXML private Button completedButton;
    @FXML private TableView<Order> chefOrderView;
    @FXML private TableColumn<Order, Integer> orderIDColumn;
    @FXML private TableColumn<Order, Integer> orderNoColumn;
    @FXML private TableColumn<Order, String> orderTypeColumn;
    @FXML private TableColumn<Order, String> itemNameColumn;
    private Connection connection = null;
    private Connection connection2 = null;
    private ResultSet rs = null;
    private ResultSet rsOrders = null;
    private PreparedStatement pst = null;
    private PreparedStatement pst2 = null;
    private ObservableList<Item> dailyObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> starterObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> mainObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> sideObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> dessertObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> drinkObservableList = FXCollections.observableArrayList();

    /**
     * A method to show orders not completed by the chef. This is shown on start up.
     * @throws SQLException throws if SQLite query fails.
     */
    public void initialize() throws SQLException {
        String query = "SELECT * FROM menu";
        String query2 = "SELECT orderID, orderNo, itemName, orderType "
                + "FROM orders WHERE chefCompleted = 'False' ";
        connection = DBManager.DBConnection();
        connection2 = DBManager.DBConnection();
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
            pst2 = connection2.prepareStatement(query2);
            rsOrders = pst2.executeQuery();
            ObservableList<Order> itemList = getOrderList(rsOrders);
            orderIDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
            orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
            orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            chefOrderView.setItems(itemList);
        } catch (Exception e) {
            System.out.println("Reason of the problem is: " + e);
        } finally {
            rsOrders.close();
            rs.close();
            pst.close();
            pst2.close();
            connection.close();
            connection2.close();
        }
    }
    /**
     * A method to get the menu information from the database.
     * @param rs Result of the query.
     * @throws SQLException throws if SQLite query fails.
     */
    public void fillMenuLists(final ResultSet rs) throws SQLException {
        while (rs.next()) {
            Item temp = new Item();
            temp.setId(rs.getInt("id"));
            temp.setItemName(rs.getString("name"));
            temp.setPrice(rs.getDouble("price"));
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
     * A method to update a specific menu item.
     * @param event the button click.
     * @throws IOException throws if input fails.
     */
    public void changeItemButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("chefChangeItem.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * A button to log out of the system.
     * @param event "used to get information in current scene.
     * @throws IOException used to get information in current scene.
     */
    public void logoutButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * A method to get the orders from the database.
     * @param rsOrders The result from an SQL query.
     * @return The ordeer information retrieved from the database.
     * @throws SQLException
     */
    private ObservableList<Order> getOrderList(final ResultSet rsOrders) throws SQLException {
        ObservableList<Order> tempItemList = FXCollections.observableArrayList();
        while (rsOrders.next()) {
            Order temp = new Order();
            temp.setOrderID(rsOrders.getInt("orderID"));
            temp.setOrderNo(rsOrders.getInt("orderNo"));
            temp.setItemName(rsOrders.getString("itemName"));
            temp.setOrderType(rsOrders.getString("orderType"));
            tempItemList.add(temp);
        }
        return tempItemList;
    }

    /**
     * A method to allow the chef to set the order as complete
     * @param event "used to get information in current scene
     * @throws SQLException throws if SQLite query fails.
     * @throws IOException throws if input fails
     */
    public void completeOrder(final ActionEvent event) throws SQLException, IOException {
        connection2 = DBManager.DBConnection();
        int id = chefOrderView.getSelectionModel().getSelectedItem().getOrderID();
        String complete = "UPDATE Orders SET chefCompleted = 'True' WHERE orderID = ?;";
        PreparedStatement pr = null;
        try {
            pr = connection2.prepareStatement(complete);
            pr.setInt(1, id);
            pr.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            initialize();
            rsOrders.close();
            pr.close();
            connection2.close();
        }
    }
    /**
     * Refresh the order table.
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void refreshButtonAction(final ActionEvent event) throws SQLException {
        String query = "SELECT orderID, orderNo, itemName, orderType FROM orders WHERE chefCompleted = 'False' ";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            rsOrders = pst.executeQuery();
            ObservableList<Order> itemList = getOrderList(rsOrders);
            orderIDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
            orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
            orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            chefOrderView.setItems(itemList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rsOrders.close();
            pst.close();
            connection.close();
        }
    }
}
