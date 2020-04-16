
package sample.ManagerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.DBManager;
import sample.Report;
import sample.Staff;
import sample.user;


public class MainScreenController {
    @FXML private TableView<Report> table;
    @FXML private TableColumn<Report, String> tableViewReport;
    @FXML private TableColumn<Report, String> tableViewName;
    @FXML private TableColumn<Report, Integer> tableViewCount;
    @FXML private TableView<Staff> staffTable;
    @FXML private TableColumn<Staff, Integer> staffId;
    @FXML private TableColumn<Staff, String> staffFirstName;
    @FXML private TableColumn<Staff, String> staffLastName;
    @FXML private TableColumn<Staff, Integer> staffHoursToWork;
    @FXML private TableColumn<Staff, Integer> staffTotalHoursWorked;
    @FXML private TableColumn<Staff, String> staffType;
    @FXML private TableView<user> customerTable;
    @FXML private TableColumn<user, Integer> customerId;
    @FXML private TableColumn<user, String> customerFirstName;
    @FXML private TableColumn<user, String> customerLastName;

    private Connection connection = null;
    private Connection connection1 = null;
    private Connection connection3 = null;
    private ResultSet rsQueryItem = null;
    private ResultSet rsQueryCustomer = null;
    private ResultSet rsQueryBooking = null;
    private ResultSet rsEmployee = null;
    private ResultSet rsCustomer = null;
    private PreparedStatement pst = null;
    private PreparedStatement pstItem = null;
    private PreparedStatement pstCustomer = null;
    private PreparedStatement pstBooking = null;

    /**
     * @param ObservableList is used to store reports.
     */
    private ObservableList<Report> list = FXCollections.observableArrayList();
    /**
     * @param employeeIdFromTable is used to store the employeeId
     *                            which is selected in the table.
     */
    public static int employeeIdFromTable;


