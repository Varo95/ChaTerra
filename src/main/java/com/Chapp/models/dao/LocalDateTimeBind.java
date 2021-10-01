package com.Chapp.models.dao;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeBind extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String val) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");
        LocalDateTimeStringConverter c = new LocalDateTimeStringConverter(formatter, formatter);
        return c.fromString(val);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");
        LocalDateTimeStringConverter c = new LocalDateTimeStringConverter(formatter, formatter);
        return c.toString(localDateTime);
    }
}
