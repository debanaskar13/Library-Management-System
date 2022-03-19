
package com.example.settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Preferences {
    public static final String CONFIG_FILE = "config.txt";

    int nDaysWithoutFine;
    float finePerDay;
    String username;
    String password;

    // public Preferences() {
    // this.nDaysWithoutFine = 14;
    // this.finePerDay = 2;
    // this.username = "admin";
    // this.password = "admin";
    // }

    public Preferences() {
    }

    public Preferences(int nDaysWithoutFine, float finePerDay, String username, String password) {
        this.nDaysWithoutFine = nDaysWithoutFine;
        this.finePerDay = finePerDay;
        this.username = username;
        this.password = password;
    }

    public int getnDaysWithoutFine() {
        return nDaysWithoutFine;
    }

    public void setnDaysWithoutFine(int nDaysWithoutFine) {
        this.nDaysWithoutFine = nDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void initConfig() {
        setPreferences(14, 2, "admin", "admin");

    }

    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException e) {
            initConfig();
        }
        return preferences;
    }

    public static Boolean setPreferences(int nDaysWithoutFine, float finePerDay, String username, String password) {
        Writer writer = null;
        Preferences preferences = new Preferences(nDaysWithoutFine, finePerDay, username, password);
        Gson gson = new Gson();
        try {
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
