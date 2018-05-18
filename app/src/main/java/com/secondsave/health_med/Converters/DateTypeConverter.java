package com.secondsave.health_med.Converters;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

public class DateTypeConverter {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}