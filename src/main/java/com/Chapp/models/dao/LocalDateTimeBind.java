package com.Chapp.models.dao;

import com.Chapp.utils.LDTSC;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;

public class LocalDateTimeBind extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String val) {
        return LDTSC.ldtsc(LDTSC.dtf("yyyy-MM-dd HH:mm:ss:SS")).fromString(val);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        return LDTSC.ldtsc(LDTSC.dtf("yyyy-MM-dd HH:mm:ss:SS")).toString(localDateTime);
    }
}
