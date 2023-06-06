module com.example.groceryclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.groceryclient to javafx.fxml;
    exports com.example.groceryclient;
}