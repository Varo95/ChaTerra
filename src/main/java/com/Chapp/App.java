package com.Chapp;

import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.dao.RoomListDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class App extends Application {

    private static HashMap<String, Timer> timers;
    private static RoomList roomList;

    @Override
    public void start(Stage stage) throws IOException {
        RefreshDB();
        //addTimer(new Timer(),"DB");
        //stage.setOnCloseRequest(windowEvent -> cancelAndPurgeTimers("DB"));
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
        //stage.setOnCloseRequest(windowEvent -> cancelAndPurgeTimers(fxml));
        //stage.setOnHiding(windowEvent -> cancelAndPurgeTimers(fxml));
        //stage.setOnHidden(windowEvent -> cancelAndPurgeTimers(fxml));
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
        if (timers != null && fxml.equals("DB")) {
            Timer t = timers.get(fxml);
            t.cancel();
            t.purge();
        }
    }

    public static void RefreshDB() {
        roomList = RoomListDAO.load();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (roomList != null)
                    Platform.runLater(() -> RoomListDAO.saveFile(roomList));
            }
        }, 0, 10000);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void updateRoomList(Set<Room> rl) {
        roomList = new RoomList(rl);
    }
}