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
        Connection connection = DBManager.DBConnection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            String sql = "CREATE TABLE IF NOT EXISTS users(\n"
                    + "	id integer PRIMARY KEY,\n"
                    + " userName varchar(255) unique, \n"
                    + "	firstName varchar(255) NOT NULL, \n"
                    + "	lastName varchar(255) NOT NULL, \n"
                    + " password varchar(255) NOT NULL, \n"
                    + " type varchar(255) NOT NULL \n"
                    + ");";
            PreparedStatement tableCheck = connection.prepareStatement((sql));
            tableCheck.executeUpdate();

            sql = "SELECT id from users WHERE userName = ?;";
            pst = connection.prepareStatement(sql);
            pst.setString(1, UserDetails.getInstance().getUsern());
            rs = pst.executeQuery();
            int id = rs.getInt(1);
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public String getUsern(){
        return usern;
    }
}
