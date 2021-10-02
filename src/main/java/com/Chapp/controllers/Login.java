package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.dao.RoomListDAO;
import com.Chapp.utils.Dialog;
import com.Chapp.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    @FXML
    private ComboBox<Room> cbrooms;
    @FXML
    private TextField tfnickname;
    @FXML
    private static RoomList rl;

    @FXML
    protected void initialize() {
        rl = RoomListDAO.load();
        cbrooms.setConverter(Utils.RoomConverter());
        cbrooms.setItems(FXCollections.observableList(Utils.SetToList(rl.getList())));
        cbrooms.getSelectionModel().selectFirst();
    }

    @FXML
    private void addRoom() {
        C_Room.existing_Rooms(rl);
        try {
            App.loadScene(new Stage(), "c_room", "Creador de sala", true);
            refreshComboBox();
        } catch (IOException e) {
            Dialog.showError("", "", e.getMessage());
        }
    }

    private void refreshComboBox() {
        cbrooms.setItems(FXCollections.observableList(Utils.SetToList(rl.getList())));
    }
}
