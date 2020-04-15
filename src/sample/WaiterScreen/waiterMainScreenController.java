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
    @FXML private TableView<order> standingOrderTableView;
    @FXML private TableColumn<order,Integer> orderIdColumn;
    @FXML private TableColumn<order,String> itemNameColumn;
    @FXML private TableColumn<order,String> typeColumn;
    @FXML private TableColumn<order, Integer> tableIdColumn;
    @FXML private TableColumn<order,String> statusColumn;


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

    public void onRefreshButtonPressed(ActionEvent event) throws SQLException {
        String query = "SELECT * FROM orders WHERE status = 'waiting';";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            ObservableList<order> outStandingOrderList = getOutStandingOrder(rs);
            orderIdColumn.setCellValueFactory(new PropertyValueFactory<order, Integer>("orderId"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<order, String>("itemName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<order, String>("orderType"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<order, String>("orderStatus"));
            tableIdColumn.setCellValueFactory(new PropertyValueFactory<order, Integer>("tableId"));
            standingOrderTableView.setItems(outStandingOrderList);
        }
        catch (Exception e){
            System.out.println("Problem is here: " + e);
        }
    }

    private ObservableList<order> getOutStandingOrder(ResultSet rs) throws SQLException {
        ObservableList<order> tempList = FXCollections.observableArrayList();
        while(rs.next()){
            order temp = new order();
            temp.setOrderId(rs.getInt("order_id"));
            temp.setItemName(rs.getString("item_name"));
            temp.setOrderType(rs.getString("type"));
            temp.setOrderStatus(rs.getString("status"));
            temp.setTableId(rs.getInt("table_id"));
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
                String insertOrder = "INSERT INTO orders(order_id,item_name,type,status,table_id) VALUES("
                        + id + ", "
                        + "?, "
                        + "'eatin',"
                        + "'waiting',"
                        + "?);";
                PreparedStatement sendOrder = connection.prepareStatement(insertOrder);
                for(item i : resultList){
                    String tempName = i.getItemName();
                    sendOrder.setString(1,tempName);
                    sendOrder.setString(2,tableId);
                    sendOrder.executeUpdate();
                }
                resultList.clear();
                connection.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }






}
