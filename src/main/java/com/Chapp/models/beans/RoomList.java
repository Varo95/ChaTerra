package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "RoomList")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomList implements Serializable {
    @XmlElement(name = "Room", type = Room.class)
    private List<Room> list;

    public RoomList() {
        this.list = new ArrayList<>();
    }

    public RoomList(List<Room> list) {
        this.list = list;
    }

    public List<Room> getList() {
        return list;
    }

    public void setList(List<Room> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RoomList{" +
                "list=" + list +
                '}';
    }
}
