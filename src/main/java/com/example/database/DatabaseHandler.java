package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;

    private static final String DB_URL = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;
    private static Statement stmt;

    private DatabaseHandler() {
        createConnection();
        // CREATE BOOK TABLE
        String sql = "CREATE TABLE IF NOT EXISTS book(id VARCHAR(200) PRIMARY KEY,title VARCHAR(200),author VARCHAR(200),publisher VARCHAR(200),isAvail BOOLEAN DEFAULT true) ";
        execAction(sql);
        // CREATE MEMBER TABLE
        sql = "CREATE TABLE IF NOT EXISTS member(id VARCHAR(200) PRIMARY KEY,name VARCHAR(200),mobile VARCHAR(20),email VARCHAR(50)) ";
        execAction(sql);
        // CREATE ISSUE TABLE
        sql = "CREATE TABLE IF NOT EXISTS issue(bookId VARCHAR(200) PRIMARY KEY, memberId VARCHAR(200),issue_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,renew_count INTEGER DEFAULT 0,FOREIGN KEY (bookId) REFERENCES book(id),FOREIGN KEY (memberId) REFERENCES member(id) )";
        execAction(sql);
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    void createConnection() {
        try {
            Class.forName(DB_URL);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springjdbc", "root", "deba8617@8001");
        } catch (Exception e) {
            e.printStackTrace();
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
