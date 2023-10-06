module se.healthcare {
    requires javafx.controls;
    requires javafx.fxml;

    opens se.healthcare to javafx.fxml;
    exports se.healthcare;
}
