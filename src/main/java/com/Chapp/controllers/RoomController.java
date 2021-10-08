package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
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
                    room = RoomListDAO.getRoom(room);
                    refreshMessages();
                    refreshOnlineUsers();
                    for(User u: room.getUserList()){
                        if(!u.isOnline()){
                            room.removeUserOnline(u);
                        }
                    }
                });
            }
        }, 0, 20000);
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
        room.addUserOnline(user);
        RoomListDAO.setActual_user(user);
    }

    public static void setUser(User u) {
        user = u;
    }

    @FXML
    private void onclickSend() {
        if (!tamessage.isFocused())
            tamessage.requestFocus();
        if (!tamessage.getText().equals("") && !tamessage.getText().isEmpty()) {
            Message m = new Message(LocalDateTime.now(), user, tamessage.getText());
            room.addMessage(m);
            refreshMessages();
            tvmessages.scrollTo(m);
            tamessage.clear();
        }
    }

    private void refreshMessages() {
        /* ¿Reproducir audio cada vez que veamos un nuevo mensaje?
        MediaPlayer mediaPlayer = null;
        File f = new File(Objects.requireNonNull(App.class.getResource("newM.mp3")).toURI());
        mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(App.class.getResource("newM.mp3")).getFile()));
        mediaPlayer.play();
        */
        tvmessages.setItems(FXCollections.observableList(room.getMessageList()));
        //Se desplaza hasta el último mensaje
        if (room.getMessageList().size() > 0)
            tvmessages.scrollTo(room.getMessageList().get(room.getMessageList().size() - 1));
        tvmessages.refresh();
    }

    private void refreshOnlineUsers() {
        tvonline.setItems(FXCollections.observableList(Utils.SetToListU(room.getUserList())));
        tvonline.refresh();
    }

    @FXML
    private void exitRoom() {
        user.setOnline(false);
        for(User u: room.getUserList()){
            if(u.equals(user)){
                u.setOnline(false);
            }
        }
        RoomListDAO.saveFile(RoomListDAO.getRoomList());
        Dialog.showInformation("Desconexión", "Te desconectaste de la sala: " + room.getName(), "");
        App.closeScene((Stage) tamessage.getScene().getWindow());
    }

    @FXML
    private void changeUserNick() {
        Common_Window.setUserOnView(room.getUserList(), user);
        Common_Window.changeWindow(Common_Window.window.CHANGE_NICK);
        try {
            App.loadScene(new Stage(), "common_window", "Creador de sala", true, false);
            refreshOnlineUsers();
        } catch (IOException e) {
            Dialog.showError("Error", "Hubo un error en la vista", "Error en changeUserNick");
        }
    }


}
