package com.Chapp.models.dao;

import com.Chapp.models.beans.RoomList;
import com.Chapp.utils.Dialog;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;

public class RoomListDAO {

    private static String file;

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

    public static void changeFile(String filepath){
        file=filepath;
    }
    public static RoomList load() {
        return loadFile(new File(file));
    }
}
