package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {
    @XmlValue
    private String name;
    @XmlAttribute(name="isOnline")
    private boolean isOnline;

    private User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline(){
        return this.isOnline;
    }

    public void setOnline(boolean isOnline){
        this.isOnline=isOnline;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

}
