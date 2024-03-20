package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Model Class for Customer
 *
 * @author Jacob Montoya
 */
public class Customer {
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int customerDivisionID;
    private int customerCountryID;
    private String customerDivisionName;
    private String customerCountryName;
    private int customerID;

    static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Constructor for Customer to get values for Customer Table View from Database.
     *
     * @param customerName customer name
     * @param customerAddress customer Address
     * @param customerPostal customer Postal Code
     * @param customerPhone customer Phone number
     * @param createdBy created by user
     * @param lastUpdatedBy last updated by
     * @param customerDivisionId customer Division ID
     * @param customerDivisionName customer division Name
     * @param customerCountryId customer Country ID
     * @param customerCountryName customer Country name
     * @param customerId Customer ID
     */

    public Customer(String customerName, String customerAddress, String customerPostal, String customerPhone, String createdBy, String lastUpdatedBy, int customerDivisionId, String customerDivisionName, int customerCountryId, String customerCountryName, int customerId) {
        this.customerID = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerDivisionID = customerDivisionId;
        this.customerDivisionName = customerDivisionName;
        this.customerCountryID = customerCountryId;
        this.customerCountryName = customerCountryName;
        this.customerPhone = customerPhone;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public Customer(String customerName, String customerAddress, String customerPostal, String customerPhone, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerDivisionId, String customerDivisionName, int customerCountryId, String customerCountryName, int customerId) {
        this.customerID = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerDivisionID = customerDivisionId;
        this.customerDivisionName = customerDivisionName;
        this.customerCountryID = customerCountryId;
        this.customerCountryName = customerCountryName;
        this.customerPhone = customerPhone;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }


    /**
     *Updates Customer Information
     *
     * @param Index Updates Customer Information.
     * @param customer customer
     */
    public static void updateCustomer(int Index, Customer customer){
        customerList.set(Index, customer);
    }

    /**
     * Getter for customer Division Name
     *
     * @return customerDivsionName
     */
    public String getCustomerDivisionName() {
        return customerDivisionName;
    }

    /**
     * Geter for customer country name.
     *
     * @return customerCountryName
     */
    public String getCustomerCountryName() {
        return customerCountryName;
    }

    /**
     * Getter for Customer ID
     *
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for customer ID
     *
     * @param customerID customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for  customer Name
     *
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for customer Name
     *
     * @param customerName customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for customer Address
     *
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter for customer Address
     *
     * @param customerAddress customer Address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter for customer Postal Code
     *
     * @return customerPostal
     */
    public String getCustomerPostalCode(){
        return customerPostal;
    }

    /**
     * Setter for Customer Postal Code
     *
     * @param customerPostal customer postal
     */
    public void setCustomerPostalCode(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    /**
     * Getter for customer Division ID
     *
     * @return customerDivisionID
     */
    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    /**
     * Setter for customer
     *
     * @param customerDivisionID
     */
    public void setCustomerDivision(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }

    /**
     * Getter for customer country ID
     *
     * @return customerCountryID
     */
    public int getCustomerCountryID() {
        return customerCountryID;
    }

    /**
     * Setter for Customer Country ID
     *
     * @param customerCountryID customer Country ID
     */
    public void setCustomerCountryId(int customerCountryID) {
        this.customerCountryID = customerCountryID;
    }

    /**
     * Constructor for Customer id and name.
     *
     * @param customerID customer Id from database
     * @param customerName customer Name from database
     */
    public Customer(int customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
    }

    /**
     * Getter for customer phone number
     *
     * @return customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Setter for customer Phone Number
     *
     * @param customerPhone customer phone nUmber
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Override that takes the customer ID and changes it to the customer Name.
     *
     * @return customerName
     */
    @Override
    public String toString() {
        return "#" + Integer.toString(customerID) + " - " + customerName;
    }


}
