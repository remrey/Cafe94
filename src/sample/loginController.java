
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A class that used to navigate the login screen.
 * E.g. if the user is Chef, it leads to chef screen.
 * @author F. Emre YILMAZ
 * @version 1.0
 */
public class LoginController {
    public LoginManager logModel = new LoginManager();
    @FXML private TextField user;
    @FXML private TextField pass;
    @FXML private Label signUpLabel;
    @FXML private AnchorPane rootPane;

    /**
     * Checks the users identity.
     * That identity can be; Manager, Customer, Chef, Waiter or Delivery Driver.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    @FXML
    public void pressButton(final ActionEvent event) throws IOException, SQLException {
        final String username = user.getText();
        final String passw = pass.getText();
        final boolean isUserExist = logModel.isUserLegit(username, passw);
        final String userType = logModel.userType(username, passw);

        if (isUserExist && userType.equals("Manager")) {
            managerScreen(event);
        } else if (isUserExist && userType.equals("Customer")) {
            customerScreen(event);
        } else if (isUserExist && userType.equals("Chef")) {
            chefScreen(event);
        } else if (isUserExist && userType.equals("Waiter")) {
            waiterScreen(event);
        } else if (isUserExist && userType.equals("Delivery Driver")) {
            deliveryDriverScreen(event);
        } else {
            wrongScreen(event);
        }
    }

    /**
     * Directs to delivery screen is the user a delivery driver.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void deliveryDriverScreen(final ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("DeliveryDriverScreen/deliveryDriverMainScreen.fxml"));
        logModel.connection.close();
        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }


    /**
     * Directs to customer screen is the user a customer.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void customerScreen(final ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("CustomerScreen/customerHomeScreen.fxml"));
        logModel.connection.close();
        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }

    /**
     * Pops up wrong input screen if the inputs dont match with db.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void wrongScreen(final ActionEvent event) throws IOException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("wrongInputLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Wrong input!");
        stage.setScene(new Scene(temp));
        stage.show();
    }


    /**
     * Lead to signup screen for a new user creation.
     * @param event is used to get information in current scene
     * @throws IOException if input fails.
     */
    public void signUpOnMouseClicked(final MouseEvent event) throws IOException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("signUpScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up!");
        stage.setScene(new Scene(temp));
        stage.show();
    }

    /**
     * Changes the colour to red when mouse is over the label.
     * @param event is used to get information in current scene
     */
    public void signUpOnMouseEnter(final MouseEvent event) {
        signUpLabel.setTextFill(Color.web("#ff0000", 0.8));
        signUpLabel.setUnderline(true);
    }


    /**
     * Changes the colour to blue when mouse is no longer over the label.
     * @param event is used to get information in current scene
     */
    public void signUpOnMouseExit(final MouseEvent event) {
        signUpLabel.setTextFill(Color.web("#54a9cd", 0.8));
        signUpLabel.setUnderline(false);
    }


    /**
     *
     * @param event is used to get information in current scene
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void managerScreen(final ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("ManagerScreen/MainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }

    /**
     * Directs to chef screen is the user chef.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void chefScreen(final ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("ChefScreen/chefMainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }


    /**
     * Directs to waiterscreen is the user waiter.
     * @param event is used to get information in current scene.
     * @throws IOException if input fails.
     * @throws SQLException if SQLite query fails.
     */
    public void waiterScreen(final ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("WaiterScreen/waiterMainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }
}
