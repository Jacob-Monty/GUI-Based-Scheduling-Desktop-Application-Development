package model;

/**
 * Model Class for Country
 *
 * @author Jacob Montoya
 */

public class Country {

    private String countryBy;
    private int countryTotal;
    private String countryName;
    private int countryID;



    /**
     *  Constructor for Country that is used to get the data from CountryDAO.
     *
     * @param countryBy country by
     * @param countryTotal country Total
     */

    public Country(String countryBy, int countryTotal) {
        this.countryBy = countryBy;
        this.countryTotal = countryTotal;

    }

    /**
     * Constructor for country that is used to get all country information from CountryDAO
     *
     * @param countryID country ID
     * @param countryName country Name
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * getter for Country Total for reports
     *
     * @return countryTotal
     */

    public int getCountryTotal() {
        return countryTotal;
    }

    /**
     * getter for Country that will be in reports to show how many appointments there are by country.
     *
     * @return countryBy
     */
    public String getCountryBy() {
        return countryBy;
    }

    /**
     * getter for country ID
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for country ID
     *
     * @param countryID country ID
     */

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Getter for Country Name
     *
     * @return countryName from database
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for Country Name
     *
     * @param countryName countryName from database
     */
    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    /**
     * A method to get strings for the country names in the combo boxes instead of getting other information.
     *
     * @return countryName
     */
    public String toString() {
        return (countryName);
    }
}
