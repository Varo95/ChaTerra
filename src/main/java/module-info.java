module Chaterra {
    requires com.sun.xml.bind;
    requires jakarta.xml.bind;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.Chapp.controllers to javafx.fxml, javafx.controls, javafx.graphics, javafx.media;
    opens com.Chapp.models.beans to jakarta.xml.bind, com.sun.xml.bind, com.sun.xml.bind.core;
    exports com.Chapp;
}