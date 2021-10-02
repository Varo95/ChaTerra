import com.Chapp.models.beans.Message;
import com.Chapp.models.beans.Room;
import com.Chapp.models.beans.RoomList;
import com.Chapp.models.beans.User;
import com.Chapp.models.dao.RoomListDAO;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLTest {
    /**
     * Este archivo sirve para probar la escritura y la lectura del fichero XML
     */
    public static void main(String[] args) {
        write();
        read();
    }

    public static void write() {
        User a = new User("Alvaro");
        User m = new User("Gonzalo");
        Room c = new Room("Salita",a);
        c.addUserOnline(m);

        Message b = new Message(LocalDateTime.now(),a,c,"Hola mundo");
        Message k = new Message(LocalDateTime.now(),m,c,"Hola mundo2");
        List<Message> d = new ArrayList<>();
        d.add(b);
        d.add(k);
        c.setMessageList(d);
        Set<Room> x = new HashSet<>();
        x.add(c);
        RoomList z = new RoomList(x);
        try{
            RoomListDAO.saveFile(z);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void read() {
        RoomList roomList = null;
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
        }
    }
}
