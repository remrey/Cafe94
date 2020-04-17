package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A class which creates and stores information related to bookings
 * @author Lorcan
 * @version 1.0
 */
public class Booking {

    private final SimpleIntegerProperty bookingID;
    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty timePeriod;
    private final SimpleStringProperty date;
    private final SimpleBooleanProperty extended;
    private final SimpleIntegerProperty numberOfGuests;
    private final SimpleBooleanProperty approved;

    /**
     * A constructor which creates a booking from the supplied information.
     * @param bookingID This is the booking ID.
     * @param date The date for the booking in the restaurant.
     * @param timePeriod The time of the booking.
     * @param numberOfGuests The number of guests for the booking.
     * @param extended Whether the booking has been extended or not.
     * @param customerID The customer ID of the booking.
     */
    public Booking(final int bookingID, final String date, final String timePeriod,
                   final int numberOfGuests, final Boolean extended, final int customerID) {
        this.bookingID = new SimpleIntegerProperty(bookingID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.timePeriod = new SimpleStringProperty(timePeriod);
        this.date = new SimpleStringProperty(date);
        this.extended = new SimpleBooleanProperty(extended);
        this.numberOfGuests = new SimpleIntegerProperty(numberOfGuests);
        this.approved = new SimpleBooleanProperty(false);
    }

    /**
     * A default constructor for bookings.
     */
    public Booking() {
        this.bookingID = new SimpleIntegerProperty(0);
        this.customerID = new SimpleIntegerProperty(0);
        this.timePeriod = new SimpleStringProperty("zero");
        this.date = new SimpleStringProperty("00-00-00");
        this.extended = new SimpleBooleanProperty(false);
        this.numberOfGuests = new SimpleIntegerProperty(0);
        this.approved = new SimpleBooleanProperty(false);
    }

    /**
     * Returns the time for the object.
     * @return Returns the time of the booking.
     */
    public String getTimePeriod() {
        return timePeriod.get();
    }

    /**
     * Returns the time for the object.
     * @return Returns the time of the booking.
     */
    public SimpleStringProperty timePeriodProperty() {
        return timePeriod;
    }

    /**
     * Sets the time for the object.
     * @param timePeriod The time to be set for the booking.
     */
    public void setTimePeriod(final String timePeriod) {
        this.timePeriod.set(timePeriod);
    }

    /**
     * Returns the customer ID of the object.
     * @return Returns the customer ID for the booking.
     */
    public int getCustomerID() {
        return customerID.get();
    }

    /**
     * Returns the customer ID for the object.
     * @return Returns the time for the booking.
     */
    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    /**
     * Sets the customer ID of the object.
     * @param customerID Sets the customer ID of the booking.
     */
    public void setCustomerID(final int customerID) {
        this.customerID.set(customerID);
    }

    /**
     * Returns whether the booking has been extended.
     * @return Returns whether the booking has been extended.
     */
    public boolean isExtended() {
        return extended.get();
    }

    /**
     * Returns whether the booking has been extended.
     * @return Returns whether the booking has been extended.
     */
    public SimpleBooleanProperty extendedProperty() {
        return extended;
    }

    /**
     * Sets whether the booking has been extended.
     * @param extended Sets whether the booking has been extended.
     */
    public void setExtended(final boolean extended) {
        this.extended.set(extended);
    }

    /**
     * Returns whether the booking has been approved.
     * @return Returns whether the booking has been approved.
     */
    public boolean isApproved() {
        return approved.get();
    }

    /**
     * Returns whether the booking has been approved.
     * @return Returns whether the booking has been approved.
     */
    public SimpleBooleanProperty approvedProperty() {
        return approved;
    }

    /**
     * Sets whether the booking has been approved.
     * @param approved Sets whether the booking has been approved.
     */
    public void setApproved(final boolean approved) {
        this.approved.set(approved);
    }

    /**
     * Returns whether the booking has been extended.
     * @return Returns whether the booking has been extended.
     */
    public Boolean getExtended() {
        return extended.get();
    }

    /**
     * Returns the number of guests for the booking.
     * @return Returns the number of guests for the booking.
     */
    public int getNumberOfGuests() {
        return numberOfGuests.get();
    }

    /**
     * Returns the number of guests for the booking.
     * @return Returns the number of guests for the booking.
     */
    public SimpleIntegerProperty numberOfGuestsProperty() {
        return numberOfGuests;
    }

    /**
     * Sets the number of guests for the booking.
     * @param numberOfGuests Sets the number of guests for the booking.
     */
    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests.set(numberOfGuests);
    }

    /**
     * Returns the booking ID for the booking.
     * @return Returns the booking ID for the booking.
     */
    public int getBookingID() {
        return bookingID.get();
    }

    /**
     * Returns the booking ID for the booking.
     * @return Returns the booking ID for the booking.
     */
    public SimpleIntegerProperty bookingIDProperty() {
        return bookingID;
    }

    /**
     * Sets the booking ID for the booking.
     * @param bookingID Sets the booking ID for the booking.
     */
    public void setBookingID(final int bookingID) {
        this.bookingID.set(bookingID);
    }

    /**
     * Returns the date of the booking.
     * @return Returns the date of the booking.
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Returns the date of the booking.
     * @return Returns the date of the booking.
     */
    public SimpleStringProperty dateProperty() {
        return date;
    }

    /**
     * Sets the date of the booking.
     * @param date Sets the date of the booking.
     */
    public void setDate(final String date) {
        this.date.set(date);
    }
}
