
package sample.ManagerScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A window that pops-up when Add hour button clicked.
 * Used to create change hour variables of employees.
 * @author F. Emre YILMAZ
 * @version 1.0
 */
public class AddHourController {

    @FXML private ComboBox<String> hourComboBox;
    @FXML private TextField changeHoursToWorkLabel;
    @FXML private Label hourToWorkChanged;
    @FXML private Label hourAdded;

    private MainScreenController ms;
    private Connection connection = null;
    private PreparedStatement pst = null;

    private int id;
    private String firstName;
    private String lastName;
    private int totalHoursWorked;


    private final ObservableList<String> typeComboBoxList =
            FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");

    /**
     * initializes the hour control screen.
     * gets the customerID information from main screen.
     * @throws SQLException if SQLite query fails
     */
    @FXML
    public void initialize() throws SQLException {
        hourComboBox.setItems(typeComboBoxList);
        id = ms.employeeIdFromTable;
        ResultSet rs = null;

        String query = "SELECT * from staff where id = ?;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            totalHoursWorked = rs.getInt("totalHoursWorked");

            hourAdded.setStyle("-fx-text-fill: red");
            hourAdded.setText(firstName + " " + lastName);

        } catch (Exception e) {
            System.out.println("Problem is in here; " + e);
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Closes the window when cancel is clicked.
     * @param event is used to get information in current scene.
     * @throws SQLException if SQLite query fails.
     */
    @FXML
    public void onPressCancel(final ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Changes the hours to work value of the employee.
     * @param event is used to get information in current scene.
     */
    @FXML
    public void onChangePressButton(final ActionEvent event) {
        int hoursToWork = 0;
        try {
            hoursToWork = Integer.parseInt(changeHoursToWorkLabel.getText());
        } catch (Exception e) {
            hourToWorkChanged.setText("Please input an real value !");
            hourToWorkChanged.setStyle("-fx-text-fill: red");
        }
        final String query = "UPDATE staff SET hoursToWork = ? WHERE id =?; ";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, hoursToWork);
            pst.setInt(2, id);
            pst.executeUpdate();
            hourToWorkChanged.setText("New hours to work for  " + firstName
                    + " " + lastName + "is " + hoursToWork);
            hourToWorkChanged.setStyle("-fx-text-fill: green");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Add working hours to the Employees total hours worked.
     * @param event is used to get information in current scene.
     * @throws SQLException if SQLite query fails.
     */
    @FXML
    public void onAddHourPressButton(final ActionEvent event) throws SQLException {
        final String hourInString = hourComboBox.getSelectionModel().getSelectedItem();
        final int hoursToAdd = Integer.parseInt(hourInString);
        totalHoursWorked += hoursToAdd;
        String query = "UPDATE staff SET totalHoursWorked = ? WHERE id =?; ";

        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, totalHoursWorked);
            pst.setInt(2, id);
            pst.executeUpdate();
            hourAdded.setStyle("-fx-text-fill: green");
            hourAdded.setText("Total of " + hoursToAdd
                    + " hours has been added to " + firstName + " " + lastName);


        } catch (Exception e) {
            System.out.println("Problem is in here; " + e);
        } finally {
            pst.close();
            connection.close();
        }

    }
}
