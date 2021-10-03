package com.Chapp.models.dao;

import com.Chapp.controllers.LoginController;
import com.Chapp.controllers.RoomController;
import com.Chapp.models.beans.RoomList;
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

    public static Timer t;

    public static void RefreshDB(RoomList rl){
        if(t==null) t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->{
                    saveFile(rl);
                    LoginController.updateRooms(RoomListDAO.load());
                    RoomController.updateRoom(RoomListDAO.load());
                });
            }
        }, 0, 30000);
    }

    public static void saveFile(RoomList rl) {
        saveFile(rl, "Chaterra.xml");
    }

    public static void saveFile(RoomList rl, String f) {
        saveFile(rl, new File(f));
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

    public static RoomList loadFile(File f) {
        RoomList result = null;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(RoomList.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            result = (RoomList) um.unmarshal(r);
        } catch (IOException | JAXBException e) {
            Dialog.showError("Error", "Hubo un error al cargar el XML", e.getMessage());
        }
        return result == null ? new RoomList() : result;
    }

    public static RoomList load() {
        return loadFile(new File("Chaterra.xml"));
    }
}
