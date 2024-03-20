package model;


import java.time.LocalDateTime;

/**
 * Model Class for User
 *
 * @author Jacob Montoya
 */
public class User {


    private String userName;
    private int userID;
    public String password;
    public LocalDateTime createdDate;
    public String createdBy;
    public LocalDateTime lastTimeUpdated;
    public String lastUpdatedBy;




    /**
     * Constructor create for user object
     *
     * @param userID  - userID
     * @param userName - username
     * @param password - password
     */


    /**
     * UserName Constructor
     *
     * @param userName user Name
     */
    public User(String userName){
       this.userName = userName;

    }

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;

    }


    /**
     * Constructor for user
     *
     * @param userID user ID
     * @param userName user Name
     */
    public User(int userID, String userName){
        this.userID = userID;
        this.userName = userName;

    }

    public User(int userID, String userName, String password, LocalDateTime createdDate, String createdBy, LocalDateTime lastTimeUpdated, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastTimeUpdated = lastTimeUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }


    /**
     * Getter for user ID
     *
     * @return userID
     */
    public int getUserID () {
        return userID;

    }


    /**
     * Getter for user name
     *
     * @return userName
     */
    public String getUserName() {
        return userName;

    }

    /**
     * setter for user name
     *
     * @param userName user name
     */
    public void setUserName (String userName) {
        this.userName = userName;

    }

    /**
     * Displays userID as username
     *
     * @return userName
     */
    public String toString() {
        return "#" + Integer.toString(userID) + " - " + userName;
    }

    /**
     * Getter for created date
     *
     * @return created date
     */
    public LocalDateTime getCreatedDate () {
        return createdDate;
    }

    /**
     * Setter for created date
     *
     * @param createdDate sets created date for a new user
     */
    public void setCreatedDate (LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter for created by
     *
     * @return createdBy
     */
    public String getCreatedBy () {
        return createdBy;
    }

    /**
     * setter for created by
     *
     * @param createdBy sets who created the username into database
     */
    public void  setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * getter for last time updated
     *
     * @return lastTimeUpdated
     */
    public LocalDateTime getLastTimeUpdated () {
        return lastTimeUpdated;
    }

    /**
     * Setter for last time updated
     *
     * @param lastTimeUpdated when the user was last time updated
     */
    public void setLastTimeUpdated (LocalDateTime lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    /**
     * getter for last updated by
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy () {
        return lastUpdatedBy;
    }

    /**
     * Setter for last updated by
     *
     * @param lastUpdatedBy sets who updated the user recently.
     */
    public void setLastUpdatedBy (String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
