
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that is used to connect to database.
 * @author F. Emre YILMAZ
 * @version 1.0
 */
public class DBManager {
    public static Connection DBConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = null;
            String url = "jdbc:sqlite:cafe94.db";
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occured here: " + e.getMessage());
            return null;
        }

    }
}
