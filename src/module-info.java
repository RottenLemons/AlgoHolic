module CS.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.media;
    requires java.logging;

    opens sample;
    opens sample.Controller;
    opens sample.View;
    opens sample.Model;
}