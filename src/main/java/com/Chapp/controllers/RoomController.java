package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.utils.Dialog;
import com.Chapp.utils.Utils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RoomController {
    @FXML
    private TableView<Message> tvmessages;
    @FXML
    private TableView<User> tvonline;
    @FXML
    private TableColumn<Message, String> tcdates;
    @FXML
    private TableColumn<Message, String> tcusers;
    @FXML
    private TableColumn<Message, String> tcmessages;
    @FXML
    private TableColumn<User, String> tconline;
    @FXML
    private TextArea tamessage;
    @FXML
    private CheckMenuItem darkmode;
    private static Room room;
    private static User user;
    private static RoomList roomList;

    /*------Añadir sonido------------
        Media sound = new Media(ssound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
     --------------------------------*/
    @FXML
    protected void initialize() {
        configureTables();
        refreshMessages();
        refreshOnlineUsers();
        //----Refrescar los mensajes y usuarios online---
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    App.updateRoomList(roomList.getList());
                    refreshMessages();
                    refreshOnlineUsers();
                });
            }
        }, 0, 30000);
        //----------------------------
        //Enter para enviar
        tamessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onclickSend();
        });
        Platform.runLater(() -> tamessage.requestFocus());
        //------------------
        darkmode.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                tamessage.getScene().getStylesheets().add(String.valueOf(App.class.getResource("dark.css")));
            } else {
                tamessage.getScene().getStylesheets().remove(String.valueOf(App.class.getResource("dark.css")));
            }
        });

    }

    private void configureTables() {
        tcdates.setCellValueFactory(eachMessage -> new SimpleStringProperty(Utils.LocalDateToString(eachMessage.getValue().getTimestamp())));
        tcusers.setCellValueFactory(eachMessage -> new SimpleStringProperty(eachMessage.getValue().getUser().getName()));
        tcmessages.setCellValueFactory(eachMessage -> new SimpleStringProperty(eachMessage.getValue().getMessage()));
        tconline.setCellValueFactory(eachUser -> new SimpleStringProperty(eachUser.getValue().getName()));
    }

    public static void setRoom(Room r, User u) {
        room = r;
        user = u;
    }

    public static void initRoomList(RoomList rl){
        roomList = rl;
    }


    @FXML
    private void onclickSend() {
        if (!tamessage.getText().equals("") && !tamessage.getText().isEmpty()) {
            Message m = new Message(LocalDateTime.now(), user, tamessage.getText());
            room.addMessage(m);
            refreshMessages();
            tvmessages.scrollTo(m);
            App.updateRoomList(roomList.getList());
            tamessage.clear();
        }
    }

    private void refreshMessages() {
        for(Message m:room.getMessageList()){
            if(!(m.getUser()==user)){
                /*MediaPlayer mediaPlayer = null;
                File f = new File(Objects.requireNonNull(App.class.getResource("newM.mp3")).toURI());
                mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(App.class.getResource("newM.mp3")).getFile()));
                mediaPlayer.play();*/
            }
        }
        tvmessages.setItems(FXCollections.observableList(room.getMessageList()));
    }

    private void refreshOnlineUsers() {
        tvonline.setItems(FXCollections.observableList(Utils.SetToListU(room.getUserList())));
    }

    @FXML
    private void exitRoom() {
        room.removeUserOnline(user);
        Dialog.showInformation("Desconexión", "Te desconectaste de la sala: " + room.getName(), "");
        user.setOnline(false);
        App.closeScene((Stage) tamessage.getScene().getWindow());
    }


}
