package sample.WaiterScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Booking;
import sample.DBManager;
import sample.Item;
import sample.Order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A home screen for a waiter to navigate to different sections.
 * @author Luis, Emre, George, Lorcan.
 * @version 1.0
 */
public class WaiterMainScreenController {
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
    @FXML private RadioButton table1;
    @FXML private RadioButton table2;
    @FXML private RadioButton table3;
    @FXML private RadioButton table4;
    @FXML private RadioButton table5;
    @FXML private RadioButton table6;
    @FXML private RadioButton table7;
    @FXML private RadioButton table8;
    @FXML private RadioButton table9;
    @FXML private RadioButton table10;
    @FXML private RadioButton table11;
    @FXML private TableView<Order> standingOrderTableView;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> itemNameColumn;
    @FXML private TableColumn<Order, String> typeColumn;
    @FXML private TableColumn<Order, Integer> tableIdColumn;
    @FXML private TableColumn<Order, String> statusColumn;
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> deliveryOrderIdColumn;
    @FXML private TableColumn<Order, Integer> deliveryCustomerIdColumn;
    @FXML private TableColumn<Order, Boolean> deliveryStatusColumn;
    @FXML private TableView<Order> orderItemTable;
    @FXML private TableColumn<Order, Integer> deliveryItemIdColumn;
    @FXML private TableColumn<Order, String> deliveryItemNameColumn;
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> iDColumn;
    @FXML private TableColumn<Booking, String> dateColumn;
    @FXML private TableColumn<Booking, String> timeColumn;
    @FXML private TableColumn<Booking, Integer> numberOfGuestsColumn;
    @FXML private TableColumn<Booking, Boolean> extendedColumn;
    @FXML private Label displayMessage;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private double totalCost;
    private String tableChoice = "";
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private ObservableList<Item> dailyObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> starterObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> mainObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> sideObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> dessertObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> drinkObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> resultList = FXCollections.observableArrayList();
    private ObservableList<Booking> data = FXCollections.observableArrayList();

