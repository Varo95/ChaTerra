package com.Chapp.utils;

import com.Chapp.models.beans.Room;
import com.Chapp.models.dao.RoomListDAO;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    private static LocalDateTimeStringConverter a = null;
    private static DateTimeFormatter b = null;

    /**
     * Estos métodos son singleton para formatear el texto de la fecha, estamparlo en el XML y recuperarlo bien
     */
    public static LocalDateTimeStringConverter ldtsc(DateTimeFormatter dts) {
        if (a == null)
            a = new LocalDateTimeStringConverter(dts, dts);
        return a;
    }

    public static DateTimeFormatter dtf(String s) {
        if (b == null)
            b = DateTimeFormatter.ofPattern(s);
        return b;
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
    public static List<Room> SetToList(Set<Room> rooms){
        return new ArrayList<>(rooms);
    }

    public static Set<Room> ListToSet(List<Room> rooms){
        return new HashSet<>(rooms);
    }
}
