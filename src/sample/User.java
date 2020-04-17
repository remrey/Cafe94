package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class file to create and store data on users.
 * @author Emre.
 * @version 1.0.
 */

public class User{
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty address;

    /**
     * Constructs a User object using given parameters.
     * @param id the user ID.
     * @param firstName the users first name.
     * @param lastName the users last name.
     * @param address the users address.
     */

    public User(SimpleIntegerProperty id, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    /**
     * Default constructor for a user class.
     */

    public User(){
        this.id = new SimpleIntegerProperty(0);
        this.firstName = new SimpleStringProperty("zero");
        this.lastName = new SimpleStringProperty("zero");
        this.address = new SimpleStringProperty("zero");
    }

    /**
     * Gets the address of a user.
     * @return String value of the user address.
     */

    public String getAddress() {
        return address.get();
    }

    /**
     * Gets the address of a user.
     * @return SimpleStringProperty value of the user address.
     */

    public SimpleStringProperty addressProperty() {
        return address;
    }

    /**
     * Sets the address of a user.
     * @param address takes the value to set the address to.
     */

    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Gets the ID of the user.
     * @return integer value of the user ID.
     */

    public int getId() {
        return id.get();
    }

    /**
     * Gets the ID of the user.
     * @return SimpleIntegerProperty value of the user ID.
     */

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * @param id takes the value to set the ID to.
     */

    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets the first name of the user.
     * @return String value of the user first name.
     */

    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Gets the first name of the user.
     * @return SimpleStringProperty value of the user first name.
     */

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName takes the value to set the first name to.
     */

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets the last name of the user.
     * @return String value of the user last name.
     */

    public String getLastName() {
        return lastName.get();
    }

    /**
     * Gets the last name of the user.
     * @return SimpleStringProperty value of the user last name.
     */

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName takes the value to set the last name to.
     */

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}

