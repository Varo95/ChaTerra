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
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {

    private static RoomList roomList;


    @Override
    public void start(Stage stage) throws IOException {
        loadScene(stage, "common_window", " Seleccionar fichero", false, false);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadScene(Stage stage, String fxml, String title, boolean SaW, boolean notResizable) throws IOException {
        stage.setScene(new Scene(loadFXML(fxml)));
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("chaterra.png"))));
        stage.setTitle(title);
        stage.setResizable(notResizable);
        if (SaW) stage.showAndWait();
        else stage.show();
    }

    public static void closeScene(Stage stage) {
        stage.close();
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
        System.exit(0);
    }

    public static void updateRoomList(Set<Room> rl) {
        roomList.setList(rl);
    }
}