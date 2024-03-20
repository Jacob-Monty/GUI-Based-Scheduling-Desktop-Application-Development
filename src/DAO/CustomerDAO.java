package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Queries to retrieve customer information from the SQL database.
 *
 * @author Jacob Montoya
 *
 */
public class CustomerDAO {

    /**
     * Query to retrieve all customer information from database.
     *
     * @return custList
     */
    public static ObservableList<Customer> getAllCustomerList() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Create_Date, customers.Last_Update, customers.Created_By, customers.Last_Updated_By, customers.Postal_Code, customers.Phone, customers.Division_ID, " + "" +
                         "first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers JOIN first_level_divisions ON customers. Division_ID = first_level_divisions.Division_ID JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID ORDER BY customers.Customer_ID ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String createdBy = rs.getString("Created_By");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerDivisionID = rs.getInt("Division_ID");
                String customerDivisionName = rs.getString("Division");
                int customerCountryID = rs.getInt("Country_ID");
                String customerCountryName = rs.getString("Country");
                Customer c = new Customer(customerName, customerAddress, customerPostal, customerPhone, createdBy, lastUpdatedBy, customerDivisionID, customerDivisionName, customerCountryID, customerCountryName, customerID);
                custList.add(c);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return custList;
    }

    /**
     * Query that adds Customer to database that was input by user.
     *
     * @param customerName customer Name
     * @param customerAddress customer Address
     * @param customerPostalCode customer Postal
     * @param customerPhone customer Phone Number
     * @param createdDate create on date
     * @param lastUpdated last time updated
     * @param divisionId divsion ID
     * @throws SQLException handles SQL exceptions
     */
    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addCustom = JDBC.connection.prepareStatement(sql);
        addCustom.setString(1, customerName);
        addCustom.setString(2, customerAddress);
        addCustom.setString(3, customerPostalCode);
        addCustom.setString(4, customerPhone);
        addCustom.setTimestamp(5, Timestamp.valueOf(createdDate));
        addCustom.setString(6, createdBy);
        addCustom.setTimestamp(7, Timestamp.valueOf(lastUpdated));
        addCustom.setInt(8, divisionId);
        addCustom.executeUpdate();
    }

    /**
     * Query that updates Customer information in database that was modified by the user.
     *
     * @param customerID customer ID
     * @param customerName customer Name
     * @param customerAddress customer Address
     * @param customerPostalCode Customer Postal Code
     * @param customerPhone customer Phone Number
     * @param lastUpdatedBy last updated by
     * @param lastUpdate last updated
     * @param customerDivisionID customer Divsion ID
     * @param countryID country ID
     */
    public static void updateCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, Timestamp lastUpdate, String lastUpdatedBy, int customerDivisionID, int countryID) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement updateCustom = JDBC.connection.prepareStatement(sql);
            updateCustom.setString(1, customerName);
            updateCustom.setString(2, customerAddress);
            updateCustom.setString(3, customerPostalCode);
            updateCustom.setString(4, customerPhone);
            updateCustom.setTimestamp(5, Timestamp.valueOf(now()));
            updateCustom.setString(6, lastUpdatedBy);
            updateCustom.setInt(7, customerDivisionID);
            updateCustom.setInt(8, customerID);
            updateCustom.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query that deletes customer from database.
     *
     * @param customerID customer ID
     */
    public static void deleteCustomer (int customerID) {
        try{
            String deleteCust = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement deleteCustom = JDBC.connection.prepareStatement(deleteCust);
            deleteCustom.setInt(1, customerID);
            deleteCustom.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query that returns customer name into list
     *
     * @param customerID customer ID
     * @return l
     * @throws SQLException
     */
    public static Customer returnCustomerList(int customerID) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.execute();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int searchedCustomerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");

            Customer l = new Customer(searchedCustomerID, customerName);
            return l;
        }
        return null;
    }

}
