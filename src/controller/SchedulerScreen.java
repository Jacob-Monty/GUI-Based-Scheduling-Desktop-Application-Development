package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import javafx.fxml.FXML;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * SchedulerScreen Class
 *
 * @author Jacob Montoya
 */

public class SchedulerScreen implements Initializable {

    public RadioButton allAppointmentsRadioButton;
    public ToggleGroup appointmentGroup;
    public RadioButton weeklyTableViewButton;
    public RadioButton appointmentMonthlyRadio;
    public Button addAppointmentButton;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;
    public Button addCustomerButton;
    public Button deleteCustomerButton;
    public Button modifyCustomerButton;
    public Button logoutButton;
    public Button reportsButton;

    /**
     * Tableview and all columns for appointments
     *
     */
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private ToggleGroup appointmentView;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML
    public TableColumn<Appointment, String> appointmentTitleCol;
    @FXML
    public TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML
    public TableColumn<Appointment, String> appointmentLocationCol;
    @FXML
    public TableColumn<Appointment, Integer> appointmentContactCol;
    @FXML
    public TableColumn<Appointment, String> appointmentTypeCol;
    @FXML
    public TableColumn<Appointment, Timestamp> appointmentStartCol;
    @FXML
    public TableColumn<Appointment, Timestamp> appointmentEndCol;
    @FXML
    public TableColumn<Appointment, Integer> appointmentCustIDCol;
    @FXML
    public TableColumn<Appointment, Integer> appointmentUserIDCol;

    /**
     *Customer Tableview
     *
     */
    @FXML
    private TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerIDCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, Integer> stateCol;
    public TableColumn<Customer, String> postalCol;

    ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");
    ObservableList<Appointment> AppointmentList = AppointmentDAO.getAllAppointmentList();
    ObservableList<Customer> CustomerList = CustomerDAO.getAllCustomerList();


    /**
     * Initializes tables with all information from database, such as customer and appointment data.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Appointment Tableview and Column information
        appointmentTableView.setItems(AppointmentDAO.getAllAppointmentList());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDate"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDate"));
        appointmentCustIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentUserID"));
        //Customer Tableview and Column Information
        customerTableView.setItems(CustomerDAO.getAllCustomerList());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));


    }


    /**
     * Radio button that displays all appointments currently in database
     *
     * @param actionEvent actionEvent
     */
    public void pushAllAppointmentsTableview(ActionEvent actionEvent) {
        appointmentTableView.setItems(AppointmentDAO.getAllAppointmentList());
    }

    /**
     * Radio button that displays appointments that are currently for the week.
     *
     * @param actionEvent actionEvent
     */
    public void pushWeeklyTableView(ActionEvent actionEvent) {
        appointmentTableView.setItems(AppointmentDAO.getWeeklyAppointments());
    }

    /**
     * Radio button that displays all appoints for current month.
     *
     * @param actionEvent actionEvent
     */
    public void pushMonthlyTableview(ActionEvent actionEvent) {
        appointmentTableView.setItems(AppointmentDAO.getMonthlyAppointments());
    }


    /**
     * Pushing button will take user to add appointment interface.
     *
     * @param event event
     * @throws IOException IOException
     */
    @FXML public void pushAddAppointment (ActionEvent event) throws IOException {
        Parent addAppParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        Scene addAppScene = new Scene(addAppParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAppScene);
        window.show();

    }

