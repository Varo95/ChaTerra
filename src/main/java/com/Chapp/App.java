package com.Chapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(loadFXML("secondary")));
        //stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("chaterra.png"))));
        stage.setTitle(" Chattera");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadScene(Stage stage, String fxml, String title) throws IOException {
        stage.setScene(new Scene(loadFXML(fxml)));
        //stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("chaterra.png"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.showAndWait();
    }

    public static void changeTitle(Stage stage, String title){
        stage.setTitle(title);
    }

    public static void closeScene(Stage stage) {
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }
}