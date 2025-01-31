module com.example.practicaut4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.practicaut4 to javafx.fxml;
    exports com.example.practicaut4;
}