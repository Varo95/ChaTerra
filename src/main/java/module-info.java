module Chaterra {
    requires com.sun.xml.bind;
    requires jakarta.xml.bind;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.Chapp.controllers to javafx.fxml, javafx.controls, javafx.graphics;
    opens com.Chapp.models.beans to jakarta.xml.bind, com.sun.xml.bind;
    opens com.Chapp.models.dao to jakarta.xml.bind, com.sun.xml.bind, com.sun.xml.bind.core;
    exports com.Chapp;
}