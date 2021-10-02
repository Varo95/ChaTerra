package com.Chapp.controllers;

import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class Secondary {
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

}
