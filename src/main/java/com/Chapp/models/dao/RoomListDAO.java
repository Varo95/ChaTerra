package com.Chapp.models.dao;

import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.utils.Dialog;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Platform;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class RoomListDAO {

    private static String file;

    private static RoomList roomList;

    private static User actual_user;

    public static void changeFile(String filepath) {
        file = filepath;
    }

    public static void saveFile(RoomList rl) {
        saveFile(rl, new File(file));
    }

    public static void saveFile(RoomList rl, File f) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            JAXBContext jaxbC = JAXBContext.newInstance(RoomList.class);
            Marshaller m = jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(rl, w);
        } catch (IOException | JAXBException e) {
            Dialog.showError("Error", "Hubo un error al guardar el XML", e.getMessage());
        }
    }

    public static RoomList load() {
        return loadFile(new File(file));
    }

    public static RoomList loadFile(File f) {
        RoomList result = null;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            JAXBContext jaxbC = JAXBContext.newInstance(RoomList.class);
            Unmarshaller um = jaxbC.createUnmarshaller();
            result = (RoomList) um.unmarshal(r);
        } catch (IOException | JAXBException e) {
            Dialog.showError("Error", "Hubo un error al cargar el XML", "Se intentará crear un nuevo fichero en esa ruta");
            saveFile(new RoomList());
        }
        return result == null ? new RoomList() : result;
    }

    public static void RefreshDB() {
        if (roomList == null) {
            roomList = load();
        }
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    RoomList temp = load();
                    if (temp.getList().size() > roomList.getList().size()) {
                        //Recorro las salas e intento añadirlas, si no son iguales no las cambiará
                        for (Room room_xml : temp.getList()) {
                            if (roomList.addRoom(room_xml)) {
                                System.out.println("Se ha cargado una nueva sala en la ram");
                            }
                        }
                    } else {
                        //Ir recorriendo todas las salas y si son iguales, actualizar los mensajes y usuarios online
                        for (Room room_xml : temp.getList()) {
                            for (Room room_ram : roomList.getList()) {
                                //Si las habitaciones son iguales
                                if (room_ram.equals(room_xml)) {
                                    //Si el numero de mensajes del FICHERO es MAYOR que el actual de la memoria RAM
                                    if (room_ram.getMessageList().size() < room_xml.getMessageList().size()) {
                                        room_ram.getMessageList().clear();
                                        room_ram.getMessageList().addAll(room_xml.getMessageList());
                                    }
                                    room_ram.getUserList().clear();
                                    room_xml.getUserList().removeIf(u_online -> !u_online.isOnline());
                                    room_ram.getUserList().addAll(room_xml.getUserList());
                                }
                                break;
                            }
                        }
                    }
                    //En cualquier caso vuelve a escribir el fichero
                    saveFile(roomList);
                });
            }
        }, 0, 10000);
    }

    public static RoomList getRoomList() {
        return roomList;
    }

    public static void setActual_user(User u) {
        actual_user = u;
    }

    public static Room getRoom(Room r) {
        Room result = null;
        for (Room room : roomList.getList()) {
            if (r.equals(room)) {
                result = room;
                break;
            }
        }
        return result;
    }

}
