package model;

import DAO.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Model class for Appointment
 *
 * @author Jacob Montoya
 */

public class Appointment {

    private int appointmentTypeTotal;
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStartDate;
    private LocalDateTime appointmentEndDate;
    private int appointmentCustomerID;
    private int appointmentUserID;
    private int appointmentContactID;
    private String appointmentContactName;

    /**
     * Constructor for appointment to get all appointment information.
     *
     * @param appointmentID appointment ID
     * @param appointmentTitle appointment Title
     * @param appointmentDescription appointment Description
     * @param appointmentLocation appointment Location
     * @param appointmentType appointment Type
     * @param appointmentStartDate appointment Start Date
     * @param appointmentEndDate appointment End Date
     * @param appointmentCustomerID appointment Customer ID
     * @param appointmentUserID appointment User ID
     * @param appointmentContactID appointment Contact ID
     */

    public Appointment (int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                        LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDate, int appointmentCustomerID, int appointmentUserID, int appointmentContactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;


    }

    /**
     * Constructor for appointment table by contact.
     *
     * @param appointmentID appointment ID
     * @param appointmentTitle appointment Title
     * @param appointmentDescription appointment Description
     * @param appointmentLocation appointment Location
     * @param appointmentType appointment Type
     * @param appointmentStartDate appointment Start Date
     * @param appointmentEndDate appointment End Date
     * @param appointmentContactName appointment Contact Name
     * @param appointmentCustomerID appointment Customer ID
     * @param appointmentUserID appointment User ID
     * @param appointmentContactID appointment COntact ID
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStartDate,
                       LocalDateTime appointmentEndDate, String appointmentContactName, int appointmentCustomerID, int appointmentUserID, int appointmentContactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentContactName = appointmentContactName;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;

    }

    /**
     * Constructor for report to show Type and total of appointments.
     *
     * @param appointmentType appointment Type
     * @param appointmentTypeTotal appointment Total by Type
     */
    public Appointment (String appointmentType, int appointmentTypeTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTypeTotal = appointmentTypeTotal;
    }

    /**
     * Constructor foor Appointments to get information for Report by contact.
     *
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStartDate
     * @param appointmentEndDate
     * @param appointmentCustomerID
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStartDate,
                       LocalDateTime appointmentEndDate, int appointmentCustomerID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentCustomerID = appointmentCustomerID;

    }




    /**
     * Getter for appoint ID column Database
     * @return appointmentID
     */
    public int getAppointmentID(){
        return appointmentID;
    }

    /**
     * Setter for appointment ID column for Database
     * @param appointmentID appointment ID
     */
     public void setAppointmentID(int appointmentID) {

         this.appointmentID = appointmentID;
    }

    /**
     * Getter for appointment Title Column from Database
     * @return appointmentTitle
     */
    public String getAppointmentTitle () {
         return appointmentTitle;
    }

    /**
     * Setter for appointment Title
     * @param appointmentTitle appointment Title
     *
     */
    public void setAppointmentTitle (String appointmentTitle) {
         this.appointmentTitle = appointmentTitle;
    }

    /**
     *  Getter for the appointment description
     * @return appointmentDescription
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Setter for appointment Description
     * @param appointmentDescription appointment Description
     */
    public void setAppointmentDescription (String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;

    }

    /**
     * Getter for Appointment Location
     * @return appointmentLocation appointemnt Location
     */
    public String getAppointmentLocation () {
        return appointmentLocation;

    }

