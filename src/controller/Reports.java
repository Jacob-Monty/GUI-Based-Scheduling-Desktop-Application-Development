package controller;

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.CountryDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Reports class for project.
 *
 * @author Jacob Montoya
 */

public class Reports implements Initializable {


    public TableColumn<Appointment, Integer> appointID;
    public TableColumn<Appointment, String> appointTitle;
    public TableColumn<Appointment, String> appointType;
    public TableColumn<Appointment, String> appointDescription;
    public TableColumn<Appointment, String> appLocation;
    public TableColumn<Appointment, Timestamp> appointStart;
    public TableColumn<Appointment, Timestamp> appointEnd;
    public TableColumn<Appointment, Integer> appointCustomerID;
    public TableColumn<Appointment, String> appointmentMonth;
    public TableColumn<Appointment, Integer> appointmentMonthTotal;
    public TableColumn<Appointment, String> appointmentType;
    public TableColumn<Appointment, Integer> appointmentTotal;
    public TableColumn<Country, String> appointmentCountry;
    public TableColumn<Country, Integer> totalCountry;
    public ComboBox<Contacts> selectContactCB;
    public Button backButton;
    public Button logoutButton;
    public TableView<Appointment> appointmentTypeTableView;
    public TableView <Country> countryTableView;
    public TableView contactScheduleTableView;
    public TableView<Appointment> appointmentMonthTableView;

    ObservableList<Contacts> contactList = ContactsDAO.getContacts();

    /**
     * Initializes all tables with information from different classes.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectContactCB.setItems(contactList);
        appointID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointCustomerID.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
        appointStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        appointEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        appointType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        contactScheduleTableView.refresh();

        // Report to show all appointments by month and totals for each month.
        appointmentMonthTableView.setItems(AppointmentDAO.appointmentsMonthlyReport());
        appointmentMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentMonthTotal.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeTotal"));

        // Report to show all appointments by type and totals for each type.
        appointmentTypeTableView.setItems(AppointmentDAO.appointmentTypes());
        appointmentTotal.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeTotal"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));

        // Report to show all appointments by country and total for each country.
        countryTableView.setItems(CountryDAO.countryTotal());
        totalCountry.setCellValueFactory(new PropertyValueFactory<>("countryTotal"));
        appointmentCountry.setCellValueFactory(new PropertyValueFactory<>("countryBy"));
    }

    /**
     * Allows user to choose a contact currently in the database
     *
     * @param actionEvent actionEvent
     * @throws SQLException SQLException
     */
    public void selectContact(ActionEvent actionEvent) throws SQLException {
        // Getting contact name and converting it to contact ID to obtain associated appointments
        String contactName = String.valueOf(selectContactCB.getValue());
        int contactId = ContactsDAO.returnContactId(contactName);
        if (AppointmentDAO.getContactAppointments(contactId).isEmpty()) {
            contactScheduleTableView.setPlaceholder(new Label(contactName + " has no appointments."));
            contactScheduleTableView.refresh();
            for (int i = 0; i < contactScheduleTableView.getItems().size(); i++) {
                contactScheduleTableView.getItems().clear();
            }
        } else {
            contactScheduleTableView.setItems(AppointmentDAO.getContactAppointments(contactId));
        }
    }


    /**
     * This button allows user to return to main Schedule, Appointment Screen
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void pushBackToScheduleScreen(ActionEvent actionEvent) throws IOException {
        Appointment.backToScheduleScreen(actionEvent);
    }

    /**
     * This button closes program and database connection.
     *
     * @param actionEvent actionEvent
     */
    public void pushLogout (ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
