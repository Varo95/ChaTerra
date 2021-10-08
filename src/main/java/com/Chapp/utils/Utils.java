package com.Chapp.utils;

import com.Chapp.App;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Utils {

    /**
     * Estos métodos son para formatear el texto de la fecha, estamparlo en el XML y recuperarlo bien
     */
    public static LocalDateTimeStringConverter ldtsc(DateTimeFormatter dts) {
        return new LocalDateTimeStringConverter(dts, dts);
    }

    /**
     * Este método sirve para que muestre correctamente el texto el combobox
     *
     * @return StringConverter de Rooms
     */
    public static StringConverter<Room> RoomConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Room object) {
                return object == null ? null : object.toCombox();
            }

            @Override
            public Room fromString(String string) {
                Room result = null;
                for (Room r : RoomListDAO.load().getList()) {
                    if (r.equals(new Room(string))) {
                        result = r;
                        break;
                    }
                }
                return result;
            }
        };
    }

    /**
     * Este método sirve para pasar de HasSet a ArrayList y que se pueda insertar en el combobox
     */
    public static List<Room> SetToListR(Set<Room> rooms) {
        return new ArrayList<>(rooms);
    }

    public static List<User> SetToListU(Set<User> users) {
        return new ArrayList<>(users);
    }

    /**
     * Este método sirve para formatear correctamente la fecha en la vista de la sala con
     *
     * @param localDateTime la fecha a formatear
     * @return el string de la cadena formateada
     */
    public static String LocalDateToString(LocalDateTime localDateTime) {
        return ldtsc(DateTimeFormatter.ofPattern("HH:mm:ss")).toString(localDateTime);
    }

    public static MediaPlayer onSend() {
        String s = "file://" + Objects.requireNonNull(App.class.getResource("sendM.mp3")).getFile();
        Media sound = new Media(s);
        return new MediaPlayer(sound);
    }

    public static MediaPlayer onJoinRoom() {
        String s = "file://" + Objects.requireNonNull(App.class.getResource("login.mp3")).getFile();
        Media sound = new Media(s);
        return new MediaPlayer(sound);
    }

    public static MediaPlayer onExitRoom(){
        String s = "file://" + Objects.requireNonNull(App.class.getResource("exit.mp3")).getFile();
        Media sound = new Media(s);
        return new MediaPlayer(sound);
    }
}
