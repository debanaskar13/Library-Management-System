package com.example.ui.main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.database.DatabaseHandler;
import com.example.util.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
    DatabaseHandler handler;

    @FXML
    private Text bName;
    @FXML
    private Text bAuthor;
    @FXML
    private Text bAvail;
    @FXML
    private Text mName;
    @FXML
    private Text mContact;
    @FXML
    private TextField bId;
    @FXML
    private TextField mId;
    @FXML
    private JFXTextField bookId;
    @FXML
    private ListView<String> issueDataList;

    Boolean isReadyForSubmission = false;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submissionBtn;
    @FXML
    private JFXButton issueBtn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = DatabaseHandler.getInstance();
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/com/example/ui/addmember/add_member.fxml", "Add New Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/com/example/ui/addbook/add_book.fxml", "Add New Book");

    }

    @FXML
    private void loadViewMember(ActionEvent event) {
        loadWindow("/com/example/ui/listmember/member_list.fxml", "Member List");

    }

    @FXML
    private void loadViewBook(ActionEvent event) {
        loadWindow("/com/example/ui/listbook/book_list.fxml", "Book List");

    }

    @FXML
    private void loadSettings(ActionEvent event) {
        loadWindow("/com/example/settings/settings.fxml", "Settings");
    }

    private void loadWindow(String loc, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Boolean isBookInStock = false;
    boolean isBookAvailable = false;

    @FXML
    private void showBookInfo(ActionEvent event) {
        boolean flag = false;
        isBookInStock = false;
        String qu = "SELECT * FROM book where id= '" + bId.getText() + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                bName.setText(rs.getString("title"));
                bAuthor.setText(rs.getString("author"));
                bAvail.setText(rs.getBoolean("isAvail") ? "Available" : "Not Available");
                flag = true;
                isBookInStock = true;
                isBookAvailable = rs.getBoolean("isAvail");
            }
            if (!flag) {
                Util.clearText(Arrays.asList(bName, bAvail));
                bAuthor.setText("No Such Book Available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean isAvailableMember = false;

    @FXML
    private void showMemberInfo(ActionEvent event) {
        boolean flag = false;
        isAvailableMember = false;
        String qu = "SELECT * FROM member where id= '" + mId.getText() + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                mName.setText(rs.getString("name"));
                mContact.setText(rs.getString("mobile"));
                flag = true;
                isAvailableMember = true;
            }
            if (!flag) {
                mContact.setText("");
                mName.setText("No Such Member Available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void issueBook(ActionEvent event) {
        if (!isBookInStock) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Please select Book", "Invalid Entry");
            return;
        }
        if (!isAvailableMember) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Please select Member", "Invalid Entry");
            return;
        }
        if (!isBookAvailable) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Book is not Available", "Not Available");
            return;
        }

        Optional<ButtonType> response = Util.showConfirmAlert(
                "Are you sure want to issue the book " + bName.getText() + " \nto " + mName.getText() + " ?",
                "Issue Book");
        if (response.get() == ButtonType.OK) {
            String qu1 = "INSERT INTO issue(bookId,memberId) VALUES('" + bId.getText() + "'," + "'" + mId.getText()
                    + "')";
            String qu2 = "UPDATE book SET isAvail = false WHERE id = '" + bId.getText() + "'";
            if (handler.execAction(qu1) && handler.execAction(qu2)) {
                Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Book Issued Successfully", "Success");
                isBookInStock = isBookAvailable = isAvailableMember = false;
                resetInput();
            } else {
                Util.showSimpleAlert(Alert.AlertType.ERROR, "Failed", "Failed");
                resetInput();
            }
        } else {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Issue Operation Cancelled", "Cancelled");
            resetInput();
        }
    }

    private void resetInput() {
        bId.setText("");
        bName.setText("Book Name");
        bAuthor.setText("Author");
        bAvail.setText("Availability");
        mId.setText("");
        mName.setText("Member Name");
        mContact.setText("Contact");
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        boolean flag = false;
        ObservableList<String> issueData = FXCollections.observableArrayList();

        String qu = "SELECT * FROM issue WHERE bookId = '" + bookId.getText() + "'";
        ResultSet rs = handler.execQuery(qu);

        try {
            while (rs.next()) {
                isReadyForSubmission = true;
                flag = true;
                String issuedMemberId = rs.getString("memberId");

                issueData.add("Issue Date And Time : " + rs.getTimestamp("issue_time").toString());
                issueData.add("Renew Count : " + rs.getInt("renew_count"));

                qu = "SELECT * FROM book WHERE id = '" + bookId.getText() + "'";
                ResultSet r1 = handler.execQuery(qu);
                issueData.add("");
                issueData.add("Book Information : ");
                while (r1.next()) {
                    issueData.add("\tBook Name : " + r1.getString("title"));
                    issueData.add("\tBook Id : " + r1.getString("id"));
                    issueData.add("\tBook Author : " + r1.getString("author"));
                    issueData.add("\tBook Publisher : " + r1.getString("publisher"));
                }

                qu = "SELECT * FROM member WHERE id = '" + issuedMemberId + "'";

                r1 = handler.execQuery(qu);
                issueData.add("");
                issueData.add("Member Information : ");
                while (r1.next()) {
                    issueData.add("\tMember Name : " + r1.getString("name"));
                    issueData.add("\tMember Mobile : " + r1.getString("mobile"));
                    issueData.add("\tMember Email : " + r1.getString("email"));
                }
            }
            if (!flag) {
                issueData.add("This book is not issued to anyone");
                renewBtn.setDisable(true);
                submissionBtn.setDisable(true);
            } else {
                renewBtn.setDisable(false);
                submissionBtn.setDisable(false);
            }

            issueDataList.getItems().setAll(issueData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void renewButtonAction(ActionEvent event) {
        if (!isReadyForSubmission) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "There is no book to renew", "Empty");
            return;
        }
        String query = "UPDATE issue SET renew_count = renew_count + 1,issue_time = CURRENT_TIMESTAMP WHERE bookId = '"
                + bookId.getText() + "'";

        Optional<ButtonType> response = Util.showConfirmAlert("Are you sure want to renew the book ?",
                "Renew Book");
        if (response.get() == ButtonType.OK) {
            if (handler.execAction(query)) {
                Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Book Renew Successfully", "Success");
                bookId.fireEvent(new ActionEvent());
            } else {
                Util.showSimpleAlert(Alert.AlertType.ERROR, "Failed", "Failed");
            }
        } else {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Cancelled", "Cancelled");
        }
    }

    @FXML
    private void submissionButtonAction(ActionEvent event) {
        if (!isReadyForSubmission) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "There is no book to submit", "Empty");
            return;
        }
        String query = "DELETE FROM issue WHERE bookId = '" + bookId.getText() + "'";
        String query2 = "UPDATE book SET isAvail = true WHERE id = '" + bookId.getText() + "'";
        Optional<ButtonType> response = Util.showConfirmAlert("Are you sure want to submit the book ?",
                "Book Submission");
        if (response.get() == ButtonType.OK) {
            if (handler.execAction(query) && handler.execAction(query2)) {
                Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Book Submitted Successfully", "success");
                bookId.fireEvent(new ActionEvent());
            } else {
                Util.showSimpleAlert(Alert.AlertType.ERROR, "Failed", "Failed");
            }
        } else {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Cancelled", "Cancelled");
        }
    }

}
