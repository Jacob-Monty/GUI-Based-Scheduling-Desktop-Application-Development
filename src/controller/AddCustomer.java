package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Add customer Class
 *
 * @author Jacob Montoya
 *
 */

public class AddCustomer implements Initializable {

    static ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");

    @FXML
    private TextField customerIDTextBox;
    @FXML
    private TextField customerAddressTB;
    @FXML
    private TextField customerNameTB;
    @FXML
    private TextField phoneCustomerTB;
    @FXML
    private ComboBox<Country> countryCustomerCB;
    @FXML
    private ComboBox<FirstLevelDivision> stateCustomerCB;
    @FXML
    private TextField postalCustomerTB;
    @FXML
    private Button customerSaveButton;
    @FXML
    private Button cancelCustomerButton;


    /**
     * Action when save button is clicked. It will check that all fields are entered correctly and no blank. It will then save information into database.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void pushSaveCustomer(ActionEvent actionEvent) throws IOException, SQLException {

             //Shows alert if customer name is empty or blank
        if (customerNameTB.getText().isEmpty() || customerNameTB.getText().isBlank()) {
            displayAlert(1);
            return;
        }
            //Shows alert if customer address is empty or blank
        if (customerAddressTB.getText().isEmpty() || customerAddressTB.getText().isBlank()) {
            displayAlert(2);
            return;
        }
            //Shows alert if customer phone number is empty or blank
        if (phoneCustomerTB.getText().isEmpty() || phoneCustomerTB.getText().isBlank()) {
            displayAlert(3);
            return;
        }
            //Shows alert if customer postal code is empty or blank
        if (postalCustomerTB.getText().isEmpty() || postalCustomerTB.getText().isBlank()) {
            displayAlert(4);
            return;
        }

        if (countryCustomerCB.getValue() == null) {
            displayAlert(5);
            return;
        }
            //Shows alert if customer country is not selected.
        if (stateCustomerCB.getValue() == null) {
            displayAlert(5);

        } else {
            String customerName = customerNameTB.getText();
            String customerAddress = customerAddressTB.getText();
            String customerPhone = phoneCustomerTB.getText();
            String customerPostal = postalCustomerTB.getText();
            FirstLevelDivision divId = stateCustomerCB.getValue();
            LocalDateTime createdDate = LocalDateTime.now();
            LocalDateTime lastUpdated = LocalDateTime.now();
            int divisionID = divId.getDivisionId();
            String createdBy = "script";
            CustomerDAO.addCustomer(customerName, customerAddress, customerPostal, customerPhone, createdDate, createdBy, lastUpdated, divisionID);
            confirmation(1);
            pushCancelButton(actionEvent);
        }

    }

    /**
     * Action when cancel button clicked. It will send user back to main schedule screen without saving customer information.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void pushCancelButton(ActionEvent actionEvent) throws IOException {
        Appointment.backToScheduleScreen(actionEvent);

    }

    /**
     * Initializes the combo box for country to be filled with countries in database.
     *
     * @param url   url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCustomerCB.setItems(CountryDAO.getAllCountries());

    }

    /**
     * When country is selected from combo box, this will change the choices base off country selected. It will show appropriate divsions in the combo box.
     *
     * @param event event
     */
    public void selectCountryLoad (ActionEvent event) {
        Country c = countryCustomerCB.getValue();
        try {
            stateCustomerCB.setItems(FirstLevelDivisionDAO.showDivision(c.getCountryID()));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays alerts for all errors in this class.
     *
     * @param alertType alertType
     */

    private void displayAlert ( int alertType){

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankCustomer"));
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankAddress"));
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankPhone"));
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankPostal"));
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("blankCountryState"));
                alertError.showAndWait();
                break;

        }

    }

    /**
     * Confirmation alert for successful customer add.
     *
     * @param confirm confirm
     */
    public void confirmation(int confirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (confirm) {

            // No Appointments in the next 15 minutes alert
            case 1:
                alert.setTitle(langBundle.getString("Confirm"));
                alert.setContentText(langBundle.getString("successfullCustomerAdd"));
                alert.showAndWait();
                break;

        }
    }
}
