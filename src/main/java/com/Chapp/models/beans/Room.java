package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "Room")
@XmlAccessorType(XmlAccessType.FIELD)
public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @XmlAttribute(name = "Name")
    private String name;
    @XmlElementWrapper(name = "Online_Users")
    @XmlElement(name = "User", type = User.class)
    private Set<User> userList;
    @XmlElementWrapper(name = "Messages")
    @XmlElement(name = "Message", type = Message.class)
    private List<Message> messageList;
    @XmlTransient
    private boolean isTemporal;

    public Room() {
    }

    public Room(String name, User user) {
        this.name = name;
        this.userList = new HashSet<>();
        this.userList.add(user);
        this.isTemporal = true;
    }

    public Room(String name, Set<User> users, List<Message> messageList) {
        this.name = name;
        this.userList = users;
        this.messageList = messageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user) {
        this.userList.add(user);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public boolean isTemporal() {
        return isTemporal;
    }

    public void isTemporal(boolean isTemporal) {
        this.isTemporal = isTemporal;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", userList=" + userList +
                ", messageList=" + messageList +
                ", isTemporal=" + isTemporal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name);
    }
}
