
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
 * A window that pops-up when Edit Employee button clicked.
 * Used to edit employee information.
 * @author F. Emre YILMAZ
 * @version 1.0
 */
public class EditEmployeeController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private Label successLabel;

    private MainScreenController ms;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String type;
    private int id;
    private final ObservableList<String> typeComboBoxList =
            FXCollections.observableArrayList("Chef", "Delivery Driver", "Waiter");


    /**
     * Used to initialize edit employee screen.
     * Sets the label green and puts items to combobox.
     * Gets the employee information from database.
     * Fills the text fields with employee information.
     * @throws SQLException if SQLite query fails.
     */
    @FXML
    public void initialize() throws SQLException {
        successLabel.setVisible(false);
        successLabel.setStyle("-fx-text-fill: green");
        typeComboBox.setItems(typeComboBoxList);
        id = ms.employeeIdFromTable;

        String query = "SELECT * from staff where id = ?;";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            firstName.setText(rs.getString("firstName"));
            lastName.setText(rs.getString("lastName"));
            type = rs.getString("type");

        } catch (Exception e) {
            System.out.println("Problem is in here; " + e);
        } finally {
            pst.close();
            connection.close();
        }
    }

    /**
     * Closes the edit employee screen.
     * @param event is used to get information in current scene.
     */
    public void onPressCancel(final ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * updates the employee information when ok button pressed.
     * @param event event is used to get information in current scene.
     * @throws SQLException
     */
    public void onButtonPressed(final ActionEvent event) throws SQLException {
        String editFirstName = firstName.getText();
        String editLastName = lastName.getText();
        String editType = typeComboBox.getSelectionModel().getSelectedItem();
        id = ms.employeeIdFromTable;
        String query = "UPDATE staff SET firstName = ? ,"
                + " lastName = ?, type = ? WHERE id = ?;";


        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, editFirstName);
            pst.setString(2, editLastName);
            pst.setString(3, editType);
            pst.setInt(4, id);
            pst.executeUpdate();
            successLabel.setVisible(true);

        } catch (Exception e) {
            System.out.println("Problem is in here; " + e);
        } finally {
            pst.close();
            connection.close();


        }


    }
}
