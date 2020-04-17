package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A class that is used to check the login information.
 * @author F. Emre YILMAZ
 * @version 1.0
 */

public class LoginManager {
    public Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    /**
     * creates a default connector.
     */
    public LoginManager() {
        connection = DBManager.DBConnection();
        if (connection == null) {
            System.exit(1);
        }
    }


    /**
     * Checks if the db is connected.
     * @return false is there is no connection.
     * @throws SQLException is the connection fails.
     */
    public boolean isDbConnected() throws SQLException {
        return !connection.isClosed();
    }

    /**
     * returns the usertype.
     * e.g. chef,customer etc..
     * @param usern Username input on the login screen.
     * @param passw Password input on the login screen.
     * @return returns the user type.
     */
    public String userType(final String usern, final String passw) {
        UserDetails.getInstance().setUserID(usern);
        String query = "SELECT type FROM users WHERE (userName = ? and password = ?) ";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, usern);
            pst.setString(2, passw);
            rs = pst.executeQuery();
            String result = rs.getString(1);
            rs.close();
            pst.close();
            if (result != null) {
                return result;
            } else {
                return " ";
            }
        } catch (Exception e) {
        }
        return " ";
    }


    /**
     * Checks if the user exist in the database.
     * @param usern Username input on the login screen.
     * @param passw Password input on the login screen
     * @return true if the user exists in the database.
     */
    public boolean isUserLegit(final String usern, final String passw) {
        String query = "SELECT * FROM users WHERE (userName = ? and password = ?) ";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, usern);
            pst.setString(2, passw);
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
                }
        } catch (Exception e) {
            System.out.println("Problem is in here: " + e);

        }
        return false;
    }


}
