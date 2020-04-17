package sample.CustomerScreen;

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
import sample.Item;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A controller for the customer's takeaway order screen. This screen allows the customers to place
 * a takeaway order within the system.
 * @author George, Emre
 * @version 1.0
 */
public class TakeawayOrderController {

    @FXML private ComboBox<String> tableList;
    @FXML private ComboBox<Item> dailyList;
    @FXML private ComboBox<Item> starterList;
    @FXML private ComboBox<Item> mainList;
    @FXML private ComboBox<Item> sideList;
    @FXML private ComboBox<Item> dessertList;
    @FXML private ComboBox<Item> drinkList;
    @FXML private TableView<Item> finalOrderView;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item, Double> itemPrice;
    @FXML private Label menuResultPrice;

    private double totalCost;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int userID;

    public ObservableList<Item> dailyObservableList = FXCollections.observableArrayList();
    public ObservableList<Item>  starterObservableList = FXCollections.observableArrayList();
    public ObservableList<Item>  mainObservableList = FXCollections.observableArrayList();
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

        String query = "SELECT * FROM menu;";
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
        } catch(Exception e) {
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
     * The following function can be used to go to the menu screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void backToMenuButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * This function is used when the finalise order button is pushed.
     * This will add the order to the Orders database.
     * @param event
     * @throws IOException
     */
    public void finaliseOrderButtonPushed(final ActionEvent event) throws IOException {
        try {
            int curCustomer = sample.UserDetails.getInstance().getUserID();
            totalCost = 0.0;
            menuResultPrice.setStyle("-fx-text-fill: green");
            menuResultPrice.setText("0.00");
            //Updates the orders table with new Order
            // Complete status is waiting by default
            String insertIntoOrderTable = "INSERT INTO ordertable(table_id) VALUES(?);";
            connection = DBManager.DBConnection();
            pst = connection.prepareStatement(insertIntoOrderTable);
            pst.setString(1, String.valueOf(userID));
            pst.executeUpdate();
            String getLastInsertedId = "select last_insert_rowid();";

            //gets the id of the last inserted Order
            PreparedStatement getLastId = connection.prepareStatement(getLastInsertedId);
            ResultSet lastId = getLastId.executeQuery();
            int id = lastId.getInt(1);

            //put all items in the order to a new table
            // with the orders id
            String insertOrder = "INSERT INTO Orders(orderNo,itemID,itemName,customerID,orderType,deliveryAddress"
                    + ",chefCompleted,delivered,waiterServed,orderDate,pickupTime,driverID,tableID) "
                    + "VALUES("
                    + id + ", "
                    + "?, "
                    + "?, "
                    + "?,"
                    + "'takeaway',"
                    + "'None',"
                    + "'False',"
                    + "'False',"
                    + "'False',"
                    + "'now',"
                    + "'now',"
                    + "-1,"
                    + "-1);";
            PreparedStatement sendOrder = connection.prepareStatement(insertOrder);

            for (Item i : resultList) {
                String tempName = i.getItemName();
                int tempID = i.getId();
                sendOrder.setInt(1, tempID);
                sendOrder.setString(2, tempName);
                sendOrder.setInt(3, curCustomer);
                sendOrder.executeUpdate();
            }
            resultList.clear();
            connection.close();
            menuResultPrice.setText("You have successfully finished your order.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    /**
     * This allows the Daily Specials to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddDailyButtonPressed(final ActionEvent event) {
        try {
            if (!dailyList.getSelectionModel().isEmpty()) {
                Item temp = dailyList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();


                menuResultPrice.setText(String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * This allows the Starters to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddStarterButtonPressed(final ActionEvent event) {
        try {
            if (!starterList.getSelectionModel().isEmpty()) {
                Item temp = starterList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This allows the Main to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddMainButtonPressed(final ActionEvent event) {
        try{
            if(!mainList.getSelectionModel().isEmpty()) {
                Item temp = mainList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This allows the Sides to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddSideButtonPressed(final ActionEvent event) {
        try {
            if (!sideList.getSelectionModel().isEmpty()) {
                Item temp = sideList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This allows the Dessert to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddDessertButtonPressed(final ActionEvent event) {
        try {
            if (!dessertList.getSelectionModel().isEmpty()) {
                Item temp = dessertList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This allows the Drinks to be added to the order
     * @param event Used to get information in current scene
     */
    public void onAddDrinkButtonPressed(final ActionEvent event) {
        try {
            if (!drinkList.getSelectionModel().isEmpty()) {
                Item temp = drinkList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();
                menuResultPrice.setStyle("-fx-text-fill: green");
                menuResultPrice.setText(String.valueOf(totalCost));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
