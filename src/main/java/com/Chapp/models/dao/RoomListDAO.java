package com.Chapp.models.dao;

import com.Chapp.models.beans.RoomList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;

public class RoomListDAO {

    public static void saveFile(RoomList rl) throws JAXBException {
        saveFile(rl, "Chaterra.xml");
    }

    public static void saveFile(RoomList rl, String f) throws JAXBException {
        saveFile(rl, new File(f));
    }

    public static void saveFile(RoomList rl, File f) throws JAXBException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            JAXBContext jaxbC = JAXBContext.newInstance(RoomList.class);
            Marshaller m = jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(rl, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RoomList loadFile(File f) throws JAXBException {
        RoomList result = null;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(RoomList.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            result = (RoomList) um.unmarshal(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result == null ? new RoomList() : result;
    }
}
