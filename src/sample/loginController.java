package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class loginController {
    public loginManager logModel = new loginManager();
    @FXML private TextField user;
    @FXML private TextField pass;
    @FXML private AnchorPane rootPane;

    public void pressButton(ActionEvent event) throws IOException {
        String us = user.getText();
        String pas = pass.getText();
        if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("manager")) secondScreen(event);
        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("customer")) customerScreen(event);        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("customer")) customerScreen(event);
        //remember to change bottomLine to different screen
        else if(logModel.isUserLegit(user.getText(),pass.getText()) && logModel.userType(user.getText(),pass.getText()).equals("staff")) customerScreen(event);
        else wrongScreen(event);
    }
    public void customerScreen(ActionEvent event) throws IOException{
        AnchorPane temp = FXMLLoader.load(getClass().getResource("userScreen.fxml"));

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(651);
        primaryStage.setMinWidth(603);
        rootPane.getChildren().setAll(temp);
    }

    public void wrongScreen(ActionEvent event)throws IOException{
        AnchorPane temp = FXMLLoader.load(getClass().getResource("wrongInputLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Wrong input!");
        stage.setScene(new Scene(temp));
        stage.show();
    }



    public void secondScreen(ActionEvent event) throws IOException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));

        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.setMinHeight(651);
        primaryStage.setMinWidth(603);
        rootPane.getChildren().setAll(temp);
    }

}
