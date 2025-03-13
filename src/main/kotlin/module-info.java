module org.example.hiddenpixel {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;


    opens org.example.hiddenpixel.controller to javafx.fxml;
    exports org.example.hiddenpixel;
}