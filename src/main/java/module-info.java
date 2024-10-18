module org.example.womenshopfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    opens org.example.womenshopfx.Test to com.google.gson,javafx.fxml;
    requires java.sql;
    opens org.example.womenshopfx to javafx.fxml, com.google.gson;


    exports org.example.womenshopfx;
    exports org.example.womenshopfx.Test;
    exports org.example.womenshopfx.shop;
    opens org.example.womenshopfx.shop to com.fasterxml.jackson.databind, com.google.gson, javafx.fxml;

}