package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class
 *
 * @author Jacob Montoya
 */
public class Main extends Application {

    /**
     * Loads the main login screen for user to access.
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Schedule");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * opens and closes database when login screen is loaded and close
     *
     * @param args args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }


}