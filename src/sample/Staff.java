package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Staff class file for creating and storing information about staff.
 * @author Emre.
 * @version 1.0
 */

public class Staff {

    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty hourToWork;
    private SimpleIntegerProperty totalHoursWorked;
    private SimpleStringProperty type;

    /**
     * A constructor which creates a Staff object from provided parameters.
     * @param id staff user ID.
     * @param firstName the first name of staff member.
     * @param lastName the last name of staff member.
     * @param hourToWork amount of hours currently signed up to work.
     * @param totalHoursWorked total hours worked as a staff member
     * @param type staff type: delivery driver, waiter, chef, or manager.
     */

    public Staff(SimpleIntegerProperty id, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleIntegerProperty hourToWork, SimpleIntegerProperty totalHoursWorked, SimpleStringProperty type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourToWork = hourToWork;
        this.totalHoursWorked = totalHoursWorked;
        this.type = type;
    }

    /**
     * Default constructor for staff.
     */

    public Staff() {
        id = new SimpleIntegerProperty(0);
        firstName = new SimpleStringProperty("zero");
        lastName = new SimpleStringProperty("zero");
        hourToWork = new SimpleIntegerProperty(0);
        totalHoursWorked = new SimpleIntegerProperty(0);
        type = new SimpleStringProperty("zero");
    }

    /**
     * Gets the staff user ID.
     * @return integer value of the staff user ID.
     */

    public int getId() {
        return id.get();
    }

    /**
     * Gets the staff user ID.
     * @return SimpleIntegerProperty value of the staff user ID.
     */

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * Sets the staff ID.
     * @param id takes the value to set the staff ID to.
     */

    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets the staff first name.
     * @return String value of the first name.
     */

    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Gets the staff first name.
     * @return SimpleStringProperty value of the first name.
     */

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Sets the first name of the staff.
     * @param firstName takes the value to set the staff first name to.
     */

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets the staff last name.
     * @return String value of the last name.
     */

    public String getLastName() {
        return lastName.get();
    }

    /**
     * Gets the staff last name.
     * @return SimpleStringProperty value of the last name.
     */

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Sets the last name of the staff.
     * @param lastName takes the value to set the last name to.
     */

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Gets the hours a staff member has to work.
     * @return integer value of hours to work.
     */

    public int getHourToWork() {
        return hourToWork.get();
    }

    /**
     * Gets the hours a staff member has to work.
     * @return SimpleIntegerPropery value of hours to work.
     */

    public SimpleIntegerProperty hourToWorkProperty() {
        return hourToWork;
    }

    /**
     * Sets the hours left to work of the staff member.
     * @param hourToWork takes the value to set hours to work to.
     */

    public void setHourToWork(int hourToWork) {
        this.hourToWork.set(hourToWork);
    }

    /**
     * Gets the total hours a staff member has worked.
     * @return integer value of total hours worked.
     */

    public int getTotalHoursWorked() {
        return totalHoursWorked.get();
    }

    /**
     * Gets the total hours a staff member has worked.
     * @return SimpleIntegerProperty value of total hours worked.
     */

    public SimpleIntegerProperty totalHoursWorkedProperty() {
        return totalHoursWorked;
    }

    /**
     * Sets the total hours worked of the staff member.
     * @param totalHoursWorked takes the value to set the total hours worked to.
     */

    public void setTotalHoursWorked(int totalHoursWorked) {
        this.totalHoursWorked.set(totalHoursWorked);
    }

    /**
     * Gets the type of staff member.
     * @return String value of the type of staff member.
     */

    public String getType() {
        return type.get();
    }

    /**
     * Gets the type of staff member.
     * @return String value of the type of staff member.
     */

    public SimpleStringProperty typeProperty() {
        return type;
    }

    /**
     * Sets the type of a staff member.
     * @param type takes the value to set the type to.
     */

    public void setType(String type) {
        this.type.set(type);
    }
}
