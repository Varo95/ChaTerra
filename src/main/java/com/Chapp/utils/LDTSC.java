package com.Chapp.utils;

import com.Chapp.models.beans.Room;
import com.Chapp.models.dao.RoomListDAO;
import jakarta.xml.bind.JAXBException;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class LDTSC {
    private static LocalDateTimeStringConverter a = null;
    private static DateTimeFormatter b = null;

    public static LocalDateTimeStringConverter ldtsc(DateTimeFormatter dts) {
        if (a == null)
            a = new LocalDateTimeStringConverter(dts, dts);
        return a;
    }
    public static DateTimeFormatter dtf(String s){
        if (b == null)
            b = DateTimeFormatter.ofPattern(s);
        return b;
    }

    /**
     * Este método sirve para que muestre correctamente el texto el combobox
     * @return StringConverter de Rooms
     */
    public static StringConverter<Room> RoomConverter(){
        return new StringConverter<>() {
            @Override
            public String toString(Room object) {
                return object == null ? null : object.toCombox();
            }

            @Override
            public Room fromString(String string) {
                Room result= null;
                try {
                    for(Room r: RoomListDAO.loadFile(new File("Chaterra.xml")).getList()){
                        if(r.equals(new Room(string))){
                            result=r;
                            break;
                        }
                    }
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
    }
}
