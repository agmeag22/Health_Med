package com.secondsave.health_med.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.NO_ACTION;

@Entity(tableName = "values_d",
        foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id_user",
        childColumns = "id_user",
        onDelete = CASCADE)})
public class Values {
    @PrimaryKey(autoGenerate = true)
    private int id_values;
    private int id_user;
    private int id_values_type;
    private float value;
    private Date create_date;

    public Values(int id_user, int id_values_type, float value, Date create_date) {
        this.id_user = id_user;
        this.id_values_type = id_values_type;
        this.value = value;
        this.create_date = create_date;
    }

    public int getId_values() {
        return id_values;
    }

    public void setId_values(int id_values) {
        this.id_values = id_values;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_values_type() {
        return id_values_type;
    }

    public void setId_values_type(int id_values_type) {
        this.id_values_type = id_values_type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
