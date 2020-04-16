package sample.WaiterScreen;

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
import sample.*;


import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class waiterMainScreenController {
    @FXML private ComboBox<String> tableList;
    @FXML private ComboBox<item> favoriteList;
    @FXML private ComboBox<item> starterList;
    @FXML private ComboBox<item> mainList;
    @FXML private ComboBox<item> sideList;
    @FXML private ComboBox<item> dessertList;
    @FXML private ComboBox<item> drinkList;
    @FXML private TableView<item> finalOrderView;
    @FXML private TableColumn<item, String> itemName;
    @FXML private TableColumn<item, Double> itemPrice;
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
    @FXML private TableColumn<Order,Integer> orderIdColumn;
    @FXML private TableColumn<Order,String> itemNameColumn;
    @FXML private TableColumn<Order,String> typeColumn;
    @FXML private TableColumn<Order, Integer> tableIdColumn;
    @FXML private TableColumn<Order,String> statusColumn;
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order,Integer> deliveryOrderIdColumn;
    @FXML private TableColumn<Order,Integer> deliveryCustomerIdColumn;
    @FXML private TableColumn<Order,Boolean> deliveryStatusColumn;
    @FXML private TableView<Order> orderItemTable;
    @FXML private TableColumn<Order,Integer> deliveryItemIdColumn;
    @FXML private TableColumn<Order,String> deliveryItemNameColumn;

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> iDColumn;
    @FXML private TableColumn<Booking, String> dateColumn;
    @FXML private TableColumn<Booking, String> timeColumn;
    @FXML private TableColumn<Booking, Integer> numberOfGuestsColumn;
    @FXML private TableColumn<Booking, Boolean> extendedColumn;
    @FXML private Label displayMessage;
    @FXML private Button loadButton;


    ToggleGroup toggleGroup = new ToggleGroup();
    private double totalCost;
    private String tableChoice = "";

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public ObservableList<item> favoriteObservableList = FXCollections.observableArrayList();
    public ObservableList<item> starterObservableList = FXCollections.observableArrayList();
    public ObservableList<item> mainObservableList = FXCollections.observableArrayList();
    public ObservableList<item> sideObservableList = FXCollections.observableArrayList();
    public ObservableList<item> dessertObservableList = FXCollections.observableArrayList();
    public ObservableList<item> drinkObservableList = FXCollections.observableArrayList();
    public ObservableList<item> resultList = FXCollections.observableArrayList();
    public ObservableList<Booking> data = FXCollections.observableArrayList();

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
        try{

            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            fillMenuLists(rs);
            favoriteList.setItems(favoriteObservableList);
            starterList.setItems(starterObservableList);
            mainList.setItems(mainObservableList);
            sideList.setItems(sideObservableList);
            dessertList.setItems(dessertObservableList);
            drinkList.setItems(drinkObservableList);
        }
        catch(Exception e ){
            System.out.println("Reason of the problem is: " + e);
        }

    }

    public void onLogoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

    public void setTableSelection(ActionEvent event){
        tableChoice = tableList.getSelectionModel().getSelectedItem();
    }

    public void onRefreshButtonClickedInDeliveryRequest(ActionEvent event) throws SQLException {
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
        }
        catch(Exception e){
            System.out.println("Problem is here: " + e);
        }
        finally {
            pst.close();
            connection.close();
        }
    }

    public void onDeclineButtonClicked(ActionEvent event) throws SQLException {
        String query = "DELETE from orders WHERE orderNo = ?";
        int orderNo =  orderTable.getSelectionModel().selectedItemProperty().get().getOrderNo();
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,orderNo);
            pst.executeUpdate();
            onRefreshButtonClickedInDeliveryRequest(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            pst.close();
            connection.close();
        }
    }

    public void onAcceptButtonClicked(ActionEvent event) throws SQLException {
        String query = "UPDATE orders SET waiterServed = True WHERE orderNo = ?;";
        int orderNo =  orderTable.getSelectionModel().selectedItemProperty().get().getOrderNo();
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,orderNo);
            pst.executeUpdate();
            onRefreshButtonClickedInDeliveryRequest(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            pst.close();
            connection.close();
        }

    }

    public void onMouseClickOrderTable() throws SQLException {
        Order order =  orderTable.getSelectionModel().selectedItemProperty().get();
        int orderNo = order.getOrderNo();
        String query = "SELECT * from orders where orderNo = ?;";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            pst.setInt(1,orderNo);
            ResultSet rs = pst.executeQuery();
            ObservableList<Order> orderList = fillDeliveryRequestTable(rs);
            deliveryItemIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("itemID"));
            deliveryItemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            orderItemTable.setItems(orderList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            pst.close();
            connection.close();
        }
    }

    public ObservableList<Order> fillDeliveryRequestTable(ResultSet rs) throws SQLException {
        ObservableList<Order> tempList = FXCollections.observableArrayList();
        while(rs.next()){
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

    public void fillMenuLists(ResultSet rs) throws SQLException {
        while(rs.next()){
            item temp = new item();
            temp.setId(rs.getInt("id"));
            temp.setItemName(rs.getString("name"));
            temp.setPrice(rs.getDouble("price") );
            String type = rs.getString("type");
            temp.setType(type);
            if(type.equals("favorite")) favoriteObservableList.add(temp);
            else if(type.equals("starter")) starterObservableList.add(temp);
            else if(type.equals("main")) mainObservableList.add(temp);
            else if(type.equals("side")) sideObservableList.add(temp);
            else if(type.equals("dessert")) dessertObservableList.add(temp);
            else if(type.equals("drink")) drinkObservableList.add(temp);
        }
    }

    public void onAddFavoriteButtonPressed(ActionEvent event){
        try{
            if(!favoriteList.getSelectionModel().isEmpty()) {
                item temp = favoriteList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();


                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddStarterButtonPressed(ActionEvent event){
        try{
            if(!starterList.getSelectionModel().isEmpty()) {
                item temp = starterList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddMainButtonPressed(ActionEvent event){
        try{
            if(!mainList.getSelectionModel().isEmpty()) {
                item temp = mainList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddSideButtonPressed(ActionEvent event){
        try{
            if(!sideList.getSelectionModel().isEmpty()) {
                item temp = sideList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddDessertButtonPressed(ActionEvent event){
        try{
            if(!dessertList.getSelectionModel().isEmpty()) {
                item temp = dessertList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onAddDrinkButtonPressed(ActionEvent event){
        try{
            if(!drinkList.getSelectionModel().isEmpty()){
                item temp = drinkList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();
                menuResultPrice.setStyle("-fx-text-fill: green");
                menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onToggleGroupSelected(ActionEvent event){
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        menuResultPrice.setStyle("-fx-text-fill: green");
        String tableId = selectedRadioButton.getText();
        tableChoice = "Total price for " + tableId  + " is:  ";
        menuResultPrice.setText(tableChoice + String.valueOf(totalCost));
    }

    public void onDoneButtonPressed(ActionEvent event) throws SQLException {
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
        }
        finally {
            pst.close();
            connection.close();
        }
    }

    public void onRefreshButtonPressed (ActionEvent event) throws SQLException {
        String query = "SELECT * FROM orders WHERE (waiterServed = 'False' and chefCompleted ='True'and (orderType = 'eatin' or orderType = 'takeaway'));";
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
        }
        catch (Exception e){
            System.out.println("Problem is here: " + e);
        }
    }

    private ObservableList<Order> getOutStandingOrder(ResultSet rs) throws SQLException {
        ObservableList<Order> tempList = FXCollections.observableArrayList();
        while(rs.next()){
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

    public void onFinalizeOrderButtonPressed(ActionEvent event){
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if(toggleGroup.getSelectedToggle() == null){
            menuResultPrice.setStyle("-fx-text-fill: red");
            menuResultPrice.setText("Please select a table! ");
        }
        else {
            try {
                String tableId = selectedRadioButton.getText();
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
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
                String insertOrder = "INSERT INTO Orders(orderNo,itemID,itemName,customerID,orderType,deliveryAddress" +
                        ",chefCompleted,delivered,waiterServed,orderDate,pickupTime,driverID,tableID) "
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

                String tempWord ="Table #";
                String tableIdString = tableId.replaceAll(tempWord, "");
                for(item i : resultList){
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

    private ObservableList<Booking> getBookingList(ResultSet rsBooking) throws SQLException {
        ObservableList<Booking> tempBookingList = FXCollections.observableArrayList();
        while(rsBooking.next()){
            this.data.add(new Booking(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getInt(6) ));
        }
        return this.data;
    }

    @FXML private void loadBookingData(ActionEvent event) throws SQLException {
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
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            rs.close();
            pst.close();
            connection.close();
        }
    }

    @FXML public void declineBooking(ActionEvent event) throws SQLException, IOException {
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
            }
            catch(NullPointerException ex) {
                System.err.println("Please select a row to update");
            }
            finally{
                bookingTable.getItems().clear();
                loadBookingData(event);
                rs.close();
                pr.close();
                connection.close();
            }
        }
    }

    @FXML public void approveBooking(ActionEvent event) throws SQLException, IOException {
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
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            finally{
                bookingTable.getItems().clear();
                loadBookingData(event);
                rs.close();
                pr.close();
                connection.close();
            }
        }

    }
}
