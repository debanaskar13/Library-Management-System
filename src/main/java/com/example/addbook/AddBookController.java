package com.example.addbook;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.database.DatabaseHandler;
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
        databaseHandler = new DatabaseHandler();
    }

    @FXML
    public void addBook() throws Exception {

        // Book book = new Book(id.getText(), title.getText(), author.getText(),
        // publisher.getText(), true);
        String bookId = id.getText();
        String bookName = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();

        if (bookId.isEmpty() || bookName.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter All Fields");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Books Added Successfully");
            alert.showAndWait();
            id.setText("");
            title.setText("");
            author.setText("");
            publisher.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
