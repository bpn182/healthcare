module se.healthcare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    
    opens se.healthcare to javafx.fxml;
    opens models to javafx.base;
    exports se.healthcare;
}
