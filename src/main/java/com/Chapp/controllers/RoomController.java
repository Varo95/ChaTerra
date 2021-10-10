package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
import com.Chapp.utils.Dialog;
import com.Chapp.utils.Utils;
import com.Chapp.utils.emoji.EmojiImageCache;
import com.Chapp.utils.emoji.EmojiParser;
import com.Chapp.utils.emoji.EmojiTextFlow;
import com.Chapp.utils.emoji.EmojiTextFlowParameters;
import com.Chapp.view.TableRowMessage;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class RoomController {
    @FXML
    private TableView<Message> tvmessages;
    @FXML
    private TableColumn<Message, String> tcdates;
    @FXML
    private TableColumn<Message, String> tcusers;
    @FXML
    private TableColumn<Message, EmojiTextFlow> tcmessages;
    @FXML
    private TableView<User> tvonline;
    @FXML
    private TableColumn<User, String> tconline;
    @FXML
    private TextArea tamessage;
    @FXML
    private CheckMenuItem darkmode;
    @FXML
    private Button emojibutton;
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
                });
            }
        }, 0, 20000);
        //----------------------------
        //Enter para enviar
        tamessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onclickSend();
        });
        //------------------
        //Cuando sales de la ventana que haga la misma función que pulsar Archivo->Salir de la sala
        Platform.runLater(() -> {
            tamessage.requestFocus();
            Stage a = (Stage) tamessage.getScene().getWindow();
            a.setOnCloseRequest(windowEvent -> exitRoom());
        });
        //-------------------
        ImageView emoji = new ImageView();
        emoji.setImage(EmojiImageCache.getInstance().getImage(EmojiSearchController.getEmojiImagePath(EmojiParser.getInstance().getEmoji(":slight_smile:").getHex())));
        emoji.setFitHeight(25.6);
        emoji.setFitWidth(28);
        emojibutton.setGraphic(emoji);
        emojibutton.setStyle("-fx-border-color: transparent;-fx-border-width: 0;-fx-background-radius: 0;-fx-background-color: transparent;-fx-font-family:\"Segoe UI\", Helvetica, Arial, sans-serif;-fx-font-size: 1em;-fx-text-fill: #828282;");
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
        tcmessages.setCellValueFactory(eachMessage -> new TableRowMessage(new EmojiTextFlowParameters(), eachMessage.getValue().getMessage(),tcmessages));
        tconline.setCellValueFactory(eachUser -> new SimpleStringProperty(eachUser.getValue().getName()));
    }

    public static void setRoom(Room r, User u) {
        room = r;
        user = u;
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
            MediaPlayer md = Utils.onSend();
            md.play();
            Message m = new Message(LocalDateTime.now(), user, tamessage.getText().replace("\n", ""));
            room.addMessage(m);
            refreshMessages();
            tvmessages.scrollTo(m);
            tamessage.clear();
        }
    }

    private void refreshMessages() {
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
        for (User u : room.getUserList()) {
            if (u.equals(user)) {
                u.setOnline(false);
                break;
            }
        }
        RoomListDAO.saveFile(RoomListDAO.getRoomList());
        Dialog.showInformation("Desconexión", "Te desconectaste de la sala: " + room.getName(), "");
        MediaPlayer mp = Utils.onExitRoom();
        mp.play();
        App.closeScene((Stage) tamessage.getScene().getWindow());
    }

    @FXML
    private void changeUserNick() {
        Common_Window.setUserOnView(room, user);
        Common_Window.changeWindow(Common_Window.window.CHANGE_NICK);
        try {
            App.loadScene(new Stage(), "common_window", "Cambiar nickname", true, false);
            refreshOnlineUsers();
        } catch (IOException e) {
            Dialog.showError("Error", "Hubo un error en la vista", "Error en changeUserNick");
        }
    }

    @FXML
    public void onclickEmoji() {
        try {
            EmojiSearchController.setTextArea(tamessage);
            App.loadScene(new Stage(), "emojiList", "Lista emojis", false, true);
        } catch (IOException e) {
            Dialog.showError("Error", "Hubo un error en la vista", "Error en onclickEmoji");
        }
    }

}
