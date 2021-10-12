package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Esta clase es para el XML. Así puede meter el nombre de usuario en la etiqueta mensaje como atributo.
 * Véase su uso en Message.java, etiqueta @XmlJavaTypeAdapter
 */
public class UserBind extends XmlAdapter<String, User> {

    @Override
    public User unmarshal(String s){
        return new User(s);
    }

    @Override
    public String marshal(User user) {
        return user.getName();
    }
}
