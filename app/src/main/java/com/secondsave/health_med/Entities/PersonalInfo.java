package com.secondsave.health_med.Entities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;
@Dao
@Entity(tableName = "personal_info",foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id_user",
        childColumns = "id_user",
        onDelete = CASCADE))

public  class PersonalInfo {
    @PrimaryKey(autoGenerate = true)
    private int id_personal_info;
    private int id_user;
    private String first_name;
    private String last_name;
    public PersonalInfo(){}

    public PersonalInfo(int id_user, String first_name, String last_name) {
        this.id_user = id_user;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId_personal_info() {
        return id_personal_info;
    }

    public void setId_personal_info(int id_personal_info) {
        this.id_personal_info = id_personal_info;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}

