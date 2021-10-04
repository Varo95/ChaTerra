package com.Chapp.models.beans;

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
    @XmlValue
    private String message;

    public Message() {
    }

    public Message(LocalDateTime timestamp, User user, String message) {
        this.timestamp = timestamp;
        this.user = user;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
