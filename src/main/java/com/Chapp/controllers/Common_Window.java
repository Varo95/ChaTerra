package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
import com.Chapp.utils.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Common_Window {
    @FXML
    private TextField textField;
    @FXML
    private Button button;
    @FXML
    private Button examine;
    @FXML
    private Label label;
    private static User old_nick;
    private static Set<User> users;
    private static RoomList rooms;
    private static window actual_window = window.SELECT_FILE;

    public enum window {
        SELECT_FILE("FILE"),
        CHANGE_NICK("NICK"),
        CREATE_ROOM("ROOM");
        private String w;

        window(String w) {
            this.w = w;
        }

        public String getW() {
            return this.w;
        }
    }

    @FXML
    protected void initialize() {
        switch (actual_window.getW()) {
            case "ROOM" -> {
                label.setText("Introduce el nombre para crear la sala");
                button.setText("Crear");
                button.setOnAction(actionEvent -> onClickAddRoom());
                textField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) onClickAddRoom();
                });

            }
            case "NICK" -> {
                button.setText("Cambiar");
                label.setText("Introduce el nuevo nickname");
                button.setOnAction(actionEvent -> onClickChangeNick());
                textField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) onClickChangeNick();
                });

            }
            case "FILE" -> {
                button.setText("Continuar");
                examine.setVisible(true);
                textField.setMinWidth(300);
                examine.setOnAction(actionEvent -> openFileChooser());
                label.setText("Introduce la ruta completa del fichero XML");
                button.setOnMouseClicked(mouseEvent -> onClickContinue());
                textField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) onClickContinue();
                });
            }
        }
    }


    public static void existing_Rooms(RoomList rl) {
        rooms = rl;
    }

    public void onClickAddRoom() {
        if (!textField.getText().equals("")) {
            Room r = new Room(textField.getText());
            if (rooms.addRoom(r)) {
                r.isTemporal(true);
                Dialog.showInformation("", "Se ha creado la sala", "¡Tienes 30 segundos para unirte antes de que sea borrada!");
                LoginController.changeAR(r);
                App.closeScene((Stage) textField.getScene().getWindow());
            } else {
                Dialog.showError("Imposible crear sala", "No se creó la sala", "Ya existe una sala con el mismo nombre");
            }
        } else {
            Dialog.showWarning("Imposible crear sala", "La sala no se pudo crear", "Verifica que el nombre de la sala es el correcto");
        }
    }

    private void onClickChangeNick() {
        if (!textField.getText().equals("")) {
            User u = new User(textField.getText());
            if (users.add(u)) {
                users.remove(old_nick);
                RoomController.setUser(u);
                Dialog.showInformation("", "Cambiado correctamente", "Ahora tu nickname es: " + textField.getText());
                App.closeScene((Stage) textField.getScene().getWindow());
            } else {
                Dialog.showError("Imposible cambiarte el nick", "No pudimos cambiarte el nick", "Ya existe un nickname igual en la sala");
            }
        } else {
            Dialog.showWarning("Imposible crear sala", "La sala no se pudo crear", "Verifica que el nombre de la sala es el correcto");
        }
    }

    private void onClickContinue() {
        RoomListDAO.changeFile(textField.getText());
        Dialog.showInformation("", "", "Se ha conectado correctamente a la base de datos");
        try {
            actual_window = window.CREATE_ROOM;
            App.closeScene((Stage) textField.getScene().getWindow());
            App.loadScene(new Stage(), "login", " Login", true, true);
        } catch (IOException e) {
            Dialog.showError("", "", "");
        }
    }

    private void openFileChooser() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.xml"));
        if (selectedFile != null) {
            textField.setText(selectedFile.getAbsolutePath());
        } else {
            Dialog.showError("Error", "", "Fichero no seleccionado");
        }
    }

    public static void changeWindow(window w) {
        actual_window = w;
    }

    public static void setUserOnView(Set<User> u, User user) {
        users = u;
        old_nick = user;
    }
}