    /**
     * Setter for AppointmentLocation
     * @param appointmentLocation appointment Location
     */
    public void setAppointmentLocation (String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType () {
        return appointmentType;
    }

    /**
     * setter for Appointment Type
     *
     * @param appointmentType appointment Type
     */

    public void setAppointmentType (String appointmentType){

        this.appointmentType = appointmentType;
    }

    /**
     * Getter for appointment Total by type.
     *
     * @return appointment Type Total.
     */
    public int getAppointmentTypeTotal() {
        return appointmentTypeTotal;
    }

    /**
     * Setter for appointment Total By Type.
     *
     * @param appointmentTypeTotal appointment Type Total
     */
    public void setAppointmentTypeTotal(int appointmentTypeTotal) {
        this.appointmentTypeTotal = appointmentTypeTotal;
    }

    /**
     * getter for appointment Start date
     *
     * @return appointmentStartDate
     */
    public LocalDateTime getAppointmentStartDate () {
        return appointmentStartDate;
    }

    /**
     * Setter for appointment Start Date
     *
     * @param appointmentStartDate appointment Start Date
     */
    public void setAppointmentStartDate (LocalDateTime appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    /**
     * Getter for appointment End Date
     *
     * @return appointmentEndDate
     */
    public LocalDateTime getAppointmentEndDate () {
        return appointmentEndDate;
    }

    /**
     * Setter for appointment End Date
     *
     * @param appointmentEndDate appointment End Date
     */
    public void setAppointmentEndDate (LocalDateTime appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    /**
     * Getter for appointment Customer ID
     *
     * @return appointmentCustomerID
     */
    public int getAppointmentCustomerID () {

        return appointmentCustomerID;
    }

    /**
     * Setter for appointment Customer ID
     *
     * @param appointmentCustomerID appointment Customer ID
     */
    public void setAppointmentCustomerID (int appointmentCustomerID) {
        this.appointmentCustomerID = appointmentCustomerID;
    }

    /**
     * Getter for appointment user ID
     *
     * @return appointmentUserID
     */
    public int getAppointmentUserID () {

        return appointmentUserID;
    }

    /**
     * Setter for appointment user ID
     *
     * @param appointmentUserID user ID for appointment
     */
    public void setAppointmentUserID (int appointmentUserID) {

        this.appointmentUserID = appointmentUserID;
    }

    /**
     * Getter for contact ID
     *
     * @return appointment ContactID
     */
    public int getAppointmentContactID () {

        return appointmentContactID;
    }

    /**
     * Setter for contact ID
     *
     * @param appointmentContactID appointment Contact ID
     */
    public void setAppointmentContactId (int appointmentContactID) {

        this.appointmentContactID = appointmentContactID;
    }

    /**
     * Getter for Contact Name
     *
     * @return appointmentContactName
     */
    public String getAppointmentContactName() {
        return appointmentContactName;
    }

    /**
     * Setter for contact name
     *
     */
    public void setAppointmentContactName() {
        this.appointmentContactName = appointmentContactName;
    }

    /**
     *
     * Checking for any appointment overlaps with existing appointments. Will display alerts if any appointments overlap.
     *
     * @param customerID customer ID
     * @param appointmentStartDate appointment Start Date
     * @param appointmentEndDate appointment End Date
     * @return true or false
     */
    public static boolean overlappingCheck(int customerID, LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDate) {
        ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointmentList();
        LocalDateTime checkAppointmentStart;
        LocalDateTime checkAppointmentEnd;
        for (Appointment a : appointmentList) {
            checkAppointmentStart = a.getAppointmentStartDate();
            checkAppointmentEnd = a.getAppointmentEndDate();
            if (customerID != a.getAppointmentCustomerID()) {
                // Checks to see if the appointment start date and end date are the same with other appointments
                if (checkAppointmentStart.isEqual(appointmentStartDate) || checkAppointmentEnd.isEqual(appointmentEndDate)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setContentText("Appointment cannot start or end with an existing appointment");
                    alert.showAndWait();
                    return true;
                    //Checks to see if the start date is after other appointments and end date is before other appointments.
                } else if (appointmentStartDate.isAfter(checkAppointmentStart) && (appointmentStartDate.isBefore(checkAppointmentEnd))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setContentText("Appointment can not start during an existing appointment in the database");
                    alert.showAndWait();
                    return true;
                    // Checks to see if the new appointment is ending during an existing appointment.
                } else if (appointmentEndDate.isAfter(checkAppointmentStart) && appointmentEndDate.isBefore(checkAppointmentEnd)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setContentText("Appointment can not end with an existing appointment that is in the database");
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the appointment is within operating business hours.
     *
     * @param appointmentStartDate appointment Start Date
     * @param appointmentEndDate appointment End Date
     * @return true or false
     */
    public static boolean businessHour(LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDate) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId eastZone = ZoneId.of("America/New_York");

        LocalDateTime appStartEST = appointmentStartDate.atZone(localZone).withZoneSameInstant(eastZone).toLocalDateTime();
        LocalDateTime appEndEST = appointmentEndDate.atZone(localZone).withZoneSameInstant(eastZone).toLocalDateTime();

        LocalDateTime businessStartEST = appStartEST.withHour(8).withMinute(0);
        LocalDateTime businessEndEST = appEndEST.withHour(22).withMinute(0);

        if (appStartEST.isBefore(businessStartEST) || appEndEST.isAfter(businessEndEST)) {
            LocalTime localStart = Appointment.localStartingTime();
            LocalTime localEnd = Appointment.localEndingTime();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("Not within Business Hours");
            alert.setContentText(String.format("Appointment is set outside of Business Hours \n" + "Please set appointment between" + localStart.format(DateTimeFormatter.ofPattern("hh:mm")) + " - "
                    + localEnd.format(DateTimeFormatter.ofPattern("hh;mm")) + "PM local time."));
            alert.getDialogPane().setMinHeight(275);
            alert.getDialogPane().setMinWidth(425);
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads the local time for appointments.
     *
     * @return businessStartingLocal
     */
    public static LocalTime localStartingTime() {
        LocalTime openBusinessTime = LocalTime.of(8,0);
        ZoneId easternZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        LocalDateTime businessEastern = LocalDateTime.of(LocalDate.now(), openBusinessTime);
        LocalDateTime businessLocal = businessEastern.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();
        LocalTime businessStartingLocal = businessLocal.toLocalTime();

        return businessStartingLocal;
    }

    /**
     * Loads local time for appointments ending
     *
     * @return businessEndingLocal
     */
    public static LocalTime localEndingTime() {
        LocalTime closingBusinessTime = LocalTime.of(22,0);
        ZoneId easternZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        LocalDateTime businessEndDT = LocalDateTime.of(LocalDate.now(), closingBusinessTime);
        LocalDateTime businessLocalDT = businessEndDT.atZone(easternZone).withZoneSameInstant(localZone).toLocalDateTime();
        LocalTime businessEndingLocal = businessLocalDT.toLocalTime();

        return businessEndingLocal;
    }

    /**
     * Gets times to display in combo box every 30 minutes.
     *
     * @return appointmentTimeList
     */
    public static ObservableList<LocalTime> getTimes() {
        ObservableList<LocalTime> appointmentTimeList = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(1,00);
        LocalTime end = LocalTime.MIDNIGHT.minusHours(1);

        while (start.isBefore(end.plusSeconds(2))) {
            appointmentTimeList.add(start);
            start = start.plusMinutes(30);

        }
        return appointmentTimeList;
    }

    /**
     * Sends user back to main Scheduling screen.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     */
    public static void backToScheduleScreen (ActionEvent actionEvent) throws IOException {
        new FXMLLoader();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Appointment.class.getResource("../view/ScheduleScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}
