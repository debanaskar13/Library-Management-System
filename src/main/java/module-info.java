module com.example {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;

    opens com.example.addbook to javafx.fxml;
    opens com.example.ui.listbook to javafx.fxml;

    exports com.example.addbook;
    exports com.example.ui.listbook;
}
