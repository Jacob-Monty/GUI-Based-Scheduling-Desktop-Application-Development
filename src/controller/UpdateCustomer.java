package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.now;

/**
 * Initializes Update Customer Class
 *
 * @author Jacob Montoya
 *
 */
public class UpdateCustomer  implements Initializable {

    static ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");
    public TextField customerIDTextBox;
    public TextField customerNameTB;
    public TextField customerAddressTB;
    public TextField phoneCustomerTB;
    public TextField postalCustomerTB;
    public Button customerSaveButton;
    public Button cancelCustomerButton;
    public ComboBox<Country> countryCustomerCB;
    public ComboBox<FirstLevelDivision> stateCustomerCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCustomerCB.setItems(CountryDAO.getAllCountries());
    }


    /**
     * Push allows user to save new customer to database and this also displays alerts for any errors or blank fields.
     *
     * @param actionEvent actionEvent
     */
    public void pushSaveCustomer(ActionEvent actionEvent) {
        try {
            int customerId = Integer.parseInt(customerIDTextBox.getText());
            String customerName = customerNameTB.getText();
            if (customerNameTB.getText().isEmpty() || customerNameTB.getText().isBlank()) {
                displayAlert(1);
                return;
            }
            String customerAddress = customerAddressTB.getText();
            if (customerAddressTB.getText().isEmpty() || customerAddressTB.getText().isBlank()) {
                displayAlert(2);
                return;
            }
            String customerPostal = postalCustomerTB.getText();
            if (postalCustomerTB.getText().isEmpty() || postalCustomerTB.getText().isBlank()) {
                displayAlert(4);
                return;
            }
            String customerPhone = phoneCustomerTB.getText();
            if (phoneCustomerTB.getText().isEmpty() || phoneCustomerTB.getText().isBlank()) {
                displayAlert(3);
                return;
            }
            int customerDivisionId = stateCustomerCB.getValue().getDivisionId();
            int countryId = countryCustomerCB.getValue().getCountryID();

            Timestamp lastUpdate = Timestamp.valueOf(now());
            String lastUpdatedBy = "script";
            CustomerDAO.updateCustomer(customerId, customerName, customerAddress, customerPostal, customerPhone, lastUpdate, lastUpdatedBy, customerDivisionId, countryId);

            pushCancelButton(actionEvent);

            confirmation(1);

        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Pushing takes user back to main Schedule Screen.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void pushCancelButton(ActionEvent actionEvent) throws IOException {
        Appointment.backToScheduleScreen(actionEvent);
    }

    /**
     * Pulls customer information from selected customer to auto-populates fields.
     *
     * @param customer customer
     * @throws SQLException SQLException
     */
    public void getCustomerInfo(Customer customer) throws SQLException {
        customerIDTextBox.setText(Integer.toString(customer.getCustomerID()));
        customerNameTB.setText(customer.getCustomerName());
        customerAddressTB.setText(customer.getCustomerAddress());
        phoneCustomerTB.setText(customer.getCustomerPhone());
        postalCustomerTB.setText(customer.getCustomerPostalCode());
        FirstLevelDivision s = FirstLevelDivisionDAO.returnDivisionName(customer.getCustomerDivisionID());
        stateCustomerCB.setValue(s);
        Country c = CountryDAO.returnCountry(customer.getCustomerCountryID());
        countryCustomerCB.setValue(c);
        Country C = countryCustomerCB.getValue();
        stateCustomerCB.setItems(FirstLevelDivisionDAO.showDivision(C.getCountryID()));
    }

    /**
     * Gets country information from database and allows user to choose.
     *
     * @param actionEvent actionEvent
     */
    public void selectCountryLoad(ActionEvent actionEvent) {
        Country f = countryCustomerCB.getValue();
        try {
            stateCustomerCB.setItems(FirstLevelDivisionDAO.showDivision(f.getCountryID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Alerts for all errors in this class.
     *
     * @param alertType
     */
    private void displayAlert ( int alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
                alertError.setContentText(langBundle.getString("blankCountry"));
                alertError.showAndWait();
                break;
        }
    }

    public void confirmation(int confirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (confirm) {

            // No Appointments in the next 15 minutes alert
            case 1:
                alert.setTitle(langBundle.getString("Confirm"));
                alert.setContentText(langBundle.getString("successfullCustomerUpdated"));
                alert.showAndWait();
                break;

        }
    }
}
