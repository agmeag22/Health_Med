package com.secondsave.health_med.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "dose")
public class Dose {
    @PrimaryKey(autoGenerate = true)
    private int id_dose;
    @ForeignKey(entity = User.class,
            parentColumns = "id_user",
            childColumns = "id_user",
            onDelete = CASCADE)
    private int id_user;
    private int id_dose_type;
    private String name;
    private float size;
    private Date start_date;
    private Date end_date;
    private Date next_date;
    private float lapse;
    private boolean reminder_enabled;

    public Dose(int id_dose, int id_user, int id_dose_type, String name, float size, Date start_date, Date end_date, float lapse, boolean reminder_enabled) {
        this.id_dose = id_dose;
        this.id_user = id_user;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
}
