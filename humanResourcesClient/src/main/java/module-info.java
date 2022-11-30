module hr.humanresourcesclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hr.hr to javafx.fxml;
    exports hr.hr;
}