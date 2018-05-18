package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


// ERD = https://www.lucidchart.com/invitations/accept/65e4e6c9-217a-45a1-9843-bbb3354bbf20
import java.sql.Date;

@Entity(tableName = "reminder",foreignKeys = {
        @ForeignKey(entity = Dose.class,parentColumns = "id_dose",childColumns = "id_dose")
})
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id_reminder;
    private int id_dose;
    private Date start_date;
    private Date next_date;
    private boolean dismissed;

    public Reminder(int id_dose, Date start_date, Date next_date, boolean dismissed) {
        this.id_dose = id_dose;
        this.start_date = start_date;
        this.next_date = next_date;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getNext_date() {
        return next_date;
    }

    public void setNext_date(Date next_date) {
        this.next_date = next_date;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }
}
