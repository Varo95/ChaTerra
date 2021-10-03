package com.Chapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;

public class App extends Application {

    private static HashMap<String, Timer> timers;

    @Override
    public void start(Stage stage) throws IOException {
        loadScene(stage, "login", " Iniciar Sesión", false, false);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadScene(Stage stage, String fxml, String title, boolean SaW, boolean notResizable) throws IOException {
        stage.setScene(new Scene(loadFXML(fxml)));
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("chaterra.png"))));
        stage.setTitle(title);
        stage.setOnCloseRequest(windowEvent -> cancelAndPurgeTimers(fxml));
        stage.setOnHiding(windowEvent -> cancelAndPurgeTimers(fxml));
        stage.setOnHidden(windowEvent -> cancelAndPurgeTimers(fxml));
        stage.setResizable(notResizable);
        if (SaW) stage.showAndWait();
        else stage.show();
    }

    public static void closeScene(Stage stage) {
        stage.close();
    }

    public static void addTimer(Timer t, String fxml) {
        if (timers == null) timers = new HashMap<>();
        timers.put(fxml, t);
    }

    public static void cancelAndPurgeTimers(String fxml) {
        if (timers != null)
            for (String s : timers.keySet()) {
                if (fxml.equals(s)) {
                    Timer t = timers.get(s);
                    t.cancel();
                    t.purge();
                }
            }
    }

    public static void main(String[] args) {
        launch();
    }
}