package sample;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LibraryController implements Initializable{

    @FXML
    private ListView<String> bookListView;
    @FXML
    private Button exitButton;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private TextField authorNameTextField;
    @FXML
    private TextField bookTitleTextField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private ImageView exitImageView;
    @FXML
    private ImageView bookImageView;
    @FXML
    private Label emptyFieldsLabel;
    @FXML
    private ImageView modeButtonImage;
    @FXML
    private BorderPane parent;
    @FXML
    private AnchorPane topLine;
    @FXML
    private AnchorPane base;
    @FXML
    private Button modeButton;
    @FXML
    private ImageView sortImageView;


    private boolean isLightMode = true;

    ArrayList<Book> listOfBooks = new ArrayList<>();
    private int selectedID = -1;

    File moonFile;
    Image moonImage;
    File sunFile;
    Image sunImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File exitFile = new File("images/exit.png");
        Image exitImage = new Image(exitFile.toURI().toString());
        exitImageView.setImage(exitImage);

        File bookFile = new File("images/book.png");
        Image bookImage = new Image(bookFile.toURI().toString());
        bookImageView.setImage(bookImage);

        File sortFile = new File("images/sort.png");
        Image sortImage = new Image(sortFile.toURI().toString());
        sortImageView.setImage(sortImage);

        moonFile = new File("images/moon.png");
        moonImage = new Image(moonFile.toURI().toString());
        modeButtonImage.setImage(moonImage);

        sunFile = new File("images/sun.png");
        sunImage = new Image(sunFile.toURI().toString());
    }

    public void exitButtonAction(ActionEvent event) {
        output();
        createMessageStageForm();
    }

    public void changeModeAction(ActionEvent event) {
        isLightMode = !isLightMode;
        if(isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }



    public void addButtonAction(ActionEvent event) {
        if (!authorNameTextField.getText().isBlank() && !genderTextField.getText().isBlank() &&
                !bookTitleTextField.getText().isBlank() && !dateTextField.getText().isBlank()) {

            if (isInteger(dateTextField.getText())) {

                listOfBooks.add(new Book(authorNameTextField.getText(), genderTextField.getText(), bookTitleTextField.getText(),
                        Integer.parseInt(dateTextField.getText())));

                bookListView.getItems().add(bookTitleTextField.getText());

                authorNameTextField.clear();
                genderTextField.clear();
                bookTitleTextField.clear();
                dateTextField.clear();
                emptyFieldsLabel.setText("");
            } else {
                emptyFieldsLabel.setText("Please fill the year as a number");
                emptyFieldsLabel.setTextFill(Color.RED);
            }
        } else {
            emptyFieldsLabel.setText("Please fill in all fields");
            emptyFieldsLabel.setTextFill(Color.RED);
        }
    }

    public void removeButtonAction(ActionEvent event) {
        selectedID = bookListView.getSelectionModel().getSelectedIndex();

        listOfBooks.remove(selectedID);
        bookListView.getItems().remove(selectedID);

        authorNameTextField.clear();
        genderTextField.clear();
        bookTitleTextField.clear();
        dateTextField.clear();
    }


    private void setLightMode(){
        base.getStylesheets().remove("styles/darkMode.css");
        base.getStylesheets().add("styles/lightMode.css");
        topLine.getStylesheets().remove("styles/darkMode.css");
        topLine.getStylesheets().add("styles/lightMode.css");
        bookListView.getStylesheets().remove("styles/darkMode.css");
        bookListView.getStylesheets().add("styles/lightMode.css");

        modeButton.getStylesheets().remove("styles/darkMode.css");
        modeButton.getStylesheets().add("styles/lightMode.css");

        modeButtonImage.setImage(moonImage);
    }

    private void setDarkMode(){
        base.getStylesheets().remove("styles/lightMode.css");
        base.getStylesheets().add("styles/darkMode.css");
        topLine.getStylesheets().remove("styles/lightMode.css");
        topLine.getStylesheets().add("styles/darkMode.css");
        bookListView.getStylesheets().remove("styles/lightMode.css");
        bookListView.getStylesheets().add("styles/darkMode.css");

        modeButton.getStylesheets().remove("styles/lightMode.css");
        modeButton.getStylesheets().add("styles/darkMode.css");

        modeButtonImage.setImage(sunImage);
    }

    public void toSortListView(ActionEvent event) {
        bookListView.getItems().clear();

        Book[] books = toSort(listOfBooks);

        for (int i = 0; i < books.length; i++) {
            bookListView.getItems().add(books[i].getNameOfBook());
        }
    }

    static Book[] toSort(ArrayList<Book> listOfBooks) {
        Book[] array = new Book[listOfBooks.size()];
        for (int i = 0; i < listOfBooks.size(); i++) {
            array[i] = listOfBooks.get(i);
        }

        int n = array.length;
        Book[] temp = new Book[1];
        boolean swapped = false;

        for (int i = 0; i < n && !swapped; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (array[j+1].getNameOfBook().compareTo(array[j].getNameOfBook()) < 0) {

                    temp[0] = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp[0];

                } else {
                    swapped = true;
                }
            }
        }
        return array;
    }

    void output() {
        try {
            File fileOut = new File("bookList.txt");
            FileWriter fw = new FileWriter(fileOut);

            Book[] books = new Book[listOfBooks.size()];

            Book[] sorted = toSort(listOfBooks);

            for (int i = 0; i < sorted.length; i++) {
                fw.write(sorted[i] + System.lineSeparator());
            }

            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    public void createMessageStageForm() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("exitMessage.fxml"));
            Stage exitMessageStage = new Stage();
            exitMessageStage.initStyle(StageStyle.UNDECORATED);
            exitMessageStage.setScene(new Scene(root, 600, 130));
            exitMessageStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
