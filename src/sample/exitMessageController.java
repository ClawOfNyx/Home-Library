package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class exitMessageController implements Initializable {

    @FXML
    private ImageView bookMessageImageView;

    @FXML
    private Button okButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bookFile = new File("images/book.png");
        Image bookImage = new Image(bookFile.toURI().toString());
        bookMessageImageView.setImage(bookImage);
    }

    public void okButton(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}
