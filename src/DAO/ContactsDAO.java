package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Database Access Class
 *
 * @author Jacob Montoya
 */
public class ContactsDAO {

    /**
     * Query to retrieve all contact information from database.
     *
     * @return contactList
     */
    public static ObservableList<Contacts> getContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
            PreparedStatement contacts = JDBC.connection.prepareStatement(sql);
            ResultSet rs = contacts.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts c = new Contacts(contactID, contactName, contactEmail);
                contactsList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactsList;
    }

    /**
     * Query to retrieve contact information by contact ID.
     *
     * @param contactId contact ID
     * @return s
     */
    public static Contacts returnContactsList(int contactId) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchContactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts s = new Contacts(searchContactID, contactName, contactEmail);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Query to get contact ID.
     *
     * @param contactName contactName
     * @return contactID
     * @throws SQLException handles SQL exception
     */
    public static int returnContactId(String contactName) throws SQLException {
        int contactID = 0;
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            contactID = rs.getInt("Contact_ID");
        }
        return contactID;
    }


}
