package com.example.ui.addmember;

import com.example.database.DatabaseHandler;
import com.example.util.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class AddMemberController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private JFXTextField mName;
    @FXML
    private JFXTextField mId;
    @FXML
    private JFXTextField mMobile;
    @FXML
    private JFXTextField mEmail;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = DatabaseHandler.getInstance();
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void addMember(ActionEvent event) {
        String name = mName.getText();
        String id = mId.getText();
        String mobile = mMobile.getText();
        String email = mEmail.getText();

        if (name.isEmpty() || id.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            Util.showSimpleAlert(Alert.AlertType.ERROR, "Please Enter All Fields", "Error");
            return;
        }

        String qu = "INSERT INTO member values(" +
                "'" + id + "'," +
                "'" + name + "'," +
                "'" + mobile + "'," +
                "'" + email + "'" +
                ")";

        if (handler.execAction(qu)) {
            Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Member Added Successfully", "Success");
            Util.clearInputText(Arrays.asList(mName, mId, mMobile, mEmail));
        } else {
            Util.showSimpleAlert(Alert.AlertType.ERROR, "Failed", "Failed");
        }

    }

}
