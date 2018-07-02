package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


import java.sql.Date;

@Entity(tableName = "reminder",foreignKeys = {
        @ForeignKey(entity = Dose.class,parentColumns = "id_dose",childColumns = "id_dose")
})
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id_reminder;
    private int id_dose;
    private Date date;
    private boolean dismissed;

    public Reminder(int id_dose, Date date, boolean dismissed) {
        this.id_dose = id_dose;
        this.date = date;
        this.dismissed = dismissed;
    }

    public int getId_reminder() {
        return id_reminder;
    }

    public void setId_reminder(int id_reminder) {
        this.id_reminder = id_reminder;
    }

    public int getId_dose() {
        return id_dose;
    }

    public void setId_dose(int id_dose) {
        this.id_dose = id_dose;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