    /**
     * Loads up a screen to add customer.
     * @param event is used to get information in current scene.
     * @throws IOException IOException throws if input fails.
     */
    @FXML
    private void onAddCustomerPressButton(final ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/signUpScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Used to refresh the customer list and display on the tableview.
     * @param event is used to get information in current scene.
     * @throws SQLException if SQLite query fails.
     */
    @FXML
    private void onShowCustomersPressButton(final ActionEvent event) throws SQLException {
        String query = "SELECT * FROM users where type = 'Customer'";
        connection1 = DBManager.DBConnection();
        try {
            pst = connection1.prepareStatement(query);
            rsCustomer = pst.executeQuery();
            ObservableList<user> userList = getUserList(rsCustomer);
            customerId.setCellValueFactory(new PropertyValueFactory<user, Integer>("id"));
            customerFirstName.setCellValueFactory(new PropertyValueFactory<user, String>("firstName"));
            customerLastName.setCellValueFactory(new PropertyValueFactory<user, String>("lastName"));
            customerTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection1.close();
        }

    }

    /**
     * Populates userList using the a resultset.
     * @param rs Resulset which has the rows of the query.
     * @return the populated user list.
     * @throws SQLException if the resultset is empty.
     */
    private ObservableList<user> getUserList(final ResultSet rs) throws SQLException {
        ObservableList<user> tempUserList = FXCollections.observableArrayList();
        while (rs.next()) {
            user temp = new user();
            temp.setId(rs.getInt("id"));
            temp.setFirstName(rs.getString("firstName"));
            temp.setLastName(rs.getString("lastName"));
            tempUserList.add(temp);

        }
        return tempUserList;
    }

    /**
     * Used to remove the customer which is clicked on the table view.
     * @param event is used to get information in current scene
     * @throws SQLException if the query fails.
     * @throws IOException if the input fails.
     */
    public void onRemoveCustomerPressButton(final ActionEvent event) throws SQLException, IOException {
        try {
            int id = customerTable.getSelectionModel().getSelectedItem().getId();
            String query = "DELETE from users where id = ?;";
            connection1 = DBManager.DBConnection();
            pst = connection1.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Problem is here: " + e);
        } finally {
            pst.close();
            connection1.close();
            onShowCustomersPressButton(event);
        }


    }

    /**
     * Loads up the add employee screen when add employee button pressed.
     * @param event is used to get information in current scene.
     * @throws IOException if the input fails.
     */
    public void onPressButton(final ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Used to refresh and show the list of employees when show employee button clicked.
     * @param event
     * @throws SQLException
     */
    public void onShowEmployeePressButton(final ActionEvent event) throws SQLException {
        String query = "SELECT * FROM staff ";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            rsEmployee = pst.executeQuery();
            ObservableList<Staff> staffList = getStaffList(rsEmployee);
            staffId.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("Id"));
            staffFirstName.setCellValueFactory(new PropertyValueFactory<Staff, String>("firstName"));
            staffLastName.setCellValueFactory(new PropertyValueFactory<Staff, String>("lastName"));
            staffHoursToWork.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("hourToWork"));
            staffTotalHoursWorked.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("totalHoursWorked"));
            staffType.setCellValueFactory(new PropertyValueFactory<Staff, String>("type"));
            staffTable.setItems(staffList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            pst.close();
            connection.close();
        }

    }

    private ObservableList<Staff> getStaffList(final ResultSet rs) throws SQLException {
        ObservableList<Staff> tempStaffList = FXCollections.observableArrayList();
        while (rs.next()) {
            Staff temp = new Staff();
            temp.setId(rs.getInt("id"));
            temp.setFirstName(rs.getString("firstName"));
            temp.setLastName(rs.getString("lastName"));
            temp.setType(rs.getString("type"));
            temp.setHourToWork(rs.getInt("hoursToWork"));
            temp.setTotalHoursWorked(rs.getInt("totalHoursWorked"));
            tempStaffList.add(temp);

        }
        return tempStaffList;
    }

    /**
     * Used to remove employee that is clicked in the table.
     * @param event is used to get information in current scene.
     * @throws SQLException if SQLite query fails.
     * @throws IOException if the input fails.
     */
    public void onRemoveEmployeePressButton(final ActionEvent event) throws SQLException, IOException {
        int id = staffTable.getSelectionModel().getSelectedItem().getId();
        String query = "DELETE from staff where id = ?;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Problem is here: " + e);
        } finally {
            pst.close();
            connection.close();
            onShowEmployeePressButton(event);
        }

    }

    /**
     * Used to pop-up edit employee screen.
     * @param event is used to get information in current scene.
     * @throws SQLException if SQLite query fails.
     */
    public void onEditEmployeePressButton(final ActionEvent event) throws SQLException {
        try {
            employeeIdFromTable = staffTable.getSelectionModel().getSelectedItem().getId();
            Parent root = FXMLLoader.load(getClass().getResource("editEmployee.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Employee");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Problem is here " + e);
        }
    }


    /**
     * Refreshes the report screen.
     * @throws SQLException if SQLite query fails.
     */
    public void onResfreshButtonPush() throws SQLException {
        list.clear();
        String itemQuery = "SELECT itemName, COUNT('itemName') AS 'value_occurence' "
                + "FROM Orders GROUP BY itemName "
                + "ORDER BY 'value_occurence' ASC LIMIT 1;";
        String customerQuery = "SELECT customerID, COUNT('customerID') AS 'value_occurence' "
                + "FROM Orders WHERE customerID>0 GROUP BY customerID "
                + "ORDER BY 'value_occurence' ASC LIMIT 1;";
        String bookingQuery = "SELECT timePeriod, COUNT('timePeriod') AS 'value_occurence'"
                + " FROM booking GROUP BY timePeriod "
                + "ORDER BY 'value_occurence' DESC LIMIT 1;";
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

        list.addAll(new Report(1, "Most Popular Item", item, itemTimes));
        list.addAll(new Report(2, "Most Active Customer", customer, customerTimes));
        list.addAll(new Report(3, "Busiest Time Period", time, timeTimes));
        tableViewReport.setCellValueFactory(new PropertyValueFactory<Report, String>("Description"));
        tableViewName.setCellValueFactory(new PropertyValueFactory<Report, String>("Name"));
        tableViewCount.setCellValueFactory(new PropertyValueFactory<Report, Integer>("Amount"));
        table.setItems(list);

        connection3.close();
        pstItem.close();
        pstCustomer.close();
        pstBooking.close();
    }

    /**
     * Used pop-up adding employee hours screen.
     * @param event is used to get information in current scene.
     */
    public void onAddHoursButtonPushed(final ActionEvent event) {
        try {
            employeeIdFromTable = staffTable.getSelectionModel().getSelectedItem().getId();
            System.out.println(employeeIdFromTable);
            Parent root = FXMLLoader.load(getClass().getResource("addHours.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Hours");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Problem is in here: " + e);
        }
    }

    /**
     * Navigates to signup screen.
     * Logs-out from the current user.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     */
    public void logoutButtonPushed(final ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
