
package com.example.settings;

import com.example.util.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField finePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField nDaysWithoutFine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initiDefaultValues();
    }

    @FXML
    private void handleNDaysWithoutFineAction(ActionEvent event) {
        finePerDay.requestFocus();
    }

    @FXML
    private void handleFinePerDayAction(ActionEvent event) {
        username.requestFocus();
    }

    @FXML
    private void handleUsernameAction(ActionEvent event) {
        password.requestFocus();
    }

    @FXML
    private void handlePasswordAction(ActionEvent event) {
        saveButton.fireEvent(new ActionEvent());
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
        try {
            int nDays = Integer.parseInt(nDaysWithoutFine.getText());
            float fine = Float.parseFloat(finePerDay.getText());
            String uname = String.valueOf(username.getText());
            String pass = String.valueOf(password.getText());
            if (Preferences.setPreferences(nDays, fine, uname, pass)) {
                Util.showSimpleAlert(Alert.AlertType.INFORMATION, "Settings Saved Successfully", "Success");
            }
        } catch (NumberFormatException e) {
            Util.showSimpleAlert(Alert.AlertType.ERROR, "Please Enter Valid Number", "Invalid Number");
        }
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        ((Stage) finePerDay.getScene().getWindow()).close();
    }

    private void initiDefaultValues() {
        Preferences preferences = Preferences.getPreferences();
        finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
        nDaysWithoutFine.setText(String.valueOf(preferences.getnDaysWithoutFine()));
    }
}
