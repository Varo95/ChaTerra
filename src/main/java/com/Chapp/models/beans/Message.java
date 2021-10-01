package com.Chapp.models.beans;

import com.Chapp.models.dao.LocalDateTimeBind;
import com.Chapp.models.dao.UserBind;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message implements Serializable {
    @XmlAttribute(name="Time")
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeBind.class)
    private LocalDateTime timestamp;
    @XmlAttribute(name="User")
    @XmlJavaTypeAdapter(type = User.class, value = UserBind.class)
    private User user;
    @XmlTransient
    private Room room;
    @XmlValue
    private String message;

    //--------------Métodos para que el usuario, la fecha y la sala se queden como atributo en el mensaje---------------
    /*@XmlAttribute(name = "Time")
    private String setTimeXML() {
        return timestamp.toString();
    }

    @XmlAttribute(name = "User")
    private String setUserXML() {
        return user.getName();
    }*/

    //------------------------------------------------------------------------------------------------------------------
    public Message() {
    }

    public Message(LocalDateTime timestamp, User user, Room room, String message) {
        this.timestamp = timestamp;
        this.user = user;
        this.room = room;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", user=" + user +
                ", room=" + room +
                ", message='" + message + '\'' +
                '}';
    }
}
