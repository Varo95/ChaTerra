package com.Chapp;

import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args){
        //App.main(args);
        //--------------------------------------Prueba de escritura-----------------------------------------------------
        /*User a = new User("Alvaro");
        User m = new User("Gonzalo");
        Room c = new Room("Salita",a);
        c.addUser(m);

        Message b = new Message(LocalDateTime.now(),a,c,"Hola mundo");
        Message k = new Message(LocalDateTime.now(),m,c,"Hola mundo2");
        List<Message> d = new ArrayList<>();
        d.add(b);
        d.add(k);
        c.setMessageList(d);
        List<Room> x = new ArrayList<>();
        x.add(c);
        RoomList z = new RoomList(x);
        try{
            RoomListDAO.saveFile(z);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------Prueba de lectura------------------------------------------------------

        /*RoomList roomList = null;
        try {
            roomList = RoomListDAO.loadFile(new File("Chaterra.xml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        for(Room r: roomList.getList()){
            System.out.println("Nombre de la sala: "+r.getName());
            System.out.println("Usuarios online: ");
            String s="";
            for(User u: r.getUserList()){
                s=s+","+u.getName();
            }
            System.out.println(s);
            System.out.println("------------------------------");
            System.out.println("Mensajes de la sala:");
            for(Message m:r.getMessageList()){
                System.out.println("->Hora: "+m.getTimestamp().toString());
                System.out.println("-->Usuario: "+m.getUser().getName());
                System.out.println("--->Mensaje: "+m.getMessage());
            }
        }*/

    }
}
