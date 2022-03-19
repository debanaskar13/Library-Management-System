package com.example.util;

import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class Util {
    private static Alert alert;

    public static void showSimpleAlert(AlertType type, String message, String title) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> response = alert.showAndWait();
        return response;
    }

    public static void clearInputText(List<JFXTextField> inputList) {
        for (JFXTextField input : inputList) {
            input.setText("");
        }
    }

    public static void clearText(List<Text> texts) {
        for (Text text : texts) {
            text.setText("");
        }
    }
}
