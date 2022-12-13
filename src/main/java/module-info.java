module com.example.customcontrolsinjjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.customcontrolsinjjavafx to javafx.fxml;
    exports com.example.customcontrolsinjjavafx;
}