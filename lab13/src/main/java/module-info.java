module lab {
    requires javafx.controls;
    requires javafx.fxml;
    opens pl.pwr to javafx.fxml;
    exports pl.pwr;
}