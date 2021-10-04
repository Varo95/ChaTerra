package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
import com.Chapp.utils.Dialog;
import com.Chapp.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginController {
    @FXML
    private ComboBox<Room> cbrooms;
    @FXML
    private TextField tfnickname;

    private static RoomList roomList;

    @FXML
    protected void initialize() {
        roomList = RoomListDAO.load();
        cbrooms.setConverter(Utils.RoomConverter());
        cbrooms.setItems(FXCollections.observableList(Utils.SetToListR(roomList.getList())));
        tfnickname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                joinRoom();
        });
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    App.updateRoomList(roomList.getList());
                    C_Room.existing_Rooms(roomList);
                    refreshComboBox();
                });
            }
        }, 0, 30000);
        //App.addTimer(t, "login");
        LoginController.sendRoomList(roomList);
    }

    private static void sendRoomList(RoomList rl) {
        roomList = rl;
    }

    @FXML
    private void addRoom() {
        try {
            App.loadScene(new Stage(), "c_room", "Creador de sala", true, false);
            refreshComboBox();
        } catch (IOException e) {
            Dialog.showError("", "", e.getMessage());
        }
    }

    public void refreshComboBox() {
        cbrooms.setItems(FXCollections.observableList(Utils.SetToListR(roomList.getList())));
        cbrooms.getSelectionModel().selectFirst();
    }

    @FXML
    private void joinRoom() {
        if (!tfnickname.getText().equals("") && !tfnickname.getText().isEmpty()) {
            User c = new User(tfnickname.getText());
            if (cbrooms.getSelectionModel().getSelectedItem().addUserOnline(c)) {
                Dialog.showInformation("", "", "Pulsa aceptar para unirte a la sala");
                RoomController.setRoom(cbrooms.getSelectionModel().getSelectedItem(), c);
                RoomController.initRoomList(roomList);
                try {
                    App.loadScene(new Stage(), "room", "Sala: " + cbrooms.getSelectionModel().getSelectedItem().getName(), true, true);
                    tfnickname.clear();
                } catch (IOException e) {
                    Dialog.showError("Hubo un error", "Error al cargar la vista room", e.getMessage());
                }
            } else {
                Dialog.showWarning("No pudiste entrar", "El usuario ya existe", "¡Hay un usuario online con el mismo nombre!\n Prueba otro nickname");
            }
        } else {
            Dialog.showError("", "No pudiste entrar", "Tienes que rellenar al menos el campo del nickname");
        }
    }


}
