package DAO;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * First Level Division Database Access Class
 *
 * @author Jacob Montoya
 */

public class FirstLevelDivisionDAO {

    /**
     * Query to retrieve all the division IDs from the database.
     *
     * @return firstDivisionList
     */
    public static ObservableList<FirstLevelDivision> getAllDivisionID() {
        ObservableList<FirstLevelDivision> firstDivisionList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement division = JDBC.connection.prepareStatement(sql);
            ResultSet rs = division.executeQuery();

         while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            LocalDateTime createDate = Create_Date.toLocalDateTime();
            Timestamp Last_Updated = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdated = Last_Updated.toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            FirstLevelDivision d = new FirstLevelDivision(divisionId, divisionName, countryID, createDate, createdBy, lastUpdated, lastUpdatedBy);
            firstDivisionList.add(d);
         }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return firstDivisionList;
    }


    /**
     * Query to show divisions.
     *
     * @param countryID country ID
     * @return optionsDivisionCountry
     * @throws SQLException handles SQL Exceptions
     */
    public static ObservableList<FirstLevelDivision> showDivision(int countryID) throws SQLException {
        ObservableList<FirstLevelDivision> optionsDivisionCountry = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = " + countryID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            countryID = rs.getInt("Country_ID");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = Create_Date.toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp Last_Updated = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdated = Last_Updated.toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            FirstLevelDivision a = new FirstLevelDivision(divisionId, divisionName, countryID, createDate, createdBy, lastUpdated, lastUpdatedBy);
            optionsDivisionCountry.add(a);
        }
        return optionsDivisionCountry;
    }

    /**
     * Query that returns division name.
     *
     * @param divisionId division ID
     * @return s
     */
    public static FirstLevelDivision returnDivisionName (int divisionId) {
        try {
            String sqlStatement = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ps.setInt(1, divisionId);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchDivisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            FirstLevelDivision f = new FirstLevelDivision(searchDivisionId, divisionName);
            return f;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
