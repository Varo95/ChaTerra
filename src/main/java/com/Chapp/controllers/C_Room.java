package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.utils.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class C_Room {
    @FXML
    private TextField tfroom;
    private static RoomList rooms;

    @FXML
    protected void initialize() {

    }

    public static void existing_Rooms(RoomList rl) {
        rooms = rl;
    }
    @FXML
    public void onClickAdd() {
        if (!tfroom.getText().equals("")) {
            Room r = new Room(tfroom.getText());
            if(rooms.addRoom(r)){
                Dialog.showInformation("","Se ha creado","La sala se creó correctamente");
                App.closeScene((Stage)tfroom.getScene().getWindow());
            }else{
                Dialog.showError("Imposible crear sala","No se creó la sala","Ya existe una sala con el mismo nombre");
            }
        }else{
            Dialog.showWarning("Imposible crear sala","La sala no se pudo crear","Verifica que el nombre de la sala es el correcto");
        }
    }
}
