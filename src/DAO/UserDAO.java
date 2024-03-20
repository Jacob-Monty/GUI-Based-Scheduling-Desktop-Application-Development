package DAO;

import DAO.JDBC;
import com.mysql.cj.jdbc.exceptions.SQLError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

/**
 * User database access class
 *
 * @author Jacob Montoya
 */
public class UserDAO {
    /**
     * Query to access user database information.
     *
     * @return userList
     */
    public static ObservableList<User> getAllUsersList() {
         ObservableList<User> allUsersList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("User_ID");
                String user_name = rs.getString("User_Name");

                User m = new User(user_id, user_name);
                allUsersList.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsersList;
    }

    /**
     * Retrieves username and password from database so that user can use it to login to program.
     *
     * @param User_Name -username from database
     * @param Password - Password from database
     */
    public static boolean userLogin(String User_Name, String Password) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM users WHERE User_Name = ? and Password = ? ")){
            ps.setString(1, User_Name);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     *  Pulls username from database
     * @param userName - username
     * @throws IOException handles IOException
     * @throws SQLException handles SQLException
     */
    public static int getUserID(String userName) throws IOException, SQLException {
        int userId = 0;
        String sqlStatement = "SELECT User_ID, User_Name from users where User_Name = '" + userName + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userId = rs.getInt("User_ID");
            userName = rs.getString("User_Name");

        }

        return userId;
    }

    /**
     * Returns username from User ID
     *
     * @param userId - user ID
     */
    public static User returnUserId(int userId) {
        try {
            String sql = "SELECT User_ID, User_Name FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int searchedUserId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            User u = new User(searchedUserId, userName);
            return u;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Query that checks to make sure that the password is valid and in the database.
     *
     * @param Password password
     * @return true or false
     */
    public static boolean passwordValid (String Password) {
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY Password = ?")) {
            ps.setString(1, Password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks that the username is valid in the database
     *
     * @param User_Name username
     * @return true or false
     */
    public static boolean usernameValid(String User_Name){
        try (PreparedStatement ps = JDBC.database().prepareStatement("SELECT * FROM Users WHERE BINARY User_Name = ?")){
            ps.setString(1, User_Name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
