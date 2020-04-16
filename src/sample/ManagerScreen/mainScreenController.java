package sample.ManagerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DBManager;
import sample.Report;
import sample.Staff;
import sample.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainScreenController {
    @FXML private TableView<Report> table;
    @FXML private TableColumn<Report,String> tableViewReport;
    @FXML private TableColumn<Report,String> tableViewName;
    @FXML private TableColumn<Report,Integer> tableViewCount;
    @FXML private TableView<Staff> staffTable;
    @FXML private TableColumn<Staff,Integer> staffId;
    @FXML private TableColumn<Staff,String> staffFirstName;
    @FXML private TableColumn<Staff,String> staffLastName;
    @FXML private TableColumn<Staff,Integer> staffHoursToWork;
    @FXML private TableColumn<Staff,Integer> staffTotalHoursWorked;
    @FXML private TableColumn<Staff,String> staffType;
    @FXML private TableView<user> customerTable;
    @FXML private TableColumn<user, Integer> customerId;
    @FXML private TableColumn<user, String> customerFirstName;
    @FXML private TableColumn<user, String> customerLastName;

    public static int employeeIdFromTable;


    Connection connection = null;
    Connection connection1 = null;
    Connection connection3 = null;
    ResultSet rsQueryItem = null;
    ResultSet rsQueryCustomer = null;
    ResultSet rsQueryBooking = null;
    ResultSet rsEmployee = null;
    ResultSet rsCustomer = null;
    PreparedStatement pst = null;
    PreparedStatement pstItem = null;
    PreparedStatement pstCustomer = null;
    PreparedStatement pstBooking = null;


    public ObservableList<Report> list = FXCollections.observableArrayList();



    public void onAddCustomerPressButton(ActionEvent Event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/signUpScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onShowCustomersPressButton(ActionEvent event) throws IOException, SQLException {
        String query = "SELECT * FROM users where type = 'Customer'";
        connection1 = DBManager.DBConnection();
        try{
            pst = connection1.prepareStatement(query);
            rsCustomer = pst.executeQuery();
            ObservableList<user> userList = getUserList(rsCustomer);
            customerId.setCellValueFactory(new PropertyValueFactory<user,Integer>("id"));
            customerFirstName.setCellValueFactory(new PropertyValueFactory<user,String>("firstName"));
            customerLastName.setCellValueFactory(new PropertyValueFactory<user,String>("lastName"));
            customerTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            rsCustomer.close();
            pst.close();
            connection1.close();
        }

    }

    private ObservableList<user> getUserList(ResultSet rsCustomer) throws SQLException {
        ObservableList<user> tempUserList = FXCollections.observableArrayList();
        while(rsCustomer.next()){
            user temp = new user();
            temp.setId(rsCustomer.getInt("id"));
            temp.setFirstName(rsCustomer.getString("firstName"));
            temp.setLastName(rsCustomer.getString("lastName"));
            tempUserList.add(temp);

        }
        return tempUserList;
    }

    public void onRemoveCustomerPressButton(ActionEvent event) throws SQLException, IOException {
        int id = customerTable.getSelectionModel().getSelectedItem().getId();
        String query = "DELETE from users where id = ?;";
        connection1 = DBManager.DBConnection();
        try {
            pst = connection1.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem is here: " + e);
        }
        finally {
            pst.close();
            connection1.close();
            onShowCustomersPressButton(event);
        }

    }


    public void onPressButton(ActionEvent Event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("addEmployee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void onShowEmployeePressButton(ActionEvent event) throws IOException, SQLException {
        String query = "SELECT * FROM staff ";
        connection = DBManager.DBConnection();
        try{
            pst = connection.prepareStatement(query);
            rsEmployee = pst.executeQuery();
            ObservableList<Staff> staffList = getStaffList(rsEmployee);
            staffId.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("Id"));
            staffFirstName.setCellValueFactory(new PropertyValueFactory<Staff,String>("firstName"));
            staffLastName.setCellValueFactory(new PropertyValueFactory<Staff,String>("lastName"));
            staffHoursToWork.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("hourToWork"));
            staffTotalHoursWorked.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("totalHoursWorked"));
            staffType.setCellValueFactory(new PropertyValueFactory<Staff,String>("type"));
            staffTable.setItems(staffList);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            rsEmployee.close();
            pst.close();
            connection.close();
        }

    }

    private ObservableList<Staff> getStaffList(ResultSet rsEmployee) throws SQLException {
        ObservableList<Staff> tempStaffList = FXCollections.observableArrayList();
        while(rsEmployee.next()){
            Staff temp = new Staff();
            temp.setId(rsEmployee.getInt("id"));
            temp.setFirstName(rsEmployee.getString("firstName"));
            temp.setLastName(rsEmployee.getString("lastName"));
            temp.setType(rsEmployee.getString("type"));
            temp.setHourToWork(rsEmployee.getInt("hoursToWork"));
            temp.setTotalHoursWorked(rsEmployee.getInt("totalHoursWorked"));
            tempStaffList.add(temp);

        }
        return tempStaffList;
    }

    public void onRemoveEmployeePressButton(ActionEvent event) throws SQLException, IOException {
        int id = staffTable.getSelectionModel().getSelectedItem().getId();
        String query = "DELETE from staff where id = ?;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem is here: " + e);
        }
        finally {
            pst.close();
            connection.close();
            onShowEmployeePressButton(event);
        }

    }

    public void onEditEmployeePressButton(ActionEvent event) throws SQLException, IOException {
        employeeIdFromTable = staffTable.getSelectionModel().getSelectedItem().getId();
        System.out.println(employeeIdFromTable);
        Parent root = FXMLLoader.load(getClass().getResource("editEmployee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Employee");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void onResfreshButtonPush() throws SQLException{
        list.clear();
        String itemQuery = "SELECT itemName, COUNT('itemName') AS 'value_occurence' FROM Orders GROUP BY itemName ORDER BY 'value_occurence' ASC LIMIT 1;";
        String customerQuery = "SELECT customerID, COUNT('customerID') AS 'value_occurence' FROM Orders GROUP BY customerID ORDER BY 'value_occurence' ASC LIMIT 1;";
        String bookingQuery = "SELECT timePeriod, COUNT('timePeriod') AS 'value_occurence' FROM booking GROUP BY timePeriod ORDER BY 'value_occurence' DESC LIMIT 1;";
        connection3 = DBManager.DBConnection();
        pstItem = connection3.prepareStatement(itemQuery);
        pstCustomer = connection3.prepareStatement(customerQuery);
        pstBooking = connection3.prepareStatement(bookingQuery);
        rsQueryItem = pstItem.executeQuery();
        rsQueryCustomer = pstCustomer.executeQuery();
        rsQueryBooking = pstBooking.executeQuery();
        String item = rsQueryItem.getString(1);
        int itemTimes = rsQueryItem.getInt(2);
        String customer = rsQueryCustomer.getString(1);
        int customerTimes = rsQueryCustomer.getInt(2);
        String time = rsQueryBooking.getString(1);
        int timeTimes = rsQueryBooking.getInt(2);

        list.addAll(new Report(1,"Most Popular Item",item,itemTimes));
        list.addAll(new Report(2,"Most Active Customer",customer,customerTimes));
        list.addAll(new Report(3, "Busiest Time Period",time,timeTimes));
        tableViewReport.setCellValueFactory(new PropertyValueFactory<Report,String>("Description"));
        tableViewName.setCellValueFactory(new PropertyValueFactory<Report,String>("Name"));
        tableViewCount.setCellValueFactory(new PropertyValueFactory<Report,Integer>("Amount"));
        table.setItems(list);

        connection3.close();
        pstItem.close();
        pstCustomer.close();
        pstBooking.close();
        rsQueryBooking.close();
        rsQueryCustomer.close();
        rsQueryItem.close();
    }

    public void logoutButtonPushed(ActionEvent event) throws IOException, SQLException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
