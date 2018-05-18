package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dose_type")
public class DoseType {
    @PrimaryKey(autoGenerate = true)
    private int id_dose_type;
    private String name;

    public DoseType(String name) {
        this.name = name;
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
}
