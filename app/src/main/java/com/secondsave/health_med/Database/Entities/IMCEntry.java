package com.secondsave.health_med.Database.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "imc_entry")
public class IMCEntry {
    @PrimaryKey(autoGenerate = true)
    private int id_values;
    private String username;
    private float imc;
    private float height;
    private float weight;
    private Date create_date;

    public IMCEntry(String username, float imc, float height, float weight, Date create_date) {
        this.username = username;
        this.imc = imc;
        this.height = height;
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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
