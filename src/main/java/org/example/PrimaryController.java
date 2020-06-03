package org.example;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Describes customer interface and controller for "Sign in" window
 */
public class PrimaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() throws IOException{
        signInButton.setOnAction(actionEvent -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            loginUser(login, password);
        });

        signUpButton.setOnAction(actionEvent -> {
            signUpButton.getScene().getWindow().hide();
            Scene scene;
            try {
                scene = new Scene(loadFXML("signUp"));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        passwordField.setOnAction(actionEvent -> {
            loginUser(loginField.getText(), passwordField.getText());
        });
    }

    /**
     * Creates new User and looking for it in the database.
     * if the user is found, there will be open the main chat window
     *
     * @param login the existing login in DB
     * @param password the password for corresponding user
     *
     * @throws SQLException if connection to DB failed
     */
    private void loginUser(String login, String password) {

        if(login.equals("")&&password.equals("")){
            System.out.println("login and password are empty!");
            return;
        }
        User user = new User();
        user.setUsername(login);
        user.setPassword(password);

        DBHandler dbHandler = new DBHandler();

        ResultSet resultSet = dbHandler.getUser(user);
        try {
            if (resultSet.next()) {
                signUpButton.getScene().getWindow().hide();
                Scene scene;
                try {
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    //get controller for text area form and set parameters
                    SecondaryController controllerEditBook = loader.getController();
                    controllerEditBook.setUser(user);
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Wrong");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
