package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHandler {
    DatabaseHandler handler;

    private static final String DB_URL = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;
    private static Statement stmt;

    public DatabaseHandler() {
        createConnection();
        setupBookTable();
    }

    void createConnection() {
        try {
            Class.forName(DB_URL);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springjdbc", "root", "deba8617@8001");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setupBookTable() {
        try {
            String sql = "create table if not exists book(id varchar(200) primary key,title varchar(200),author varchar(200),publisher varchar(200),isAvail boolean default true) ";
            stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Exception at ExecQuery : dataHandler " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        } catch (Exception e) {
            System.out.println("Exception at ExecQuery : dataHandler " + e.getLocalizedMessage());
            return false;
        }
    }
}
