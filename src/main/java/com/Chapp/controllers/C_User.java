package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.User;
import com.Chapp.utils.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Set;

public class C_User {
    @FXML
    private TextField tfuser;

    private static User old_nick;
    private static Set<User> users;

    @FXML
    protected void initialize(){
        tfuser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onClickChange();
        });
    }
    public static void verifyChangeNick(Set<User> u, User user){
        users = u;
        old_nick = user;
    }

    private void onClickChange(){
        if (!tfuser.getText().equals("")) {
            User u = new User(tfuser.getText());
            if(users.add(u)){
                users.remove(old_nick);
                Dialog.showInformation("","Cambiado correctamente","Ahora tu nickname es: "+tfuser.getText());
                App.closeScene((Stage)tfuser.getScene().getWindow());
            }else{
                Dialog.showError("Imposible cambiarte el nick","No pudimos cambiarte el nick","Ya existe un nickname igual en la sala");
            }
        }else{
            Dialog.showWarning("Imposible crear sala","La sala no se pudo crear","Verifica que el nombre de la sala es el correcto");
        }
    }

}
