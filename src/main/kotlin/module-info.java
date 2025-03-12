module org.example.hiddenpixel {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.example.hiddenpixel to javafx.fxml;
    exports org.example.hiddenpixel;
}