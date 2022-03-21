package com.example.ui.login;

import com.example.settings.Preferences;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private Label loginLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleUsernameAction(ActionEvent event) {
        password.requestFocus();
    }

    @FXML
    private void handlePasswordAction(ActionEvent event) {
        loginBtn.fireEvent(new ActionEvent());
    }

    @FXML
    private void login(ActionEvent event) {

        String uname = username.getText();
        String pass = DigestUtils.sha1Hex(password.getText());

        Preferences preferences = Preferences.getPreferences();

        if ((preferences.getUsername().equals(uname)) && (preferences.getPassword().equals(pass))) {
            // Login to main Window
            ((Stage) username.getScene().getWindow()).close();
            loadWindow("/com/example/ui/main/main.fxml", "Library Management System");

        } else {
            loginLabel.setText("Invalid Credentials");
            loginLabel.setStyle("-fx-background-color: red;-fx-text-fill: white;");
        }

    }

    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) username.getScene().getWindow()).close();
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

}
