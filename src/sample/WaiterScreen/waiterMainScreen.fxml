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

public class takeawayOrderController {
    /**
     * FXML assignments
     */
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

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int userID;

    public ObservableList<item> dailyObservableList = FXCollections.observableArrayList();
    public ObservableList<item> starterObservableList = FXCollections.observableArrayList();
    public ObservableList<item> mainObservableList = FXCollections.observableArrayList();
    public ObservableList<item> sideObservableList = FXCollections.observableArrayList();
    public ObservableList<item> dessertObservableList = FXCollections.observableArrayList();
    public ObservableList<item> drinkObservableList = FXCollections.observableArrayList();
    public ObservableList<item> resultList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {

        String query = "SELECT * FROM menu;";
        connection = DBManager.DBConnection();
        try{

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
     * This will send you back to the menu screen.
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
     * This will finish the order.
     */

    public void finaliseOrderButtonPushed(ActionEvent event) throws IOException {
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

        /**
     * This will allow you to check what you have already ordered.
     */

    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void bookingButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerBooking.fxml"));
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

    public void onAddDailyButtonPressed(ActionEvent event){
        try{
            if(!dailyList.getSelectionModel().isEmpty()) {
                item temp = dailyList.getSelectionModel().getSelectedItem();
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


}
