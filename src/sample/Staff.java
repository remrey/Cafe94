package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Staff {

    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty hourToWork;
    private SimpleIntegerProperty totalHoursWorked;
    private SimpleStringProperty type;



    public Staff(SimpleIntegerProperty id, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleIntegerProperty hourToWork, SimpleIntegerProperty totalHoursWorked, SimpleStringProperty type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourToWork = hourToWork;
        this.totalHoursWorked = totalHoursWorked;
        this.type = type;
    }

    public Staff() {
        id = new SimpleIntegerProperty(0);
        firstName = new SimpleStringProperty("zero");
        lastName = new SimpleStringProperty("zero");
        hourToWork = new SimpleIntegerProperty(0);
        totalHoursWorked = new SimpleIntegerProperty(0);
        type = new SimpleStringProperty("zero");
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getHourToWork() {
        return hourToWork.get();
    }

    public SimpleIntegerProperty hourToWorkProperty() {
        return hourToWork;
    }

    public void setHourToWork(int hourToWork) {
        this.hourToWork.set(hourToWork);
    }

    public int getTotalHoursWorked() {
        return totalHoursWorked.get();
    }

    public SimpleIntegerProperty totalHoursWorkedProperty() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(int totalHoursWorked) {
        this.totalHoursWorked.set(totalHoursWorked);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}