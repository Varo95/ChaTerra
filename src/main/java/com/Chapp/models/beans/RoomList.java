package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "RoomList")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomList implements Serializable {
    @XmlElement(name = "Room", type = Room.class)
    private Set<Room> list;

    public RoomList() {
        this.list = new HashSet<>();
    }

    public RoomList(Set<Room> list) {
        this.list = list;
    }

    public Set<Room> getList() {
        return list;
    }

    public void setList(Set<Room> list) {
        this.list = list;
    }

}