    /**
     * Sets up the view on start of the screen.
     * @throws SQLException Used to get information in current scene.
     */
    public void initialize() throws SQLException {
        table1.setToggleGroup(toggleGroup);
        table2.setToggleGroup(toggleGroup);
        table3.setToggleGroup(toggleGroup);
        table4.setToggleGroup(toggleGroup);
        table5.setToggleGroup(toggleGroup);
        table6.setToggleGroup(toggleGroup);
        table7.setToggleGroup(toggleGroup);
        table8.setToggleGroup(toggleGroup);
        table9.setToggleGroup(toggleGroup);
        table10.setToggleGroup(toggleGroup);
        table11.setToggleGroup(toggleGroup);
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
        } catch (Exception e) {
            System.out.println("Reason of the problem is: " + e);
        }
    }

    /**
     * To log out of the system.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void onLogoutButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Used to select an item in the table.
     * @param event Used to get information in current scene.
     */
    public void setTableSelection(final ActionEvent event) {
        tableChoice = tableList.getSelectionModel().getSelectedItem();
    }

    /**
     * Used to show the orders for delivery
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onRefreshButtonClickedInDeliveryRequest(final ActionEvent event) throws SQLException {
        String query = "SELECT * FROM orders where (orderType = 'delivery' and waiterServed = 'False') group by orderNo;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            ObservableList<Order> orderList = fillDeliveryRequestTable(rs);
            deliveryOrderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
            deliveryCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customerID"));
            deliveryStatusColumn.setCellValueFactory(new PropertyValueFactory<Order, Boolean>("delivered"));
            orderTable.setItems(orderList);
        } catch (Exception e){
            System.out.println("Problem is here: " + e);
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Used to delete an order from table
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onDeclineButtonClicked(final ActionEvent event) throws SQLException {
        String query = "DELETE from orders WHERE orderNo = ?";
        int orderNo =  orderTable.getSelectionModel().selectedItemProperty().get().getOrderNo();
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, orderNo);
            pst.executeUpdate();
            onRefreshButtonClickedInDeliveryRequest(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Used to confirm an order has been served
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onAcceptButtonClicked(final ActionEvent event) throws SQLException {
        String query = "UPDATE orders SET waiterServed = True WHERE orderNo = ?;";
        int orderNo =  orderTable.getSelectionModel().selectedItemProperty().get().getOrderNo();
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1,orderNo);
            pst.executeUpdate();
            onRefreshButtonClickedInDeliveryRequest(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Used to show all orders with specific order number.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onMouseClickOrderTable() throws SQLException {
        Order order =  orderTable.getSelectionModel().selectedItemProperty().get();
        int orderNo = order.getOrderNo();
        String query = "SELECT * from orders where orderNo = ?;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, orderNo);
            ResultSet rs = pst.executeQuery();
            ObservableList<Order> orderList = fillDeliveryRequestTable(rs);
            deliveryItemIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("itemID"));
            deliveryItemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            orderItemTable.setItems(orderList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Fill temporary order table with information from the database.
     * @param rs Result of the query.
     * @return Returns the information from the database.
     * @throws SQLException Throws if SQLite query fails.
     */
    public ObservableList<Order> fillDeliveryRequestTable(final ResultSet rs) throws SQLException {
        ObservableList<Order> tempList = FXCollections.observableArrayList();
        while (rs.next()) {
            Order temp = new Order();
            temp.setOrderNo(rs.getInt("orderNo"));
            temp.setItemID((rs.getInt("itemID")));
            temp.setItemName(rs.getString("itemName"));
            temp.setCustomerID(rs.getInt("customerID"));
            temp.setDelivered(rs.getBoolean("delivered"));
            tempList.add(temp);
        }
        return tempList;
    }

    /**
     * Fill temporary menu table with information from the database.
     * @param rs Result of the query.
     * @throws SQLException Throws if SQLite query fails.
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
     * Add a daily special to the database.
     * @param event Used to get information in current scene.
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
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add starter to the menu
     * @param event Used to get information in current scene.
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
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a main to the menu.
     * @param event Used to get information in current scene.
     */
    public void onAddMainButtonPressed(final ActionEvent event) {
        try {
            if (!mainList.getSelectionModel().isEmpty()) {
                Item temp = mainList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a side to the menu.
     * @param event Used to get information in current scene.
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
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a dessert to the menu.
     * @param event Used to get information in current scene.
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
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a drink to the menu.
     * @param event Used to get information in current scene.
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
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Selects the table when box is selected.
     * @param event Used to get information in current scene.
     */
    public void onToggleGroupSelected(final ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        menuResultPrice.setStyle("-fx-text-fill: green");
        String tableId = selectedRadioButton.getText();
        tableChoice = "Total price for " + tableId  + " is:  ";
        menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
    }

    /**
     * Update the orders and set that waiter has served order.
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onDoneButtonPressed(final ActionEvent event) throws SQLException {
        String query = "UPDATE orders SET waiterServed = True WHERE orderID = ?;";
        int orderID =  standingOrderTableView.getSelectionModel().selectedItemProperty().get().getOrderID();
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1,orderID);
            pst.executeUpdate();
            onRefreshButtonPressed(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Refresh the orders table.
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void onRefreshButtonPressed(final ActionEvent event) throws SQLException {
        String query = "SELECT * FROM orders WHERE "
                + "(waiterServed = 'False' and chefCompleted ='True'and"
                + " (orderType = 'eatin' or orderType = 'takeaway'));";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            ObservableList<Order> outStandingOrderList = getOutStandingOrder(rs);
            orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("waiterServed"));
            tableIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("tableID"));
            standingOrderTableView.setItems(outStandingOrderList);
        } catch (Exception e) {
            System.out.println("Problem is here: " + e);
        }
    }

    /**
     * Add the outstanding orders to a temporary table.
     * @param rs Result of the query.
     * @return Return the information from the temporary list.
     * @throws SQLException Throws if SQLite query fails.
     */
    private ObservableList<Order> getOutStandingOrder(final ResultSet rs) throws SQLException {
        ObservableList<Order> tempList = FXCollections.observableArrayList();
        while (rs.next()) {
            Order temp = new Order();
            temp.setOrderID(rs.getInt("orderID"));
            temp.setItemName(rs.getString("itemName"));
            temp.setOrderType(rs.getString("orderType"));
            temp.setWaiterServed(rs.getBoolean("waiterServed"));
            temp.setTableID(rs.getInt("tableID"));
            tempList.add(temp);
        }
        return tempList;
    }

    /**
     * This function is used when the button is pushed. This will then add it to the order database.
     * @param event Used to get information in current scene.
     */
    public void onFinalizeOrderButtonPressed(final ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (toggleGroup.getSelectedToggle() == null) {
            menuResultPrice.setStyle("-fx-text-fill: red");
            menuResultPrice.setText("Please select a table! ");
        } else {
            try {
                String tableId = selectedRadioButton.getText();
                itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost = 0.0;
                menuResultPrice.setStyle("-fx-text-fill: green");
                menuResultPrice.setText("0.00");
                //Updates the orders table with new Order
                // Complete status is waiting by default
                String insertIntoOrderTable = "INSERT INTO ordertable(table_id) VALUES(?);";
                connection = DBManager.DBConnection();
                pst = connection.prepareStatement(insertIntoOrderTable);
                pst.setString(1,tableId);
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
                        + "-1,"
                        + "'eatin',"
                        + "'None',"
                        + "'False',"
                        + "'False',"
                        + "'False',"
                        + "'now',"
                        + "'now',"
                        + "-1,"
                        + "?);";
                PreparedStatement sendOrder = connection.prepareStatement(insertOrder);
                String tempWord = "Table #";
                String tableIdString = tableId.replaceAll(tempWord, "");
                for(Item i : resultList) {
                    String tempName = i.getItemName();
                    int tempID = i.getId();
                    sendOrder.setInt(1,tempID);
                    sendOrder.setString(2,tempName);
                    sendOrder.setInt(3,Integer.parseInt(tableIdString));
                    sendOrder.executeUpdate();
                }
                resultList.clear();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Get booking information from the database for bookings.
     * @param rsBooking Result of the query.
     * @return Returns the list.
     * @throws SQLException "Throws if SQLite query fails.
     */
    private ObservableList<Booking> getBookingList(final ResultSet rsBooking) throws SQLException {
        ObservableList<Booking> tempBookingList = FXCollections.observableArrayList();
        while (rsBooking.next()) {
            this.data.add(new Booking(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getInt(6) ));
        }
        return this.data;
    }

    /**
     * Loads the bookings which haven't been checked.
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     */
    @FXML private void loadBookingData(final ActionEvent event) throws SQLException {
        data.clear();
        String sql = "SELECT * FROM booking WHERE checked = 0;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<Booking> bookingList = getBookingList(rs);
            this.iDColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingID"));
            this.dateColumn.setCellValueFactory(new PropertyValueFactory<Booking, String>("date"));
            this.timeColumn.setCellValueFactory(new PropertyValueFactory<Booking, String>("timePeriod"));
            this.numberOfGuestsColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("numberOfGuests"));
            this.extendedColumn.setCellValueFactory(new PropertyValueFactory<Booking, Boolean>("extended"));
            bookingTable.setItems(bookingList);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            rs.close();
            pst.close();
            connection.close();
        }
    }

    /**
     * Declined the selected booking
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     * @throws IOException Throws if input fails.
     */
    @FXML public void declineBooking(final ActionEvent event) throws SQLException, IOException {
        connection = DBManager.DBConnection();
        if (bookingTable.getSelectionModel().getSelectedCells().isEmpty()) {
            displayMessage.setText("Please select a booking");
            displayMessage.setVisible(true);
        } else {
            int id = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
            String updateSql = "UPDATE booking SET approved = 0, checked = 1 WHERE bookingID = ?;";
            PreparedStatement pr = null;
            try {
                pr = connection.prepareStatement(updateSql);
                pr.setInt(1,id);
                pr.executeUpdate();
                displayMessage.setText("Booking successfully declined");
            } catch (NullPointerException ex) {
                System.err.println("Please select a row to update");
            } finally {
                bookingTable.getItems().clear();
                loadBookingData(event);
                rs.close();
                pr.close();
                connection.close();
            }
        }
    }

    /**
     * Approves the selected booking.
     * @param event Used to get information in current scene.
     * @throws SQLException Throws if SQLite query fails.
     * @throws IOException Throws if input fails.
     */
    @FXML public void approveBooking(final ActionEvent event) throws SQLException, IOException {
        connection = DBManager.DBConnection();
        if (bookingTable.getSelectionModel().getSelectedCells().isEmpty()) {
            displayMessage.setText("Please select a booking");
            displayMessage.setVisible(false);
            displayMessage.setVisible(true);
        } else {
            int id = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
            String updateSql = "UPDATE booking SET approved = 1, checked = 1 WHERE bookingID = ?;";
            PreparedStatement pr = null;
            try {
                pr = connection.prepareStatement(updateSql);
                pr.setInt(1,id);
                pr.executeUpdate();
                displayMessage.setText("Booking successfully approved");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                bookingTable.getItems().clear();
                loadBookingData(event);
                rs.close();
                pr.close();
                connection.close();
            }
        }
    }
}
