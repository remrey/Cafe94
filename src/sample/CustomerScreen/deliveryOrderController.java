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
import sample.item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A controller for the customer's delivery order screen. This screen allows the customers to place
 * a delivery order within the system.
 * @author George, Emre
 * @version 1.0
 */

public class DeliveryOrderController {

    @FXML private ComboBox<String> tableList;
    @FXML private ComboBox<item> dailyList;
    @FXML private ComboBox<item> starterList;
    @FXML private ComboBox<item> mainList;
    @FXML private ComboBox<item> sideList;
    @FXML private ComboBox<item> dessertList;
    @FXML private ComboBox<item> drinkList;
    @FXML private TableView<item> finalOrderView;
    @FXML private TableColumn<item, String> itemName;
    @FXML private TableColumn<item, Double> itemPrice;
    @FXML private Label menuResultPrice;

    private double totalCost;
    private int userID;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public ObservableList<item> dailyObservableList = FXCollections.observableArrayList();
    public ObservableList<item> starterObservableList = FXCollections.observableArrayList();
    public ObservableList<item> mainObservableList = FXCollections.observableArrayList();
    public ObservableList<item> sideObservableList = FXCollections.observableArrayList();
    public ObservableList<item> dessertObservableList = FXCollections.observableArrayList();
    public ObservableList<item> drinkObservableList = FXCollections.observableArrayList();
    public ObservableList<item> resultList = FXCollections.observableArrayList();

    /**
     * Initialize function connects to the database and sets the list of
     * viewable items from the menu.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void initialize() throws SQLException {
        String query = "SELECT * FROM menu";
        connection = DBManager.DBConnection();
        try{
//            PreparedStatement menuCheck= connection.prepareStatement(sql);
//            menuCheck.executeUpdate();
//
//            PreparedStatement setMenu = connection.prepareStatement(addMenuQuery);
//            setMenu.executeUpdate();

            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            fillMenuLists(rs);
            dailyList.setItems(dailyObservableList);
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
    /**
     * Fills the menu lists based in the menu database.
     * @param rs Result of the query.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void fillMenuLists(ResultSet rs) throws SQLException {
        while(rs.next()){
            item temp = new item();
            temp.setId(rs.getInt("id"));
            temp.setItemName(rs.getString("name"));
            temp.setPrice(rs.getDouble("price") );
            String type = rs.getString("type");
            temp.setType(type);
            if (type.equals("dailySpecial")) dailyObservableList.add(temp);
            else if(type.equals("starter")) starterObservableList.add(temp);
            else if(type.equals("main")) mainObservableList.add(temp);
            else if(type.equals("side")) sideObservableList.add(temp);
            else if(type.equals("dessert")) dessertObservableList.add(temp);
            else if(type.equals("drink")) drinkObservableList.add(temp);
        }
    }
    /**
     * The following function can be used to go to the menu screen.
     * @param event Used to get information in current scene.
     * @throws IOException Throws if input fails.
     */
    public void backToMenuButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/menu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
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
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerBooking.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * The following function can be used to go to the profile screen.
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

    /**
     * This function allows the Daily Specials to be added to the order.
     * @param event Used to get information in current scene.
     */
    public void onAddDailyButtonPressed(ActionEvent event){
        try{
            if(!starterList.getSelectionModel().isEmpty()) {
                item temp = starterList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * This function allows the Starters to be added to the order.
     * @param event Used to get information in current scene.
     */
    public void onAddStarterButtonPressed(ActionEvent event){
        try{
            if(!starterList.getSelectionModel().isEmpty()) {
                item temp = starterList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * This function allows the Mains to be added to the order.
     * @param event Used to get information in current scene.
     */
    public void onAddMainButtonPressed(ActionEvent event){
        try{
            if(!mainList.getSelectionModel().isEmpty()) {
                item temp = mainList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * This function allows the Sides to be added to the order.
     * @param event Used to get information in current scene.
     */
    public void onAddSideButtonPressed(ActionEvent event){
        try{
            if(!sideList.getSelectionModel().isEmpty()) {
                item temp = sideList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * This function allows the Desserts to be added to the order.
     * @param event Used to get information in current scene.
     */
    public void onAddDessertButtonPressed(ActionEvent event){
        try{
            if(!dessertList.getSelectionModel().isEmpty()) {
                item temp = dessertList.getSelectionModel().getSelectedItem();
                resultList.add(temp);
                itemName.setCellValueFactory(new PropertyValueFactory<item, String>("itemName"));
                itemPrice.setCellValueFactory(new PropertyValueFactory<item, Double>("price"));
                finalOrderView.setItems(resultList);
                totalCost += temp.getPrice();

                menuResultPrice.setText(String.valueOf(totalCost));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * This function allows the Drinks to be added to the order.
     * @param event Used to get information in current scene.
     */
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
                menuResultPrice.setText(String.valueOf(totalCost));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This function is used when the finalise order button is pushed.
     * This will add the order to the Orders database.
     * @param event Used to get information in current scene.
     */
    public void onFinaliseOrderButtonPressed(ActionEvent event){
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
            String insertOrder = "INSERT INTO Orders(orderNo,itemID,itemName,customerID,orderType,deliveryAddress" +
                    ",chefCompleted,delivered,waiterServed,orderDate,pickupTime,driverID,tableID) "
                    + "VALUES("
                    + id + ", "
                    + "?, "
                    + "?, "
                    + "?,"
                    + "'delivery',"
                    + "'None',"
                    + "'False',"
                    + "'False',"
                    + "'False',"
                    + "'now',"
                    + "'now',"
                    + "-1,"
                    + "-1);";
            PreparedStatement sendOrder = connection.prepareStatement(insertOrder);

            for (item i : resultList) {
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


}
