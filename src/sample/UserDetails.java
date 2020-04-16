package sample;

import sample.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserDetails {
    private static String usern;
    private static int userID;
    private static UserDetails instance;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    private UserDetails(){};

    public static UserDetails getInstance() {
        if (instance == null) {
            instance = new UserDetails();
        }
        return instance;
    }

    public void setUserID(String usern_) {
        usern = usern_;
    }

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
        }
        finally {
            connection.close();
            pst.close();
            return id;
        }

    }

    public String getUsern(){
        return usern;
    }
}
