package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointment Database Access class
 *
 * @author Jacob Montoya
 *
 */
public class AppointmentDAO {

    /**
     * Retrieve all appointments from database.
     *
     * @return return
     */
    public static ObservableList<Appointment> getAllAppointmentList () {
        ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * From appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartDate = rs.getTimestamp ("Start").toLocalDateTime();
                LocalDateTime appointmentEndDate = rs.getTimestamp ("End").toLocalDateTime();
                String appointmentContactName = rs.getString("Contact_Name"); /** Has Contact Name **/
                int appointmentCustomerID = rs.getInt("Customer_ID");
                int appointmentUserID = rs.getInt("User_ID");
                int appointmentContactID = rs.getInt("Contact_ID");


                Appointment n = new Appointment (appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate,
                        appointmentEndDate, appointmentContactName, appointmentCustomerID, appointmentUserID, appointmentContactID);
                allAppointmentList.add(n);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allAppointmentList;
    }

    /**
     * Updates database with new info based on user input.
     *
     * @param appointmentID appointment ID
     * @param appointmentTitle appointment Title
     * @param appointmentDescription appointment Description
     * @param appointmentLocation appointment Location
     * @param appointmentType appointment Type
     * @param appointmentStartDate appointment Start Time and Date
     * @param appointmentEndDate appointment End Time and Date
     * @param appointmentCustomerID appointment Customer ID
     * @param appointmentUserID appointment User ID
     * @param appointmentContactID appointment Contact ID
     */
    public static void updateAppointment (int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStartDate,
                                          LocalDateTime appointmentEndDate, int appointmentCustomerID, int appointmentUserID, int appointmentContactID) {
        try{
            String sqlStatement = "Update appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppointment = JDBC.connection.prepareStatement(sqlStatement);

            updateAppointment.setString(1, appointmentTitle);
            updateAppointment.setString(2, appointmentDescription);
            updateAppointment.setString(3, appointmentLocation);
            updateAppointment.setString(4, appointmentType);
            updateAppointment.setTimestamp(5, Timestamp.valueOf(appointmentStartDate));
            updateAppointment.setTimestamp(6,Timestamp.valueOf(appointmentEndDate));
            updateAppointment.setInt(7, appointmentCustomerID);
            updateAppointment.setInt(8, appointmentUserID);
            updateAppointment.setInt(9,appointmentContactID);
            updateAppointment.setInt(10,appointmentID);

            updateAppointment.execute();

        } catch (SQLException e){
            throw new RuntimeException(e);

        }

    }

