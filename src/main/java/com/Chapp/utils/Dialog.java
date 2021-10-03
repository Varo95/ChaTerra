package com.Chapp.utils;

import com.Chapp.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Dialog {

    public static void showError(String title, String header, String description) {
        showDialog(AlertType.ERROR, title, header, description);
    }

    public static void showWarning(String title, String header, String description) {
        showDialog(AlertType.WARNING, title, header, description);
    }

    public static void showInformation(String title, String header, String description) {
        showDialog(AlertType.INFORMATION, title, header, description);
    }

    public static boolean showConfirmation(String title, String header, String description) {
        return showDialogBoolean(title, header, description);
    }

    private static void showDialog(AlertType type, String title, String header, String description) {
        Alert alert = new Alert(type);
        addIcon((Stage) alert.getDialogPane().getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
    }

    private static boolean showDialogBoolean(String title, String header, String description) {
        boolean result;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        addIcon((Stage) alert.getDialogPane().getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
        result = alert.getResult().getButtonData().isDefaultButton();
        return result;
    }

    private static void addIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("chaterra.png"))));
    }
}
