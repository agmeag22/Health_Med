package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "dose",foreignKeys = {
        @ForeignKey(entity = DoseType.class,
                parentColumns = "id_dose_type",
                childColumns = "id_dose_type"),
        @ForeignKey(entity = User.class,
                parentColumns = "id_user",
                childColumns = "id_user",
                onDelete = CASCADE)
})
public class Dose {
    @PrimaryKey(autoGenerate = true)
    private int id_dose;
    private int id_user;
    private int id_dose_type;
    private String name;
    private float size;
    private float lapse;

    public Dose(int id_dose, int id_user, int id_dose_type, String name, float size, float lapse) {
        this.id_dose = id_dose;
        this.id_user = id_user;
        this.id_dose_type = id_dose_type;
        this.name = name;
        this.size = size;
        this.lapse = lapse;
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
}
