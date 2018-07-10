package com.secondsave.health_med.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.secondsave.health_med.Utils.DateConverter;


import java.io.Serializable;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "dose")
public class Dose implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id_dose;
    @ForeignKey(entity = User.class,
            parentColumns = "username",
            childColumns = "username",
            onDelete = CASCADE)
    private String username;
    private int id_dose_type;
    private String name;
    private float size;
    @TypeConverters({DateConverter.class})
    private Date start_date;
    @TypeConverters({DateConverter.class})
    private Date end_date;
    @TypeConverters({DateConverter.class})
    private Date next_date;
    private float lapse;
    private boolean reminder_enabled;

    public Dose(String username, int id_dose_type, String name, float size, Date start_date, Date end_date, float lapse, boolean reminder_enabled) {
        this.username = username;
        this.id_dose_type = id_dose_type;
        this.name = name;
        this.size = size;
        this.start_date = start_date;
        this.end_date = end_date;
        this.lapse = lapse;
        this.reminder_enabled = reminder_enabled;
    }

    public int getId_dose() {
        return id_dose;
    }

    public void setId_dose(int id_dose) {
        this.id_dose = id_dose;
    }

    public int getId_dose_type() {
        return id_dose_type;
    }

    public void setId_dose_type(int id_dose_type) {
        this.id_dose_type = id_dose_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getLapse() {
        return lapse;
    }

    public void setLapse(float lapse) {
        this.lapse = lapse;
    }

    public Date getNext_date() {
        return next_date;
    }

    public void setNext_date(Date next_date) {
        this.next_date = next_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public boolean isReminder_enabled() {
        return reminder_enabled;
    }

    public void setReminder_enabled(boolean reminder_enabled) {
        this.reminder_enabled = reminder_enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
