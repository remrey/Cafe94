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


        rootPane = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
