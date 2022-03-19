package com.example.ui.addbook;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.database.DatabaseHandler;
import com.example.util.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddBookController implements Initializable {
    DatabaseHandler databaseHandler;

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    public void addBook() throws Exception {
        String bookId = id.getText();
        String bookName = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();

        if (bookId.isEmpty() || bookName.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Util.showSimpleAlert(Alert.AlertType.ERROR, "Please Enter All Fields", "Error");
            return;
        }

        String qu = "INSERT INTO BOOK VALUES(" +
                " '" + bookId + "', " +
                " '" + bookName + "', " +
                " '" + bookAuthor + "', " +
                " '" + bookPublisher + "', " +
                " " + true + " " +
                ")";

        if (databaseHandler.execAction(qu)) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Books Added Successfully", "Success");
            Util.clearInputText(Arrays.asList(id, title, author, publisher));
        } else {
            Util.showSimpleAlert(Alert.AlertType.ERROR, "Failed", "Failed");
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
