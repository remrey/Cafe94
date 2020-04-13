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
        String query = "SELECT type FROM users WHERE (userName = ? and password = ?) ";
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
        String query = "SELECT * FROM users WHERE (userName = ? and password = ?) ";
        try{
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
