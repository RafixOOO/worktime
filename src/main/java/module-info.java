module org.example.worktime {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.web;
    requires javafx.base;

    opens org.example.worktime to javafx.fxml;
    exports org.example.worktime;
}