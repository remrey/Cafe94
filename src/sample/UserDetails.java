package sample;

import sample.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class file to store details of current signed in user to be accessed throughout the system.
 * @author Lorcan, Luis, Emre
 * @version 1.0
 */

public final class UserDetails {
    private static String usern;
    private static String firstName;
    private static String lastName;
    private static UserDetails instance;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    private UserDetails() { } ;

    /**
     * Creates instance of UserDetails and ensures only one exists.
     * @return created instance
     */

    public static UserDetails getInstance() {
        if (instance == null) {
            instance = new UserDetails();
        }
        return instance;
    }

    /**
     * Sets username of UserDetails to current signed in User.
     * @param usern_ username of current user.
     */

    public void setUserID(String usern_) {
        usern = usern_;
    }

    /**
     * Finds the userID from users table in the database and returns.
     * @return users userID.
     * @throws SQLException throws if SQLite query fails.
     */

    public int getUserID() throws SQLException {
        String sql = "SELECT id from users WHERE userName = ?;";
        connection = DBManager.DBConnection();
        int id = -1;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, UserDetails.getInstance().getUsern());
            rs = pst.executeQuery();
            id = rs.getInt("id");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
            pst.close();
            return id;
        }

    }

    /**
     * Finds User First Name from database and returns.
     * @return users first name.
     * @throws SQLException throws if SQLite query fails.
     */

    public String getUserFirst() throws SQLException {
        Connection connection = DBManager.DBConnection();
        String sql = "SELECT firstName from users WHERE userName = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, UserDetails.getInstance().getUsern());
            rs = pst.executeQuery();
            firstName = rs.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
            pst.close();
            return firstName;
        }
    }

    /**
     * Gets users last name from database and returns.
     * @return users last name.
     * @throws SQLException throws if SQLite query fails.
     */

    public String getUserLast() throws SQLException {
        Connection connection = DBManager.DBConnection();
        String sql = "SELECT lastName from users WHERE userName = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, UserDetails.getInstance().getUsern());
            rs = pst.executeQuery();
            lastName = rs.getString(1);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
            pst.close();
            return lastName;
        }
    }

    /**
     * Gets users username.
     * @return users username.
     */

    public String getUsern() {
        return usern;
    }
}
