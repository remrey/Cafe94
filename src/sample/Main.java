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


//
//        String menuQuery = "CREATE TABLE IF NOT EXISTS menu(\n"
//                + "	id integer PRIMARY KEY,\n"
//                + "	name varchar(255) NOT NULL,\n"
//                + "	price real NOT NULL, \n"
//                + " type varchar(255) NOT NULL \n"
//                + ");";
//        String addMenuItemsQuery = "INSERT INTO menu(name,price,type) "
//                + "VALUES"
//                + "('Favorite1','5.21','favorite'), \n"
//                + "('Favorite2','3.21','favorite'), \n"
//                + "('Onion Rings','5.21','starter'), \n"
//                + "('Garlic Bread','4.23','starter'), \n"
//                + "('Chicken Burger','7.21','main'), \n"
//                + "('Newyork Steak','9.00','main'), \n"
//                + "('French Fries','2.21','side'), \n"
//                + "('Spicy Fried','3.00','side'), \n"
//                + "('Vanilla Cheesecake','4.12','dessert'), \n"
//                + "('Fosters','6.00','drink'), \n"
//                + "('Sprite','2.20','drink');";
//        PreparedStatement menuCheck = connection.prepareStatement(menuQuery);
//        menuCheck.executeUpdate();
//        PreparedStatement menuItemCheck = connection.prepareStatement(addMenuItemsQuery);
//        menuItemCheck.executeUpdate();


        rootPane = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
