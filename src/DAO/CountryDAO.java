package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries to retrieve Country information from database.
 *
 * @author Jacob Montoya
 */

public class CountryDAO {

    /**
     * Query to retrieve all country information from database.
     *
     * @return countryList
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement country = JDBC.connection.prepareStatement(sql);
            ResultSet rs = country.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country g = new Country(countryID, countryName);
                countriesList.add(g);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countriesList;
    }

    /**
     * Query returns country name by off of countryID.
     *
     * @param countryID country ID
     * @return r
     */
    public static Country returnCountry (int countryID) {
        try {
            String rc = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(rc);
            ps.setInt(1, countryID);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedCountryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country j = new Country(searchedCountryID, countryName);
            return j;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Query that shows total countries and number of customers per country from the database.
     *
     * @return custCountry
     */
    public static ObservableList<Country> countryTotal() {
        ObservableList<Country> custCountry  = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country, COUNT(customers.Customer_ID) AS Total FROM countries INNER JOIN first_level_divisions ON  countries.Country_ID = first_level_divisions.Country_ID INNER JOIN customers ON customers.Division_ID = first_level_divisions.Division_ID group by countries.Country";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryMonth = rs.getString("Country");
                int countryMonthTotal = rs.getInt("Total");
                Country results = new Country(countryMonth, countryMonthTotal);
                custCountry.add(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custCountry;
    }
}
