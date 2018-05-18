package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "values_type")
class ValuesType {
    @PrimaryKey(autoGenerate = true)
    private int id_values_type;
    private String name;

    public ValuesType(String name) {
        this.name = name;
    }

    public int getId_values_type() {
        return id_values_type;
    }

    public void setId_values_type(int id_values_type) {
        this.id_values_type = id_values_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
