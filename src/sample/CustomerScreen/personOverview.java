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

public class personOverview {

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

    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void bookingButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/customerCreateBooking.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void menuButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/CustomerScreen/menu.fxml"));
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
