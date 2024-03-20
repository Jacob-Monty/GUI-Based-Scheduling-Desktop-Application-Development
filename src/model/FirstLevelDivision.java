package model;

import java.time.LocalDateTime;

/**
 * Class for First Level Division
 *
 * @ author Jacob Montoya
 */
public class FirstLevelDivision {

    private int divisionId;
    private String divisionName;
    private int countryID;

    /**
     * Constructor for First Level Division
     *
     * @param divisionId division ID
     * @param divisionName division Name
     */
    public FirstLevelDivision (int divisionId, String divisionName){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    /**
     * Constructor for First Level Division
     *
     * @param divisionId division ID
     * @param divisionName division Name
     * @param countryID country ID
     * @param createDate created Date
     * @param createdBy created By
     * @param lastUpdated last Updated
     * @param LastUpdatedBy last updated by
     */
    public FirstLevelDivision (int divisionId, String divisionName, int countryID, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String LastUpdatedBy){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Getter for division id
     *
     * @return division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for division Id
     *
     * @param divisionId divison ID from Database
     */
    public void setDivisionId (int divisionId){
        this.divisionId = divisionId;
    }

    /**
     * Getter for division Name.
     *
     * @return divisonName
     */
    public String getDivisionName () {
        return divisionName;
    }

    /**
     * Setter for division name
     *
     * @param divisionName divions Name from database
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Getter for country ID
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for country ID
     *
     * @param countryID country ID from database
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Override to show the divsion Name as string.
     *
     * @return divisionName
     */
    @Override
    public String toString() {
        return (divisionName);
    }


}
