package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    public static Connection DBConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = null;
            String url = "jdbc:sqlite:cafe94.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to the database server has been successful");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occured here: " + e.getMessage());
            return null;
        }

    }
}
