package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView userImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailRegTextField;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File userCreationFile = new File("images/user-creation.png");
        Image userCreationImage = new Image(userCreationFile.toURI().toString());
        userImageView.setImage(userCreationImage);
    }

    public void registerButtonAction(ActionEvent event) {
        if (!passwordField.getText().isBlank() && !confirmPasswordField.getText().isBlank() &&
                !firstnameTextField.getText().isBlank() && !lastnameTextField.getText().isBlank() &&
                !emailRegTextField.getText().isBlank()) {

            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                //confirmPasswordLabel.setText("You're set");
                confirmPasswordLabel.setText("");

                registerUser();
            } else {
                confirmPasswordLabel.setText("Password does not match");
            }
        } else {
            registrationMessageLabel.setText("Please fill in all fields");
        }

    }

    public void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String email = emailRegTextField.getText();
        String password = passwordField.getText();

        String insertFields = "INSERT INTO user_account(firstname, lastname, email, password) VALUES ('";
        String insertValues = firstname + "', '" + lastname + "', '" + email + "', '" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registrationMessageLabel.setText("User registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
}
