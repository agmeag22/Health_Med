package com.secondsave.health_med.Database.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


import com.secondsave.health_med.Utils.DateConverter;

import java.util.Date;

@Entity(tableName = "imc_entry")
public class IMCEntry {
    @PrimaryKey(autoGenerate = true)
    private int id_values;
    private String username;
    private float imc;
    private float fat;
    private float weight;
    @TypeConverters({DateConverter.class})
    private Date create_date;

    public IMCEntry(String username, float imc, float fat, float weight, Date create_date) {
        this.username = username;
        this.imc = imc;
        this.fat = fat;
        this.weight = weight;
        this.create_date = create_date;
    }

    public int getId_values() {
        return id_values;
    }

    public void setId_values(int id_values) {
        this.id_values = id_values;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float IMC) {
        this.imc = IMC;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }
}
