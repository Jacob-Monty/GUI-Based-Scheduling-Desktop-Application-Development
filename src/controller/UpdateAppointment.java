package controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contacts;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Update Appointment Class
 *
 * @author Jacob Montoya
 */

public class UpdateAppointment implements Initializable {
    @FXML
    private ChoiceBox<Contacts> contactComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField appointmentIdTextField;
    @FXML
    private
    TextField titleTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox<LocalTime> startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private ChoiceBox<LocalTime> endTime;
    @FXML
    private ChoiceBox<Customer> customerIDComboBox;
    @FXML
    private ChoiceBox<User> userIDComboBox;

    ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");

    /**
     * Initializes combo boxes to show contact, customer, and user lists.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contacts> contactList = ContactsDAO.getContacts();
        contactComboBox.setItems(contactList);

        ObservableList<Customer> customerList = CustomerDAO.getAllCustomerList();
        customerIDComboBox.setItems(customerList);

        ObservableList<User> userList = UserDAO.getAllUsersList();
        userIDComboBox.setItems(userList);


    }

    /**
     * Action takes user back to main Schedule Screen.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void cancelUpdateButton(ActionEvent actionEvent) throws IOException {
        Appointment.backToScheduleScreen(actionEvent);
    }

    /**
     * Displays the selected appointment information for the user to update.
     *
     * @param appointment appointment
     * @throws SQLException SQLException
     */
    public void getAppInfo(Appointment appointment) throws SQLException {
        startTime.setItems(Appointment.getTimes());
        endTime.setItems(Appointment.getTimes());
        appointmentIdTextField.setText(Integer.toString(appointment.getAppointmentID()));
        titleTextField.setText(appointment.getAppointmentTitle());
        descriptionTextField.setText(appointment.getAppointmentDescription());
        locationTextField.setText(appointment.getAppointmentLocation());
        typeTextField.setText(appointment.getAppointmentType());
        startDate.setValue(appointment.getAppointmentStartDate().toLocalDate());
        startTime.setValue(appointment.getAppointmentStartDate().toLocalTime());
        endDate.setValue(appointment.getAppointmentEndDate().toLocalDate());
        endTime.setValue(appointment.getAppointmentEndDate().toLocalTime());
        Contacts g = ContactsDAO.returnContactsList(appointment.getAppointmentContactID());
        contactComboBox.setValue(g);
        Customer f = CustomerDAO.returnCustomerList(appointment.getAppointmentCustomerID());
        customerIDComboBox.setValue(f);
        User h = UserDAO.returnUserId(appointment.getAppointmentUserID());
        userIDComboBox.setValue(h);
    }

    /**
     *  Saves appointment information that was modified. Will display errors if left blank and checks for overlaps.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
    public void saveUpdateApp(ActionEvent actionEvent) throws IOException, SQLException {

        int appointmentID = Integer.parseInt(appointmentIdTextField.getText());
        String appointmentTitle = titleTextField.getText();
        String appointmentDescription = descriptionTextField.getText();
        String appointmentType = typeTextField.getText();
        String appointmentLocation = locationTextField.getText();
        //Displays alert if appointment Title is Empty
        if (appointmentTitle.isBlank() || appointmentTitle.isEmpty()) {
            displayAlert(8);
            return;
        }
        //Displays alert if appointment Type is Empty
        if (appointmentType.isEmpty() || appointmentType.isBlank()) {
            displayAlert(10);
            return;
        }
        //Displays alert if appointment description is Empty
        if (appointmentDescription.isBlank() || appointmentDescription.isEmpty()) {
            displayAlert(9);
            return;
        }
        //Displays alert if appointment Location is Empty
        if (appointmentLocation.isBlank() || appointmentLocation.isEmpty()) {
            displayAlert(11);
            return;
        }
        //Displays alert if appointment start date is Empty
        LocalDate startPick = startDate.getValue();
        if (startPick == null) {
            displayAlert(2);
            return;
        }
        //Displays alert if appointment start time is empty.
        LocalTime start = startTime.getValue();
        if (start == null) {
            displayAlert(3);
            return;
        }
        LocalDateTime appointmentStartDate = LocalDateTime.of(startDate.getValue(), startTime.getValue());
        //Displays alert if end date is not selected
        LocalDate endPicker = endDate.getValue();
        if (endPicker == null) {
            displayAlert(4);
            return;
        }
        //displays alert if appointment end time is not selected.
        LocalTime end = endTime.getValue();
        if (end == null) {
            displayAlert(5);
            return;
        }

        LocalDateTime appointmentEndDate = LocalDateTime.of(endDate.getValue(), endTime.getValue());
        //Displays alert if customer is not selected.
        Customer customer = customerIDComboBox.getValue();
        if (customer == null) {
            displayAlert(6);
            return;
        }

        int appointmentCustomerID = customerIDComboBox.getValue().getCustomerID();
        //Displays alert if the user ID is not selected.
        User user = userIDComboBox.getValue();
        if (user == null) {
            displayAlert(7);
            return;
        }

        int appointmentUserID = userIDComboBox.getValue().getUserID();
        //Displays alert if the contact is not selected.
        Contacts contact = contactComboBox.getValue();
        if (contact == null) {
            displayAlert(1);
            return;
        }

        int appointmentContact = contact.getContactId();
        // Checks to see if appointment is within the business hours and then displays alert.
        if (Appointment.businessHour(appointmentStartDate, appointmentEndDate)) {
            return;
        // Checks to see if the appointment is overlapping another appointment in the database.
        } else if (Appointment.overlappingCheck(appointmentCustomerID, appointmentStartDate, appointmentEndDate)) {
            return;
        } else {
            AppointmentDAO.updateAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCustomerID, appointmentUserID, appointmentContact);
            Appointment.backToScheduleScreen(actionEvent);
            confirmation(1);
        }
    }


    /**
     * Displays all alerts for errors in this class.
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
     * Confirmation for a successful save of an updated customer.
     *
     * @param confirm confirm
     */
    public void confirmation(int confirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (confirm) {

            // No Appointments in the next 15 minutes alert
            case 1:
                alert.setTitle(langBundle.getString("Confirm"));
                alert.setContentText(langBundle.getString("successfulAppUpdate"));
                alert.showAndWait();
                break;

        }
    }


}