    /**
     * Query to add appointment to database
     *
     * @param appointmentTitle appointment Title
     * @param appointmentDescription appointment description
     * @param appointmentLocation appointment location
     * @param appointmentType appointment type
     * @param appointmentStartDate appointment start date and time
     * @param appointmentEndDate appointment end date and time
     * @param appointmentCustomerID appointment customer ID
     * @param appointmentUserID appointment user ID
     * @param appointmentContactID appointment contact ID
     * @throws SQLException SQLException
     */
    public static void addAppointment (String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStartDate,
                                          LocalDateTime appointmentEndDate, int appointmentCustomerID, int appointmentUserID, int appointmentContactID) throws SQLException {
        try {
            String sqlStatement = "Insert INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement insertAppointment = JDBC.connection.prepareStatement(sqlStatement);

            insertAppointment.setString(1, appointmentTitle);
            insertAppointment.setString(2, appointmentDescription);
            insertAppointment.setString(3, appointmentLocation);
            insertAppointment.setString(4, appointmentType);
            insertAppointment.setTimestamp(5, Timestamp.valueOf(appointmentStartDate));
            insertAppointment.setTimestamp(6, Timestamp.valueOf(appointmentEndDate));
            insertAppointment.setInt(7, appointmentCustomerID);
            insertAppointment.setInt(8, appointmentUserID);
            insertAppointment.setInt(9, appointmentContactID);
            insertAppointment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Query to delete appointment from database
     *
     * @param appointmentID apppointment ID
     */
    public static void deleteAppointment (int appointmentID){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteApp = JDBC.connection.prepareStatement(sql);
            deleteApp.setInt(1, appointmentID);
            deleteApp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query to get all appointments from the current week.
     *
     * @return weeklyList
     */
    public static ObservableList<Appointment> getWeeklyAppointments() {
        ObservableList<Appointment> weeklyList = FXCollections.observableArrayList();
        try{
            String sqlStatement = "SELECT * FROM appointments INNER JOIN contacts on appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartDate = rs.getTimestamp ("Start").toLocalDateTime();
                LocalDateTime appointmentEndDate = rs.getTimestamp ("End").toLocalDateTime();
                String appointmentContactName = rs.getString("Contact_Name");
                int appointmentCustomerID = rs.getInt("Customer_ID");
                int appointmentUserID = rs.getInt("User_ID");
                int appointmentContactID = rs.getInt("Contact_ID");
                Appointment w = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate,
                        appointmentEndDate, appointmentContactName, appointmentCustomerID, appointmentUserID, appointmentContactID);
                weeklyList.add(w);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return weeklyList;
    }

    /**
     * Query to get appointments from current month.
     *
     * @return monthlyList
     */
    public static ObservableList<Appointment> getMonthlyAppointments() {
        ObservableList<Appointment> monthlyTableView = FXCollections.observableArrayList();
        try{
            String sqlStatement = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE MONTH(START) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartDate = rs.getTimestamp ("Start").toLocalDateTime();
                LocalDateTime appointmentEndDate = rs.getTimestamp ("End").toLocalDateTime();
                String appointmentContactName = rs.getString("Contact_Name");
                int appointmentCustomerID = rs.getInt("Customer_ID");
                int appointmentUserID = rs.getInt("User_ID");
                int appointmentContactID = rs.getInt("Contact_ID");
                Appointment monthlyTV = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate,
                        appointmentEndDate, appointmentContactName, appointmentCustomerID, appointmentUserID, appointmentContactID);
                monthlyTableView.add(monthlyTV);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return monthlyTableView;
    }

    /**
     * Query to get appointments by user.
     *
     * @param userID user ID
     * @return return userApps
     */
    public static ObservableList<Appointment> getUserAppointment (int userID) {
        ObservableList<Appointment> userApps = FXCollections.observableArrayList();
        try{
            String sqlStatement = "SELECT * FROM Appointments WHERE User_ID = '" + userID + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartDate = rs.getTimestamp ("Start").toLocalDateTime();
                LocalDateTime appointmentEndDate = rs.getTimestamp ("End").toLocalDateTime();
                int appointmentCustomerID = rs.getInt("Customer_ID");
                int appointmentUserID = rs.getInt("User_ID");
                int appointmentContactID = rs.getInt("Contact_ID");
                Appointment userApp = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate,
                        appointmentEndDate, appointmentCustomerID, appointmentUserID, appointmentContactID);
                userApps.add(userApp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userApps;
    }

    /**
     * Query to get appointments by contact ID.
     *
     * @param contactID contact ID
     * @return contactAppointments
     */
    public static ObservableList<Appointment> getContactAppointments(int contactID) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = '" + contactID + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartDate = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEndDate = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerID = rs.getInt("Customer_ID");
                Appointment contactResults = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCustomerID);
                contactAppointments.add(contactResults);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactAppointments;
    }

    /**
     * Query to retrieve information on appointments by type.
     *
     * @return appointmentTypeList
     */
    public static ObservableList<Appointment> appointmentTypes() {
        ObservableList<Appointment> appointmentTypeList = FXCollections.observableArrayList();
        try{
            String sqlStatement = "SELECT Type, Count(*) AS Total FROM appointments GROUP BY Type";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String appointmentType = rs.getString("Type");
                int appointmentTypeTotal = rs.getInt("Total");
                Appointment appTypes = new Appointment(appointmentType, appointmentTypeTotal);
                appointmentTypeList.add(appTypes);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return appointmentTypeList;
    }

    /**
     * Query to retrieve appointments by month.
     *
     * @return appointmentMonthlyReport
     */
    public static ObservableList<Appointment> appointmentsMonthlyReport() {
        ObservableList<Appointment> appointmentMonthlyReport = FXCollections.observableArrayList();
        try{
            String sqlStatement = "SELECT DISTINCT(MONTHNAME(START)) AS Month, Count(*) AS Total FROM appointments GROUP BY Month";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String appointmentType = rs.getString("Month");
                int appointmentTypeTotal = rs.getInt("Total");
                Appointment results = new Appointment(appointmentType, appointmentTypeTotal);
                appointmentMonthlyReport.add(results);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return appointmentMonthlyReport;
    }


}
