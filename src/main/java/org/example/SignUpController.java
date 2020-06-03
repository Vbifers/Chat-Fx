package org.example;

import DB.DBFields;
import DB.DBHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.HashMap;

public class SignUpController {

    @FXML
    private TextField signUpFirstName;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpLastName;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private RadioButton signUpMale;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private RadioButton signUpFemale;

    @FXML
    private TextField signUpCountry;

    @FXML
    private TextField signUpLogin;


    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();


        signUpButton.setOnAction(actionEvent -> {
            String firstName = signUpFirstName.getText();
            String lastName = signUpLastName.getText();
            String login = signUpLogin.getText();
            String password = signUpPassword.getText();
            String gender = signUpFemale.isSelected()? "Female": "Male";
            String location = signUpCountry.getText();
            User user= new User(firstName, lastName, login, password, location, gender);
            dbHandler.signUpUser(user);
        });

    }
}
