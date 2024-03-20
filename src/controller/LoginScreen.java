package controller;

import DAO.AppointmentDAO;
import DAO.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.UserDAO.*;

/**
 * Login Screen Class
 *
 * @author Jacob Montoya
 */
public class LoginScreen implements Initializable {

    public Button loginButton;
    public Button exitButton;
    public Label usernameLabel;
    public Label passwordLabel;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Label loginLabel;
    public Label locationLabel;
    public Label locationHeader;
    boolean loginSuccessful = false;

    //Converts time and gets 15 min from now to display upcoming appointments
    LocalDateTime currentTime = LocalDateTime.now();
    ZonedDateTime LDTConvert = currentTime.atZone(ZoneId.systemDefault());
    LocalDateTime  currentTimePlus15 = LocalDateTime.now().plusMinutes(15);
    ZonedDateTime LDTUTC = LDTConvert.withZoneSameInstant(ZoneId.of("Etc/UTC"));

    // Language bundle to convert languages
    ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");

    /**
     * This intializes time zones and languages based off of the language selected in the user's system. The program currently supports Englisha and French languages.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        String location = ZoneId.systemDefault().toString();
        locationLabel.setText(location);
        usernameLabel.setText(langBundle.getString("Username"));
        passwordLabel.setText(langBundle.getString("Password"));
        loginButton.setText(langBundle.getString("Login"));
        exitButton.setText(langBundle.getString("Exit"));
        locationHeader.setText(langBundle.getString("location"));


    }

    /**
     * Pressing this button the user is able to log into the Schedule screen to view information. There are alerts in place to show if login information is incorrect or blank.
     *
     * @param actionEvent actionEvent
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
    public void loginButtonAction(ActionEvent actionEvent) throws IOException, SQLException {
        String User_Name = usernameTextField.getText();
        String Password = passwordTextField.getText();


        if (User_Name.isEmpty() || User_Name.isBlank()) {
            displayAlert(1);
            return;
        }
        if (Password.isEmpty() || Password.isBlank()) {
            displayAlert(2);
            loginActivity();
            loginSuccessful = false;
            return;
        }
        if (!usernameValid(User_Name) && !passwordValid(Password)){
            displayAlert(5);
            loginActivity();
            loginSuccessful = false;
            return;
        }
        if (!passwordValid(Password) ) {
            displayAlert(3);
            loginActivity();
            loginSuccessful = false;
            return;
        }
        if (!usernameValid(User_Name)) {
            displayAlert(4);
            loginActivity();
            loginSuccessful = false;

        } else if (userLogin(User_Name, Password)) {
            int userID = getUserID(User_Name);
            ObservableList<Appointment> userApps = AppointmentDAO.getUserAppointment(userID);
            new FXMLLoader();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ScheduleScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            loginSuccessful = true;
            loginActivity();

            // Checks for appointments that are 15 minutes or currently available. Will display an alert if there are no appointments within time range.
            boolean isValid = false;
            for (Appointment appointment : userApps) {
                LocalDateTime startTime = appointment.getAppointmentStartDate();
                if ((startTime.isAfter(currentTime) || startTime.isEqual(currentTimePlus15)) && (startTime.isBefore(currentTimePlus15) || startTime.isEqual(currentTime))) {
                    Alert confirmApp = new Alert(Alert.AlertType.INFORMATION);
                    confirmApp.setTitle("Alert");
                    confirmApp.setContentText("Appointment");
                    confirmApp.setContentText(langBundle.getString("Appointment") + " " + appointment.getAppointmentID() + " " + langBundle.getString("startsAt") + " " + appointment.getAppointmentStartDate());
                    confirmApp.getButtonTypes().clear();
                    confirmApp.getButtonTypes().addAll(ButtonType.OK);
                    confirmApp.showAndWait();
                    isValid = true;
                }

            }

            if (!isValid) {
                displayAlert(6);
            }
        }


    }

    /**
     * Login Activity for users that are logging in.
     *
     * @throws IOException IOException
     */
    public void loginActivity() throws IOException {
        FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm:ss");
        ZoneId localZone = ZoneId.systemDefault();
        if (loginSuccessful) {
            fileWriter.write(usernameTextField.getText() + " login was successful on " + formatter.format(currentTime));
        } else if (!loginSuccessful) {
            fileWriter.write(usernameTextField.getText() + " failed to login on " + formatter.format(currentTime));
        }
        fileWriter.write("\n");
        fileWriter.close();
    }

    interface LogActivity {
        public String getFileName();
    }

    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };


    /**
     * Pushing this will close program and close database
     *
     * @param actionEvent actionEvent
     */
    public void exitProgramAction(ActionEvent actionEvent) {
        JDBC.closeConnection();

        System.exit(0);
    }

    /**
     * Alerts for all errors in this class.
     *
     * @param alertType alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle(langBundle.getString("ErrorBlankUserName"));
                alertError.setContentText(langBundle.getString("blankUsername"));
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle(langBundle.getString("ErrorBlankPassword"));
                alertError.setContentText(langBundle.getString("blankPassword"));
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle(langBundle.getString("ErrorPassword"));
                alertError.setContentText(langBundle.getString("incorrectPassword"));
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle(langBundle.getString("ErrorUserName"));
                alertError.setContentText(langBundle.getString("incorrectUsername"));
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle(langBundle.getString("ErrorUserPassword"));
                alertError.setContentText(langBundle.getString("incorrectUserPassword"));
                alertError.showAndWait();
                break;
            case 6:
                alert.setTitle(langBundle.getString("noAppointment15"));
                alert.setContentText(langBundle.getString("noUpcomingAppointment"));
                alert.showAndWait();
        }
    }
}