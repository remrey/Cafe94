package sample;

import com.sun.tools.javac.comp.Check;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainScreenController {
    @FXML private AnchorPane employeePane;
    @FXML private CheckBox popularItem;
    @FXML private CheckBox busiestPeriod;
    @FXML private CheckBox activeCustomer;
    @FXML private CheckBox highestWork;
    @FXML private TableView<Report> table;
    @FXML private TableColumn<Report,Integer> tableViewId;
    @FXML private TableColumn<Report,String> tableViewReport;
    @FXML private TableColumn<Report,String> tableViewName;
    @FXML private TableColumn<Report,Integer> tableViewCount;
    @FXML private TableView<Staff> staffTable;
    @FXML private TableColumn<Staff,Integer> staffId;
    @FXML private TableColumn<Staff,String> staffFirstName;
    @FXML private TableColumn<Staff,String> staffLastName;
    @FXML private TableColumn<Staff,Integer> staffHoursToWork;
    @FXML private TableColumn<Staff,Integer> staffTotalHoursWorked;
    Connection connection = null;
    ResultSet rs = null;
    ResultSet rsEmployee = null;
    PreparedStatement pst = null;


    public ObservableList<Report> list = FXCollections.observableArrayList();



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

    public void onShowPressButton(ActionEvent event) throws IOException{
        list.clear();
        boolean itemCheck = popularItem.isSelected();
        boolean periodCheck = busiestPeriod.isSelected();
        boolean customerCheck = activeCustomer.isSelected();
        boolean workCheck = highestWork.isSelected();

        if(workCheck) list.addAll(new Report(234,"Highest Number of Hours Work","Stefan",250));
        if(itemCheck) list.addAll(new Report(12,"Most Popular Item","Chicken Burger",25));
        if(periodCheck) list.addAll(new Report(14,"Busiest Period","5.00-6.00",12));
        if(customerCheck) list.addAll(new Report(26,"Most Active Customer","Joe",15));

        tableViewId.setCellValueFactory(new PropertyValueFactory<Report,Integer>("Id"));
        tableViewReport.setCellValueFactory(new PropertyValueFactory<Report,String>("Description"));
        tableViewName.setCellValueFactory(new PropertyValueFactory<Report,String>("Name"));
        tableViewCount.setCellValueFactory(new PropertyValueFactory<Report,Integer>("Amount"));
        table.setItems(list);

    }
}
