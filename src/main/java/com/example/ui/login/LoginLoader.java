package com.example.ui.login;

import com.example.database.DatabaseHandler;
import com.example.settings.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginLoader extends Application {

    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> DatabaseHandler.getInstance()).start();
        new Thread(() -> Preferences.getPreferences()).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}