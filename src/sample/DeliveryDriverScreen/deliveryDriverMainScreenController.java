package sample.DeliveryDriverScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Order;
import sample.DBManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The main screen for the Delivery Driver.
 * From here they can view unassigned delivery orders
 * and assign themselves to them along with mark them
 * as delivered once delivered.
 * @author Luis, Emre, George
 * @version 1.0
 */

public class DeliveryDriverMainScreenController {
    @FXML Button assignButton;
    @FXML Button refreshButton;
    @FXML Button deliveredButton;
    @FXML private TableView<Order> deliveryDriverView;
    @FXML private TableColumn<Order, Integer> driverIDColumn;
    @FXML private TableColumn<Order, Integer> orderNoColumn;
    @FXML private TableColumn<Order, String> deliveryAddressColumn;
    Connection connection = null;
    ResultSet rs = null;
    ResultSet rsOrder = null;
    PreparedStatement pst = null;

    /**
     * This create a temporary order for the screen to use.
     * @param rsOrder Result of the query.
     * @return Result of the query.
     * @throws SQLException Throws if SQLite query fails.
     */
    private ObservableList<Order> getOrders(ResultSet rsOrder) throws SQLException {
        ObservableList<Order> tempItemList = FXCollections.observableArrayList();
        while(rsOrder.next()){
            Order temp = new Order();
            temp.setDriverID(rsOrder.getInt("driverID"));
            temp.setOrderNo(rsOrder.getInt("orderNo"));
            temp.setDeliveryAddress(rsOrder.getString("deliveryAddress"));
            tempItemList.add(temp);
        }
        return tempItemList;
    }

    @SuppressWarnings({"checkstyle:OperatorWrap", "checkstyle:WhitespaceAfter"})
    /**
     * Initialize function connects to the database and sets the list of
     * viewable items from the orders.
     * @throws SQLException Throws if SQLite query fails.
     */
    public void initialize() throws IOException, SQLException {
        int currentDriver = sample.UserDetails.getInstance().getUserID();
        String query = "SELECT * FROM orders WHERE (waiterServed = True " +
                "AND orderType = 'delivery' AND delivered ='False' " +
                "AND (driverID = -1 OR driverID = ?)) GROUP BY orderNo";
        connection = DBManager.DBConnection();
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1,currentDriver);
            rsOrder = pst.executeQuery();
            ObservableList<Order> orderList = getOrders(rsOrder);
            driverIDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("driverID"));
            orderNoColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNo"));
            deliveryAddressColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));
            deliveryDriverView.setItems(orderList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            connection.close();
        }
    }
    /**
     * Allows the order to be marked as delivered upon a button press.
     * @param event Used to get information in current scene
     * @throws SQLException Throws if SQLite query fails.
     * @throws IOException Throws if input fails.
     */
    public void deliveredOrder(ActionEvent event) throws SQLException, IOException {
        connection = DBManager.DBConnection();
        int id = deliveryDriverView.getSelectionModel().getSelectedItem().getOrderNo();
        String delivered = "UPDATE orders SET delivered = 'True' WHERE orderNo = ?;";
        try {
            pst = connection.prepareStatement(delivered);
            pst.setInt(1,id);
            pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            pst.close();
            connection.close();
            initialize();

        }
    }
    /**
     * Allows the driver to to assign themselves to any empty orders.
     * @param event Used to get information in current scene
     * @throws SQLException Throws if SQLite query fails.
     * @throws IOException Throws if input fails.
     */
    public void assignDelivery(ActionEvent event) throws SQLException, IOException {
        int currentDriver = sample.UserDetails.getInstance().getUserID();
        connection = DBManager.DBConnection();
        int id = deliveryDriverView.getSelectionModel().getSelectedItem().getOrderNo();
        String assign = "UPDATE Orders SET driverID = " + currentDriver + " WHERE orderNo = ?";
        try {
            pst = connection.prepareStatement(assign);
            pst.setInt(1,id);
            pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            connection.close();
            pst.close();
            initialize();
        }
    }

    /**
     * This allows the driver to sign out of the system.
     * @param event Used to get information in current scene
     * @throws IOException Throws if input fails.
     */

    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
