module com.example {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.base;
    requires com.google.gson;
    requires org.apache.commons.codec;

    opens com.example.ui.addbook to javafx.fxml;
    opens com.example.ui.listbook to javafx.fxml;
    opens com.example.ui.addmember to javafx.fxml;
    opens com.example.ui.listmember to javafx.fxml;
    opens com.example.ui.main to javafx.fxml;
    opens com.example.ui.login to javafx.fxml;
    opens com.example.settings to javafx.fxml, com.google.gson;

    exports com.example.ui.addbook;
    exports com.example.ui.listbook;
    exports com.example.ui.addmember;
    exports com.example.ui.listmember;
    exports com.example.ui.main;
    exports com.example.ui.login;
    exports com.example.settings;

}
