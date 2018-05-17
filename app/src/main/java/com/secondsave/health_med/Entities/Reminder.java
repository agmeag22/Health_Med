package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


// ERD = https://www.lucidchart.com/invitations/accept/65e4e6c9-217a-45a1-9843-bbb3354bbf20
import java.sql.Date;

@Entity(tableName = "reminder")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id_reminder;
    private String name;
    private int dose_size;
    private int dose_type;
    private int dose_lapse;
    private Date last_dose_time;
    private Date new_dose_time;

    public Reminder(String name, int dose_size, int dose_type, int dose_lapse, Date last_dose_time, Date new_dose_time) {
        this.name = name;
        this.dose_size = dose_size;
        this.dose_type = dose_type;
        this.dose_lapse = dose_lapse;
        this.last_dose_time = last_dose_time;
        this.new_dose_time = new_dose_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDose_size() {
        return dose_size;
    }

    public void setDose_size(int dose_size) {
        this.dose_size = dose_size;
    }

    public int getDose_type() {
        return dose_type;
    }

    public void setDose_type(int dose_type) {
        this.dose_type = dose_type;
    }

    public int getDose_lapse() {
        return dose_lapse;
    }

    public void setDose_lapse(int dose_lapse) {
        this.dose_lapse = dose_lapse;
    }

    public Date getLast_dose_time() {
        return last_dose_time;
    }

    public void setLast_dose_time(Date last_dose_time) {
        this.last_dose_time = last_dose_time;
    }

    public Date getNew_dose_time() {
        return new_dose_time;
    }

    public void setNew_dose_time(Date new_dose_time) {
        this.new_dose_time = new_dose_time;
    }
}
