package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User{
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty address;

    public User(final SimpleIntegerProperty id, final SimpleStringProperty firstName, final SimpleStringProperty lastName, final SimpleStringProperty address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(final String address) {
        this.address.set(address);
    }

    public User() {
        this.id = new SimpleIntegerProperty(0);
        this.firstName = new SimpleStringProperty("zero");
        this.lastName = new SimpleStringProperty("zero");
        this.address = new SimpleStringProperty("zero");
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(final int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName.set(lastName);
    }
}

