package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button goToRegisterButton;
    @FXML
    private Label loginFailedMessage;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField enterPasswordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File lockFile = new File("images/lock-screen.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginButtonAction(ActionEvent event) {
        if (!emailTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginFailedMessage.setText("Please enter your Email and Password");
        }
    }

    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void toRegisterButtonAction(ActionEvent event) {
        createAccountStageForm();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE email = '" + emailTextField.getText() +
                "' AND password ='" + enterPasswordField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginFailedMessage.setText("");
                    createLibraryStageForm();
                } else {
                    loginFailedMessage.setText("Invalid Login. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void createAccountStageForm() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 600));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createLibraryStageForm() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("library.fxml"));
            Stage libraryStage = new Stage();
            libraryStage.initStyle(StageStyle.UNDECORATED);
            libraryStage.setScene(new Scene(root));
            libraryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
