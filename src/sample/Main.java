package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane rootPane;
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        String sql = "CREATE TABLE IF NOT EXISTS users(\n"
                + "	id integer PRIMARY KEY,\n"
                + " userName varchar(255) unique, \n"
                + "	firstName varchar(255) NOT NULL, \n"
                + "	lastName varchar(255) NOT NULL, \n"
                + " password varchar(255) NOT NULL, \n"
                + " type varchar(255) NOT NULL \n"
                + ");";
        String admin = "INSERT or IGNORE INTO users(id, userName ,firstName, lastName, password,type) VALUES(1,'Admin' ,'Admin', 'admin','admin','Manager')";
        connection = DBManager.DBConnection();
        PreparedStatement tableCheck = connection.prepareStatement(sql);
        tableCheck.executeUpdate();
        PreparedStatement addAdmin = connection.prepareStatement(admin);
        addAdmin.executeUpdate();
        tableCheck.close();
        addAdmin.close();



        String menuQuery = "CREATE TABLE IF NOT EXISTS menu(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name varchar(255) NOT NULL,\n"
                + "	price real NOT NULL, \n"
                + " type varchar(255) NOT NULL \n"
                + ");";
        String addMenuItemsQuery = "INSERT or IGNORE INTO menu(id,name,price,type) "
                + "VALUES"
                + "(1,'Onion Rings','5.00','starter'), \n"
                + "(2,'Garlic Bread','3.50','starter'), \n"
                + "(3,'Calamari','5.80','starter'), \n"
                + "(4,'Soup of the Day','4.00','starter'), \n"
                + "(5,'Chicken Burger','7.25','main'), \n"
                + "(6,'New York Steak','13.50','main'), \n"
                + "(7,'Pizza Mozzarella','8.75','main'), \n"
                + "(8,'Lasagna','10.00','main'), \n"
                + "(9,'Caesar Salad','4.12','main'), \n"
                + "(10,'Vegetable Bowl','8.00','side'), \n"
                + "(11,'French Fries','3.00','side'), \n"
                + "(12, 'Spicy Fries', '3.60', 'side'), \n"
                + "(13, 'Mozzarella Dippers', '4.00', 'side'), \n"
                + "(14, 'Ice Cream', '4.80', 'side'), \n"
                + "(15, 'Vanilla Cheesecake', '5.50', 'dessert'), \n"
                + "(16, 'Sticky Toffee Pudding', '5.50', 'dessert'), \n"
                + "(17, 'Wine', '4.80', 'drink'), \n"
                + "(18, 'Cola', '3.00', 'drink'),\n"
                + "(19, 'Lemonade', '3.00', 'drink'),\n"
                + "(20, 'Beer', '4.50', 'drink');";

        PreparedStatement menuCheck = connection.prepareStatement(menuQuery);
        menuCheck.executeUpdate();
        PreparedStatement menuItemCheck = connection.prepareStatement(addMenuItemsQuery);
        menuItemCheck.executeUpdate();


        String orderQuery = "CREATE TABLE IF NOT EXISTS orders(\n"
                + "	orderID integer PRIMARY KEY ,\n"
                + " orderNo integer ,\n"
                + "	itemID integer ,\n"
                + " itemName varchar(255), \n"
                + " customerID integer ,\n"
                + "	orderType varchar(255) NOT NULL, \n"
                + " deliveryAddress varchar(255) NOT NULL, \n"
                + " chefCompleted boolean ,\n"
                + " delivered boolean ,\n"
                + " waiterServed boolean ,\n"
                + " orderDate datetime ,\n"
                + " pickupTime datetime ,\n"
                + " driverID integer ,\n"
                + " tableID integer"
                + ");";
        PreparedStatement orderCheck = connection.prepareStatement(orderQuery);
        orderCheck.executeUpdate();

        String orderTableQuery = "CREATE TABLE IF NOT EXISTS ordertable(\n"
                + "	id integer PRIMARY KEY,\n"
                + " table_id integer NOT NULL \n"
                + ");";
        PreparedStatement orderTableCheck = connection.prepareStatement(orderTableQuery);
        orderTableCheck.executeUpdate();

        String bookingQuery = "CREATE TABLE IF NOT EXISTS booking (\n"
                + "	bookingID integer PRIMARY KEY ,\n"
                + " date date NOT NULL,\n"
                + "	timePeriod integer NOT NULL,\n"
                + " numberOfGuests integer NOT NULL, \n"
                + " extended boolean NOT NULL,\n"
                + "	approved boolean NOT NULL, \n"
                + " checked boolean NOT NULL, \n"
                + " customerID boolean NOT NULL"
                + ");";

        PreparedStatement bookingCheck = connection.prepareStatement(bookingQuery);
        bookingCheck.executeUpdate();


        rootPane = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
