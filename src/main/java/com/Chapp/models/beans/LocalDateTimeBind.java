package com.Chapp.models.beans;

import com.Chapp.utils.Utils;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase sirve para escribir el objeto LocalDateTime en XML y recuperar la cadena de texto escrita a LocalDateTime
 */
public class LocalDateTimeBind extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String val) {
        return Utils.ldtsc(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS")).fromString(val);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        return Utils.ldtsc(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS")).toString(localDateTime);
    }
}