    /**
     * Pushing button will take user to update appointment information.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
    @FXML public void pushUpdateApp (ActionEvent actionEvent) throws IOException, SQLException {
        if(appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();
            UpdateAppointment MCController = loader.getController();
            MCController.getAppInfo(appointmentTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            displayAlert(1);
        }
    }

    /**
     * Action will allow user to delete appointment from database while showing errors if no appointment is selected.
     *
     * @param actionEvent actionEvent
     */
    public void pushDeleteApp(ActionEvent actionEvent) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            displayAlert(1);
        } else {
            Alert confirmDelete = new Alert(Alert.AlertType.WARNING);
            confirmDelete.setTitle(langBundle.getString("Alert"));
            confirmDelete.setContentText(langBundle.getString("removalApp"));
            confirmDelete.getButtonTypes().clear();
            confirmDelete.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirmDelete.showAndWait();
            if (confirmDelete.getResult() == ButtonType.OK) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle(langBundle.getString("removalConfirm"));
                confirm.setContentText("Appointment ID " + appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID() + " for " + appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentType() + " has been canceled.");
                confirm.getButtonTypes().clear();
                confirm.getButtonTypes().addAll(ButtonType.OK);
                confirm.showAndWait();

                AppointmentDAO.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID());
                AppointmentList = AppointmentDAO.getAllAppointmentList();
                appointmentTableView.setItems(AppointmentList);
                appointmentTableView.refresh();
            } else if (confirmDelete.getResult() == ButtonType.CANCEL){
                confirmDelete.close();
            }
        }

    }

    /**
     * Action will take user to add customer interface.
     *
     * @param event event
     * @throws IOException IOException
     */
    @FXML public void pushAddCustomer(ActionEvent event) throws IOException{
        Parent addCustomerParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Scene addCustomerScene = new Scene(addCustomerParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addCustomerScene);
        window.show();

    }

    /**
     * Action will allow user to delete customer from database and also deleting connected appointments. It will also display errors if no customer is selected.
     *
     * @param actionEvent actionEvent
     */
    public void pushDeleteCustomer(ActionEvent actionEvent) {
        int count = 0;

        Customer customer =customerTableView.getSelectionModel().getSelectedItem();

        // No customer selected displays alert.
        if (customer == null) {
            displayAlert(3);
            return;
        }
        int selectedCustomer = (customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
        // Counts all appointments connected to the customer that is selected.
        for (Appointment appointment : AppointmentList) {
            int appointmentCustomerID = appointment.getAppointmentCustomerID();
            if (appointmentCustomerID == selectedCustomer) {
                count++;
            }
        }
        // Displays an alert that shows how many appointments are connected to the customer and then a confirmation that they will be deleted.
        if (count > 0) {
            Alert connectedApp = new Alert(Alert.AlertType.WARNING);
            connectedApp.setTitle("Alert");
            connectedApp.setHeaderText("Warning: " + count + " connected appointment(s).");
            connectedApp.setContentText( count + " connected appointment(s) for the selected customer.\n\n" +
                    "Select OK to delete customer and all associated appointment(s).\n\n" +
                    "Select CANCEL to cancel customer delete and return to main screen.");
            connectedApp.getButtonTypes().clear();
            connectedApp.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            connectedApp.getDialogPane().setMinHeight(275);
            connectedApp.getDialogPane().setMinWidth(400);
            connectedApp.showAndWait();
            if (connectedApp.getResult() == ButtonType.OK) {
                for (Appointment appointment : AppointmentList) {
                    if (appointment.getAppointmentCustomerID() == selectedCustomer)
                        AppointmentDAO.deleteAppointment(appointment.getAppointmentID());
                    AppointmentList = AppointmentDAO.getAllAppointmentList();
                    appointmentTableView.setItems(AppointmentList);
                    appointmentTableView.refresh();
                }
                    CustomerDAO.deleteCustomer((customerTableView.getSelectionModel().getSelectedItem()).getCustomerID());
                    CustomerList = CustomerDAO.getAllCustomerList();
                    customerTableView.setItems(CustomerList);
                    customerTableView.refresh();

                    Alert confirmRemoval = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmRemoval.setTitle("Confirmation");
                    confirmRemoval.setContentText("Customer and all connected appointments have been removed.");
                    confirmRemoval.getButtonTypes().clear();
                    confirmRemoval.getButtonTypes().addAll(ButtonType.OK);
                    confirmRemoval.showAndWait();


            } else if (connectedApp.getResult() == ButtonType.CANCEL) {
                connectedApp.close();
            }
        }
        // If there are no connected appointments it will display and alert and confirmation of delete.
        if (count == 0) {
            Alert confirmRemoval = new Alert(Alert.AlertType.WARNING);
            confirmRemoval.setTitle("Alert");
            confirmRemoval.setContentText("Remove selected customer?\n" +
                    "Press OK to remove.\n Cancel to return to screen.");
            confirmRemoval.getButtonTypes().clear();
            confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirmRemoval.showAndWait();
            if (confirmRemoval.getResult() == ButtonType.OK) {
                CustomerDAO.deleteCustomer((customerTableView.getSelectionModel().getSelectedItem()).getCustomerID());

                Alert confirmRemove = new Alert(Alert.AlertType.CONFIRMATION);
                confirmRemove.setTitle("Confirmation");
                confirmRemove.setContentText("Customer has been removed.");
                confirmRemove.getButtonTypes().clear();
                confirmRemove.getButtonTypes().addAll(ButtonType.OK);
                confirmRemove.showAndWait();

                CustomerList = CustomerDAO.getAllCustomerList();
                customerTableView.setItems(CustomerList);
                customerTableView.refresh();
            } else if (confirmRemoval.getResult() == ButtonType.CANCEL) {
                confirmRemoval.close();
            }

        }

    }

    /**
     * Action will take user to update customer information in database.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
    public void pushModifyCustomer (ActionEvent actionEvent) throws IOException, SQLException {
        if(customerTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();
            UpdateCustomer MCController = loader.getController();
            MCController.getCustomerInfo(customerTableView.getSelectionModel().getSelectedItem());
            Stage updateStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            updateStage.setScene(new Scene(scene));
            updateStage.show();
        } else {
            displayAlert(4);
        }
    }

    /**
     * Action to take user to Reports interface.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public void pushReports (ActionEvent actionEvent) throws IOException {
        Parent reportsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Reports.fxml")));
        Scene reportsScene = new Scene(reportsParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(reportsScene);
        window.show();

    }

    /**
     * Action close program and closes database connection.
     *
     * @param actionEvent actionEvent
     */
    public void pushLogout (ActionEvent actionEvent) {
        Stage logoutStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        logoutStage.close();
    }

    /**
     * Displays all alerts for this class.
     *
     * @param alertType alertType
     */
    private void displayAlert(int alertType) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("noAppSelected"));
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle(langBundle.getString("ErrorBlankPassword"));
                alertError.setContentText(langBundle.getString("blankPassword"));
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("Alert"));
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle(langBundle.getString("Alert"));
                alertError.setContentText(langBundle.getString("noCustomer"));
                alertError.showAndWait();

        }
    }








}
