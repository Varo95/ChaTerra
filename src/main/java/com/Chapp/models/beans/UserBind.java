package com.Chapp.models.beans;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

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
