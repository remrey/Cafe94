package sample.customerScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class customerBookingController {
    @FXML DatePicker bookingDate;
    @FXML TextField bookingDuration;
    @FXML TextField bookingTime;
    @FXML TextField bookingNumberOfGuests;
    @FXML TextField bookingPhoneNumber;
    @FXML Button bookingButton;
    @FXML TextArea bookingSpecialRequests;

    public void createBooking(){
        LocalDate date = bookingDate.getValue();
        try{
            int duration = Integer.parseInt(bookingDuration.getText());
            double time = Double.parseDouble(bookingTime.getText());
            int numberOfGuests = Integer.parseInt(bookingNumberOfGuests.getText());
            int phoneNumber = Integer.parseInt(bookingPhoneNumber.getText());

//            user user = new user("s", "s", "w", "w", "e");
//            Booking booking = new Booking(customer, numberOfGuests, time, duration, date, false);
            String specialRequest = bookingSpecialRequests.getText();
        }
        catch (NumberFormatException e){
            System.out.println("Invalid number. Please enter a valid number");
        }
    }
}
