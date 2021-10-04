package com.Chapp.models.beans;

import com.Chapp.models.beans.User;
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
