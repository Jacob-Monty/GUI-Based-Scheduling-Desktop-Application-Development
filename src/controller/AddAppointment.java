package controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.UserDAO;
import DAO.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.Appointment;
import model.Contacts;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 *
 * Add Appointment Class
 *
 * @ Author Jacob Montoya
 *
 */
public class AddAppointment implements Initializable {


    @FXML
    private TextField appointmentIdTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox<LocalTime> startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<LocalTime> endTime;
    @FXML
    private ComboBox<Customer> customerIDComboBox;
    @FXML
    private ComboBox<User> userIDComboBox;
    @FXML
    private ComboBox<Contacts> contactComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final int noOfDaysToAdd = 0;

    /**
     *
     * Save button to add appointment to table and database. Will display alerts if any fields are blank or not completed.
     * It will also verify if there are any overlapping appointments already in database
     *
     * @param actionEvent actionEvent to Save Button
     * @throws IOException IOException
     * @throws SQLException SQLException
     */

    public void onPushSaveButton(ActionEvent actionEvent) throws IOException, SQLException {

        String appointmentTitle = titleTextField.getText();
        String appointmentType = typeTextField.getText();
        String appointmentDesc = descriptionTextField.getText();
        String appointmentLocal = locationTextField.getText();

            //Shows error if appointment title is empty or blank
        if (appointmentTitle.isEmpty() || appointmentTitle.isBlank()) {
            displayAlert(8);
            return;
        }

        //Shows error if appointment Type is empty or blank
        if (appointmentType.isEmpty() || appointmentType.isBlank()) {
            displayAlert(10);
            return;
        }
            //Shows error if appointment description is empty or blank
        if (appointmentDesc.isEmpty() || appointmentDesc.isBlank()) {
            displayAlert(9);
            return;
        }

            //Shows error if appointment title is empty or blank
        if (appointmentLocal.isEmpty() || appointmentLocal.isBlank()) {
             displayAlert(11);
             return;
        }

            // Shows null if no value is input or selected
        LocalDate startDatePick = startDate.getValue();
        if (startDatePick == null) {
            displayAlert(2);
            return;
        }
         // Shows null if no value is input or selected
        LocalTime startTimePick = startTime.getValue();
        if (startTimePick == null) {
            displayAlert(3);
            return;
        }
        LocalDateTime appointmentStartDate = LocalDateTime.of(startDate.getValue(), startTime.getValue());

        // Shows null if no value is input or selected
        LocalDate endDatePick = endDate.getValue();
        if (endDatePick == null) {
            displayAlert(4);
            return;
        }
        // Shows null if no value is input or selected
        LocalTime end = endTime.getValue();
        if (end == null) {
            displayAlert(5);
            return;
        }

        LocalDateTime appointmentEndDate = LocalDateTime.of(endDate.getValue(), endTime.getValue());

        // Shows null if no value is input or selected
        Customer customerID = customerIDComboBox.getValue();
        if (customerID == null) {
            displayAlert(6);
            return;
        }

        int appointmentCustomerID = customerIDComboBox.getValue().getCustomerID();

        // Shows null if no value is selected for User
        User userID = userIDComboBox.getValue();
        if (userID == null) {
            displayAlert(7);
            return;
        }

        Contacts contact = contactComboBox.getValue();

        if (contact == null) {
            displayAlert(1);
            return;
        }
        int appointmentContact = contact.getContactId();

        // Shows null if no value is input or selected. If all fields are entered correctly. It will save information to database
        int appointmentUserID = userIDComboBox.getValue().getUserID();

        //Shows error if appointment is outside of business hours
        if (Appointment.businessHour(appointmentStartDate, appointmentEndDate)) {
            return;
        //Shows error if appointment is overlapping with a current existing appointment
        } else if (Appointment.overlappingCheck(appointmentCustomerID, appointmentStartDate, appointmentEndDate)) {
            return;
        } else {
            AppointmentDAO.addAppointment(appointmentTitle, appointmentDesc, appointmentLocal, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCustomerID, appointmentUserID, appointmentContact);
            Appointment.backToScheduleScreen(actionEvent);
            confirmation(1);
        }
    }

    /**
     * Adds the minutes to autofill the end time for the appointment time.
     *
     * @param MinutesAdd MinutesAdd
     * @return return
     */
    public long plusMinutes(long MinutesAdd) {
        try {
            return MinutesAdd;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * LAMBDA Expression: Line 217 - gets start date value and auto-fills the end date.
     * LAMBDA Expression: Line 218 - gets start time and adds 30 minutes and auto-fills the end time.
     *
     */

    ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdTextField.setId(appointmentIdTextField.getId());
        startTime.setItems(Appointment.getTimes());
        endTime.setItems(Appointment.getTimes());
        ObservableList<Contacts> contactList = ContactsDAO.getContacts();
        contactComboBox.setItems(contactList);
        ObservableList<User> userList = UserDAO.getAllUsersList();
        userIDComboBox.setItems(userList);
        ObservableList<Customer> customerList = CustomerDAO.getAllCustomerList();
        customerIDComboBox.setItems(customerList);


        //Lambda Expressions
        /**
         * These allow the autofill of the ending time and date once starting time and date are picked.
         * 30 minutes addition to the starting time and same day for the appointment date.
         */
        startDate.valueProperty().addListener((ov, oldValueDate, newValueDate) -> endDate.setValue(newValueDate.plusDays(noOfDaysToAdd)));
        startTime.valueProperty().addListener((observableValue, oldValueTime, newValueTime) -> endTime.setValue(newValueTime.plusMinutes(30)));


    }

    /**
     * Event to cancel adding of an appointment and return to the Schedule Screen
     *
     * @param action action
     * @throws IOException IOException
     */
    public void onPushCancelButton(ActionEvent action) throws IOException {
        Appointment.backToScheduleScreen(action);
    }

    /**
     *
     * Alerts for all errors in this class.
     *
     * @param alertType alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("noContact"));
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankStartDate"));
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("noTime"));
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankEndDate"));
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("noEndTime"));
                alertError.showAndWait();
                break;
            case 6:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noCustomer"));
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noUser"));
                alert.showAndWait();
                break;
            case 8:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noTitle"));
                alert.showAndWait();
                break;
            case 9:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noDescription"));
                alert.showAndWait();
                break;
            case 10:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noType"));
                alert.showAndWait();
                break;
            case 11:
                alert.setTitle(langBundle.getString("Alert"));
                alert.setContentText(langBundle.getString("noLocation"));
                alert.showAndWait();
                break;
        }
    }

    /**
     * Confirmation alert for successfully adding an appointment.
     *
     * @param confirm confirm
     */
    public void confirmation(int confirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (confirm) {

            // No Appointments in the next 15 minutes alert
            case 1:
                alert.setTitle(langBundle.getString("Confirm"));
                alert.setContentText(langBundle.getString("successfullAppAdd"));
                alert.showAndWait();
                break;

        }
    }
}