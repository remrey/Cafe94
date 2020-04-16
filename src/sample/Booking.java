package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Booking {

    private final SimpleIntegerProperty bookingID;
    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty timePeriod;
    private final SimpleStringProperty date;
    private final SimpleBooleanProperty extended;
    private final SimpleIntegerProperty numberOfGuests;
    private final SimpleBooleanProperty approved;

    public Booking(int bookingID, String date, String timePeriod, int numberOfGuests, Boolean extended, int customerID){
        this.bookingID = new SimpleIntegerProperty(bookingID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.timePeriod = new SimpleStringProperty(timePeriod);
        this.date = new SimpleStringProperty(date);
        this.extended = new SimpleBooleanProperty(extended);
        this.numberOfGuests = new SimpleIntegerProperty(numberOfGuests);
        this.approved = new SimpleBooleanProperty(false);
    }

    public Booking(){
        this.bookingID = new SimpleIntegerProperty(0);
        this.customerID = new SimpleIntegerProperty(0);
        this.timePeriod = new SimpleStringProperty("zero");
        this.date = new SimpleStringProperty("00-00-00");
        this.extended = new SimpleBooleanProperty(false);
        this.numberOfGuests = new SimpleIntegerProperty(0);
        this.approved = new SimpleBooleanProperty(false);
    }

    public String getTimePeriod() {
        return timePeriod.get();
    }

    public SimpleStringProperty timePeriodProperty() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod.set(timePeriod);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public boolean isExtended() {
        return extended.get();
    }

    public SimpleBooleanProperty extendedProperty() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended.set(extended);
    }

    public boolean isApproved() {
        return approved.get();
    }

    public SimpleBooleanProperty approvedProperty() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved.set(approved);
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

    public int getBookingID() {
        return bookingID.get();
    }

    public SimpleIntegerProperty bookingIDProperty() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID.set(bookingID);
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
