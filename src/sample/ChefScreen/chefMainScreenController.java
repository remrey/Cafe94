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
import sample.item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class chefMainScreenController {

    @FXML private ComboBox<item> dailyList;
    @FXML private ComboBox<item> starterList;
    @FXML private ComboBox<item> mainList;
    @FXML private ComboBox<item> sideList;
    @FXML private ComboBox<item> dessertList;
    @FXML private ComboBox<item> drinkList;
    @FXML Button refreshButton;
    @FXML Button completedButton;
    @FXML private TableView<Order> chefOrderView;
    @FXML private TableColumn<Order, Integer> orderIDColumn;
    @FXML private TableColumn<Order, Integer> orderNoColumn;
    @FXML private TableColumn<Order, String> orderTypeColumn;
    @FXML private TableColumn<Order, String> itemNameColumn;
    Connection connection = null;
    Connection connection2 = null;
    ResultSet rs = null;
    ResultSet rsOrders = null;
    PreparedStatement pst = null;
    PreparedStatement pst2 = null;

    public ObservableList<item> dailyObservableList = FXCollections.observableArrayList();
    public ObservableList<item> starterObservableList = FXCollections.observableArrayList();
    public ObservableList<item> mainObservableList = FXCollections.observableArrayList();
    public ObservableList<item> sideObservableList = FXCollections.observableArrayList();
    public ObservableList<item> dessertObservableList = FXCollections.observableArrayList();
    public ObservableList<item> drinkObservableList = FXCollections.observableArrayList();
    public ObservableList<item> resultList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {

        String query = "SELECT * FROM menu";
        String query2 = "SELECT orderID, orderNo, itemName, orderType FROM orders WHERE chefCompleted = 'False' ";
        connection = DBManager.DBConnection();
        connection2 = DBManager.DBConnection();
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

            pst2 = connection2.prepareStatement(query2);
            rsOrders = pst2.executeQuery();
            ObservableList<Order> itemList = getOrderList(rsOrders);
            orderIDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
            orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
            orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("itemName"));
            chefOrderView.setItems(itemList);
        }
        catch(Exception e ){
            System.out.println("Reason of the problem is: " + e);
        }
        finally {
            rsOrders.close();
            rs.close();
            pst.close();
            pst2.close();
            connection.close();
            connection2.close();
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
            else if (type.equals("starter")) starterObservableList.add(temp);
            else if(type.equals("main")) mainObservableList.add(temp);
            else if(type.equals("side")) sideObservableList.add(temp);
            else if(type.equals("dessert")) dessertObservableList.add(temp);
            else if(type.equals("drink")) drinkObservableList.add(temp);
        }
    }

    public void changeItemButtonPushed(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("chefChangeItem.fxml"));
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

    private ObservableList<Order> getOrderList(ResultSet rsOrders) throws SQLException {
        ObservableList<Order> tempItemList = FXCollections.observableArrayList();
        while(rsOrders.next()){
            Order temp = new Order();
            temp.setOrderID(rsOrders.getInt("orderID"));
            temp.setOrderNo(rsOrders.getInt("orderNo"));
            temp.setItemName(rsOrders.getString("itemName"));
            temp.setOrderType(rsOrders.getString("orderType"));
            tempItemList.add(temp);
        }
        return tempItemList;
    }

    public void completeOrder(ActionEvent event) throws SQLException, IOException {
        connection2 = DBManager.DBConnection();
        int id = chefOrderView.getSelectionModel().getSelectedItem().getOrderID();
        String complete = "UPDATE Orders SET chefCompleted = 'True' WHERE orderID = ?;";
        PreparedStatement pr = null;
        try {
            pr = connection2.prepareStatement(complete);
            pr.setInt(1,id);
            pr.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            initialize();
            rsOrders.close();
            pr.close();
            connection2.close();
        }
    }

    public void refreshButtonAction(ActionEvent event) throws SQLException {
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
