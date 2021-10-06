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

    private static Room selected;

    private static RoomList roomList;

    @FXML
    protected void initialize() {
        RoomListDAO.RefreshDB();
        roomList = RoomListDAO.getRoomList();
        cbrooms.setConverter(Utils.RoomConverter());
        cbrooms.setItems(FXCollections.observableList(Utils.SetToListR(roomList.getList())));
        Platform.runLater(() -> cbrooms.getSelectionModel().selectLast());
        tfnickname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                joinRoom();
        });
        //---------------------Actualizar salas y eliminar usuarios que no estén online-------------------
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    roomList = RoomListDAO.getRoomList();
                    Common_Window.existing_Rooms(roomList);
                    refreshComboBox();
                });
            }
        }, 0, 20000);
        //-------------------------------------------------------------------------------------------------
    }

    @FXML
    private void addRoom() {
        try {
            Common_Window.changeWindow(Common_Window.window.CREATE_ROOM);
            App.loadScene(new Stage(), "common_window", "Creador de sala", true, false);
            refreshComboBox();
            cbrooms.getSelectionModel().select(selected);
        } catch (IOException e) {
            Dialog.showError("", "", e.getMessage());
        }
    }

    public void refreshComboBox() {
        cbrooms.setItems(FXCollections.observableList(Utils.SetToListR(roomList.getList())));
    }

    @FXML
    private void joinRoom() {
        if (!tfnickname.getText().equals("") && !tfnickname.getText().isEmpty()) {
            User c = new User(tfnickname.getText());
            if (cbrooms.getSelectionModel().getSelectedItem().addUserOnline(c)) {
                cbrooms.getSelectionModel().getSelectedItem().isTemporal(false);
                Dialog.showInformation("", "", "Pulsa aceptar para unirte a la sala");
                RoomController.setRoom(cbrooms.getSelectionModel().getSelectedItem(), c);
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

    public static void changeAR(Room r){
        selected = r;
    }


}
