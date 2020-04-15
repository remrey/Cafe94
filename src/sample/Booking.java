package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Booking {

    private final SimpleIntegerProperty bookingID;
    private final SimpleStringProperty customerID;
    private final SimpleStringProperty time;
    private final SimpleStringProperty date;
    private final SimpleBooleanProperty extended;
    private final SimpleIntegerProperty numberOfGuests;
    private boolean approved;

    public Booking(int bookingID, String date, String time, int numberOfGuests, Boolean extended, String customerID){
        this.bookingID = new SimpleIntegerProperty(bookingID);
        this.customerID = new SimpleStringProperty(customerID);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
        this.extended = new SimpleBooleanProperty(extended);
        this.numberOfGuests = new SimpleIntegerProperty(numberOfGuests);
        this.approved = false;
    }

    public Boolean getExtended() {
        return extended.get();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.get();
    }

    public SimpleIntegerProperty numberOfGuestsProperty() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests.set(numberOfGuests);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getBookingID() {
        return bookingID.get();
    }

    public SimpleIntegerProperty bookingIDProperty() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID.set(bookingID);
    }

    public String getCustomerID() {
        return customerID.get();
    }

    public SimpleStringProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID.set(customerID);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

}