module com.cafemanagement.perkpoint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.cafemanagement.perkpoint to javafx.fxml;
    exports com.cafemanagement.perkpoint;
}