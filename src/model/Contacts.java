package model;

/**
 * Model class for contacts
 *
 * @author Jacob Montoya
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Overloaded constructor for Contact
     *
     * @param contactId    contact id
     * @param contactName  contact name
     * @param contactEmail contact email
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;

    }

    /**
     * Getter for contact id
     *
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }


    /**
     * Setter for contact id
     *
     * @param contactId contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for contact name
     *
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for contact name
     *
     * @param contactName contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for contact email
     *
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter for contact email
     *
     * @param contactEmail contact email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Override to display contactName as String
     *
     * @return contactName
     */
    @Override
    public String toString() {
        return (contactName);
    }
}



