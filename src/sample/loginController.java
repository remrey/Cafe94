package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class loginController {
    public loginManager logModel = new loginManager();
    @FXML private TextField user;
    @FXML private TextField pass;
    @FXML private Label signUpLabel;
    @FXML private AnchorPane rootPane;

    public void pressButton(ActionEvent event) throws IOException, SQLException {
        String us = user.getText();
        String pas = pass.getText();
        if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("Manager")) managerScreen(event);
        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("Customer")) customerScreen(event);
        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("Chef")) chefScreen(event);
        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("Waiter")) waiterScreen(event);
        else wrongScreen(event);
    }
    public void customerScreen(ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("CustomerScreen/customerHomeScreen.fxml"));
        logModel.connection.close();
        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }

    public void wrongScreen(ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("wrongInputLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Wrong input!");
        stage.setScene(new Scene(temp));
        stage.show();
    }

    public void signUpOnMouseClicked(MouseEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("signUpScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up!");
        stage.setScene(new Scene(temp));
        stage.show();
    }

    public void signUpOnMouseEnter(MouseEvent event) {
        signUpLabel.setTextFill(Color.web("#ff0000", 0.8));
        signUpLabel.setUnderline(true);
    }

    public void signUpOnMouseExit(MouseEvent event){
        signUpLabel.setTextFill(Color.web("#54a9cd", 0.8));
        signUpLabel.setUnderline(false);
    }

    public void managerScreen(ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("ManagerScreen/mainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }

    public void chefScreen(ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("ChefScreen/chefMainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }

    public void waiterScreen(ActionEvent event) throws IOException, SQLException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("WaiterScreen/waiterMainScreen.fxml"));
        logModel.connection.close();

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        rootPane.getChildren().setAll(temp);
    }
}
