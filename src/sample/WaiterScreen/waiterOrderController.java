package sample.WaiterScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class waiterOrderController {

    @FXML private Label totalOrderLabel;
    @FXML private CheckBox fav1CheckBox;
    @FXML private CheckBox fav2CheckBox;
    @FXML private CheckBox fav3CheckBox;
    @FXML private CheckBox fav4CheckBox;
    @FXML private CheckBox fav5CheckBox;
    @FXML private CheckBox fav6CheckBox;
    @FXML private CheckBox fav7CheckBox;
    @FXML private CheckBox fav8CheckBox;
    @FXML private CheckBox start1CheckBox;
    @FXML private CheckBox start2CheckBox;
    @FXML private CheckBox start3CheckBox;
    @FXML private CheckBox start4CheckBox;
    @FXML private CheckBox start5CheckBox;
    @FXML private CheckBox start6CheckBox;
    @FXML private CheckBox start7CheckBox;
    @FXML private CheckBox start8CheckBox;
    @FXML private CheckBox main1CheckBox;
    @FXML private CheckBox main2CheckBox;
    @FXML private CheckBox main3CheckBox;
    @FXML private CheckBox main4CheckBox;
    @FXML private CheckBox main5CheckBox;
    @FXML private CheckBox main6CheckBox;
    @FXML private CheckBox main7CheckBox;
    @FXML private CheckBox main8CheckBox;
    @FXML private CheckBox side1CheckBox;
    @FXML private CheckBox side2CheckBox;
    @FXML private CheckBox side3CheckBox;
    @FXML private CheckBox side4CheckBox;
    @FXML private CheckBox side5CheckBox;
    @FXML private CheckBox side6CheckBox;
    @FXML private CheckBox side7CheckBox;
    @FXML private CheckBox side8CheckBox;
    @FXML private CheckBox dessert1CheckBox;
    @FXML private CheckBox dessert2CheckBox;
    @FXML private CheckBox dessert3CheckBox;
    @FXML private CheckBox dessert4CheckBox;
    @FXML private CheckBox dessert5CheckBox;
    @FXML private CheckBox dessert6CheckBox;
    @FXML private CheckBox dessert7CheckBox;
    @FXML private CheckBox dessert8CheckBox;
    @FXML private CheckBox drink1CheckBox;
    @FXML private CheckBox drink2CheckBox;
    @FXML private CheckBox drink3CheckBox;
    @FXML private CheckBox drink4CheckBox;
    @FXML private CheckBox drink5CheckBox;
    @FXML private CheckBox drink6CheckBox;
    @FXML private CheckBox drink7CheckBox;
    @FXML private CheckBox drink8CheckBox;

    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("waiterHomeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /*public void viewBookingsButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }*/

    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void checkOrderButtonPushed() {

        String order = "Total order consists of: ";

        if (fav1CheckBox.isSelected()) {order += "\nFav 1";}
        if (fav2CheckBox.isSelected()) {order += "\nFav 2";}
        if (fav3CheckBox.isSelected()) {order += "\nFav 3";}
        if (fav4CheckBox.isSelected()) {order += "\nFav 4";}
        if (fav5CheckBox.isSelected()) {order += "\nFav 5";}
        if (fav6CheckBox.isSelected()) {order += "\nFav 6";}
        if (fav7CheckBox.isSelected()) {order += "\nFav 7";}
        if (fav8CheckBox.isSelected()) {order += "\nFav 8";}
        if (start1CheckBox.isSelected()) {order += "\nStarter 1";}
        if (start2CheckBox.isSelected()) {order += "\nStarter 2";}
        if (start3CheckBox.isSelected()) {order += "\nStarter 3";}
        if (start4CheckBox.isSelected()) {order += "\nStarter 4";}
        if (start5CheckBox.isSelected()) {order += "\nStarter 5";}
        if (start6CheckBox.isSelected()) {order += "\nStarter 6";}
        if (start7CheckBox.isSelected()) {order += "\nStarter 7";}
        if (start8CheckBox.isSelected()) {order += "\nStarter 8";}
        if (main1CheckBox.isSelected()) {order += "\nMain 1";}
        if (main2CheckBox.isSelected()) {order += "\nMain 2";}
        if (main3CheckBox.isSelected()) {order += "\nMain 3";}
        if (main4CheckBox.isSelected()) {order += "\nMain 4";}
        if (main5CheckBox.isSelected()) {order += "\nMain 5";}
        if (main6CheckBox.isSelected()) {order += "\nMain 6";}
        if (main7CheckBox.isSelected()) {order += "\nMain 7";}
        if (main8CheckBox.isSelected()) {order += "\nMain 8";}
        if (side1CheckBox.isSelected()) {order += "\nSide 1";}
        if (side2CheckBox.isSelected()) {order += "\nSide 2";}
        if (side3CheckBox.isSelected()) {order += "\nSide 3";}
        if (side4CheckBox.isSelected()) {order += "\nSide 4";}
        if (side5CheckBox.isSelected()) {order += "\nSide 5";}
        if (side6CheckBox.isSelected()) {order += "\nSide 6";}
        if (side7CheckBox.isSelected()) {order += "\nSide 7";}
        if (side8CheckBox.isSelected()) {order += "\nSide 8";}
        if (dessert1CheckBox.isSelected()) {order += "\nDessert 1";}
        if (dessert2CheckBox.isSelected()) {order += "\nDessert 2";}
        if (dessert3CheckBox.isSelected()) {order += "\nDessert 3";}
        if (dessert4CheckBox.isSelected()) {order += "\nDessert 4";}
        if (dessert5CheckBox.isSelected()) {order += "\nDessert 5";}
        if (dessert6CheckBox.isSelected()) {order += "\nDessert 6";}
        if (dessert7CheckBox.isSelected()) {order += "\nDessert 7";}
        if (dessert8CheckBox.isSelected()) {order += "\nDessert 8";}
        if (drink1CheckBox.isSelected()) {order += "\nDrink 1";}
        if (drink2CheckBox.isSelected()) {order += "\nDrink 2";}
        if (drink3CheckBox.isSelected()) {order += "\nDrink 3";}
        if (drink4CheckBox.isSelected()) {order += "\nDrink 4";}
        if (drink5CheckBox.isSelected()) {order += "\nDrink 5";}
        if (drink6CheckBox.isSelected()) {order += "\nDrink 6";}
        if (drink7CheckBox.isSelected()) {order += "\nDrink 7";}
        if (drink8CheckBox.isSelected()) {order += "\nDrink 8";}


        this.totalOrderLabel.setText(order);
    }

}
