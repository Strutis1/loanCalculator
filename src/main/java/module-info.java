module com.crew.mif.loancalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.crew.mif.loancalculator to javafx.fxml;
    exports com.crew.mif.loancalculator;
    exports calculations;
    exports data;
    opens calculations to javafx.fxml;
}