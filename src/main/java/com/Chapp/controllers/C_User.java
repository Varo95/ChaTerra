package com.Chapp.controllers;

import com.Chapp.models.beans.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Set;

public class C_User {
    @FXML
    private TextField tfuser;

    private static Set<User> users;

    @FXML
    protected void initialize(){

    }
    public static void existing_usersOnline(Set<User> u){
        users = u;
    }

}
