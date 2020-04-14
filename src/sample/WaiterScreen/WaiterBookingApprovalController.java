package sample.WaiterScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;


public class WaiterBookingApprovalController {
    /*
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> iDColumn;
    @FXML private TableColumn<Booking, String> dateColumn;
    @FXML private TableColumn<Booking, String> timeColumn;
    @FXML private TableColumn<Booking, Integer> numberOfGuestsColumn;
    @FXML private TableColumn<Booking, Boolean> extendedColumn;
    @FXML private Label displayMessage;
    @FXML private Button loadButton;
    Connection connection = null;
    public ObservableList<Booking> data = FXCollections.observableArrayList();
    PreparedStatement pst = null;
    ResultSet rs;


    @FXML private void loadBookingData(ActionEvent event) throws SQLException {
        String sql = "SELECT * FROM booking WHERE checked = 0;";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<Booking> bookingList = getBookingList(rs);
            this.iDColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingID"));
            this.dateColumn.setCellValueFactory(new PropertyValueFactory<Booking, String>("date"));
            this.timeColumn.setCellValueFactory(new PropertyValueFactory<Booking, String>("time"));
            this.numberOfGuestsColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("numberOfGuests"));
            this.extendedColumn.setCellValueFactory(new PropertyValueFactory<Booking, Boolean>("extended"));
            bookingTable.setItems(bookingList);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            rs.close();
            pst.close();
            connection.close();
        }

    }

    @FXML public void approveBooking(ActionEvent event) throws SQLException, IOException {
        connection = DBManager.DBConnection();
        int id = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
        String updateSql = "UPDATE booking SET approved = 1, checked = 1 WHERE bookingID = ?;";
        PreparedStatement pr = null;
        try{
            pr = connection.prepareStatement(updateSql);
            pr.setInt(1,id);
            pr.executeUpdate();
            displayMessage.setText("Booking successfully approved");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            bookingTable.getItems().clear();
            loadBookingData(event);
            rs.close();
            pr.close();
            connection.close();
        }
    }

    @FXML public void declineBooking(ActionEvent event) throws SQLException, IOException {
        connection = DBManager.DBConnection();
        int id = bookingTable.getSelectionModel().getSelectedItem().getBookingID();
        String updateSql = "UPDATE booking SET approved = 0, checked = 1 WHERE bookingID = ?;";
        PreparedStatement pr = null;
        try {
            pr = connection.prepareStatement(updateSql);
            pr.setInt(1,id);
            pr.executeUpdate();
            displayMessage.setText("Booking successfully declined");
        }
        catch(NullPointerException ex) {
            System.err.println("Please select a row to update");
        }
        finally{
            bookingTable.getItems().clear();
            loadBookingData(event);
            rs.close();
            pr.close();
            connection.close();
        }

    }

    private ObservableList<Booking> getBookingList(ResultSet rsBooking) throws SQLException {
        ObservableList<Booking> tempBookingList = FXCollections.observableArrayList();
        while(rsBooking.next()){
            this.data.add(new Booking(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getInt(6) ));
        }
        return this.data;
    }*/


    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("waiterHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void createOrderButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("waiterOrder.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
