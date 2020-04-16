
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
 * A window that pops-up when Add Employee
 * button clicked in the Managers Main Screen.
 * Used to create a new Employee.
 * @author F. Emre YILMAZ
 * @version 1.0
 */

public class AddEmployeeController {

    @FXML private TextField userName;
    @FXML private TextField name;
    @FXML private TextField lastName;
    @FXML private Label userNameTakenError;
    @FXML private Label userNameEmptyError;
    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label typeError;
    @FXML private Label successLabel;
    @FXML private ComboBox<String> typeComboBox;


    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private final ObservableList<String>  typeComboBoxList =
            FXCollections.observableArrayList("Chef", "Delivery Driver", "Waiter");

    /**
     * Used to initialize the system.
     */
    @FXML
    public void initialize() {
        typeComboBox.setItems(typeComboBoxList);
    }

    /**
     * Used to close the window when.
     * @param event
     */
    @FXML
    private void onPressCancel(final ActionEvent event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Used the insert information of the new
     * Employee to the database after the ok button
     * pressed.
     * @param event is used to get information in current scene.
     */

    @FXML
    private void onPressButton(final ActionEvent event) {
        String query = "INSERT INTO staff(firstName, lastName,type, "
                + "hoursToWork,totalHoursWorked) VALUES(?,?,?,?,?)";
        String sql = "CREATE TABLE IF NOT EXISTS staff(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	firstName varchar(255) NOT NULL,\n"
                + "	lastName varchar(255) NOT NULL, \n"
                + " type varchar(255) NOT NULL, \n"
                + " hoursToWork int NOT NULL, \n"
                + " totalHoursWorked int NOT NULL \n"
                + ");";
        String userQuery = "INSERT INTO users(userName,firstName,"
                + "lastName,password,type) VALUES(?,?,?,1234,?)";
        conn = DBManager.DBConnection();
        final String firstName = name.getText();
        final String lName = lastName.getText();
        final String usName = userName.getText();
        final boolean firstNameCheck = firstName.isEmpty();
        final boolean lastNameCheck = lName.isEmpty();
        final boolean userNameCheck = usName.isEmpty();
        if (firstNameCheck) {
            firstNameError.setVisible(true);
        } else {
            firstNameError.setVisible(false);
        }
        if (lastNameCheck) {
            lastNameError.setVisible(true);
        } else {
            lastNameError.setVisible(false);
        }
        if (typeComboBox.getSelectionModel().isEmpty()) {
            typeError.setVisible(true);
        } else {
            typeError.setVisible(false);
        }
        if (userNameCheck) {
            userNameEmptyError.setVisible(true);
        } else {
            userNameEmptyError.setVisible(false);
        }
        if (!firstNameCheck && !lastNameCheck && !userNameCheck) {
            try {
                userNameTakenError.setVisible(false);
                PreparedStatement tableCheck = conn.prepareStatement((sql));
                tableCheck.executeUpdate();
                PreparedStatement uState = conn.prepareStatement(userQuery);
                uState .setString(1, userName.getText());
                uState .setString(2, name.getText());
                uState .setString(3, lastName.getText());
                final String type = typeComboBox.getSelectionModel().getSelectedItem();
                uState .setString(4, type);
                uState .executeUpdate();
                pst = conn.prepareStatement(query);
                pst.setString(1, firstName);
                pst.setString(2, lName);
                pst.setString(3, type);
                pst.setInt(4, 0);
                pst.setInt(5, 0);
                pst.executeUpdate();
                successLabel.setText(firstName + " " + lName
                        + " " + (successLabel.getText()));
                successLabel.setVisible(true);
                name.clear();
                lastName.clear();
                userName.clear();
                typeComboBox.valueProperty().set(null);
            } catch (SQLException e) {
                userNameTakenError.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error has occured here: " + e);
            }

        }


    }
}
