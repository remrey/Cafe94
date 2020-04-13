package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginManager {
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public loginManager(){
        connection = DBManager.DBConnection();
        if(connection == null) System.exit(1);
    }


    public boolean isDbConnected() throws SQLException {
        return !connection.isClosed();
    }

    public String userType(String usern, String passw){
        String query = "SELECT type FROM users WHERE (firstName = ? and password = ?) ";
        try{
            pst = connection.prepareStatement(query);
            pst.setString(1,usern);
            pst.setString(2,passw);
            rs = pst.executeQuery();
            String result = rs.getString(1);
            rs.close();
            pst.close();
            if(result != null) return result;
            else return " ";

        }
        catch(Exception e ){

        }
        return " ";
    }

    public boolean isUserLegit(String usern, String passw){
        String query = "SELECT * FROM users WHERE (firstName = ? and password = ?) ";
        String sql = "CREATE TABLE IF NOT EXISTS users(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName varchar(255) NOT NULL,\n"
                + "	lastName varchar(255) NOT NULL, \n"
                + " password varchar(255) NOT NULL, \n"
                + " type varchar(255) NOT NULL \n"
                + ");";
        String admin = "INSERT or IGNORE INTO users(id, firstName, lastName, password,type) VALUES(1, 'Admin', 'admin','admin','manager')";

        try{
            PreparedStatement tableCheck = connection.prepareStatement(sql);
            tableCheck.executeUpdate();
//            tableCheck.close();
            PreparedStatement addAdmin = connection.prepareStatement(admin);
            addAdmin.executeUpdate();
//            addAdmin.close();
            pst = connection.prepareStatement(query);
            pst.setString(1,usern);
            pst.setString(2,passw);
            rs = pst.executeQuery();
            if(rs.next()) return true;
            else return false;

        }
        catch(Exception e){
            System.out.println("Problem is in here: " + e);

        }
        return false;
    }

}
