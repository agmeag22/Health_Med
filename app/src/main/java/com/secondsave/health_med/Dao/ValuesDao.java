package com.secondsave.health_med.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Entities.PersonalInfo;
import com.secondsave.health_med.Entities.Values;

import java.util.List;

@Dao
public interface ValuesDao {
    @Insert
    void insert(Values values);

    @Query("DELETE FROM `values`")
    void deleteAll();

    @Query("DELETE FROM `values` WHERE id_values=:id")
    void deleteById(int id);

    @Query("DELETE FROM `values` WHERE id_user=:id")
    void deleteAllValuesByUserId(int id);

    @Query("SELECT * FROM `values` ORDER BY id_user ASC")
    LiveData<List<Values>> getAllValues();

    @Query("SELECT * FROM `values` WHERE id_user=:id ORDER BY id_values_type ASC")
    LiveData<List<Values>> getAllValuesByUserId(int id);

    @Query("SELECT * FROM `values` WHERE id_user=:id_user AND id_values_type=:id_values_type")
    LiveData<List<Values>> getAllValuesByUserIdAndType(int id_user,String id_values_type);

}
