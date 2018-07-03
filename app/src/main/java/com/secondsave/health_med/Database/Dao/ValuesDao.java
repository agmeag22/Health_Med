package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Database.Entities.Values;

import java.util.List;

@Dao
public interface ValuesDao {
    @Insert
    void insert(Values values);

    @Query("DELETE FROM values_d")
    void deleteAll();

    @Query("DELETE FROM values_d WHERE id_values=:id")
    void deleteById(int id);

    @Query("DELETE FROM values_d WHERE id_user=:id")
    void deleteAllValuesByUserId(int id);

    @Query("SELECT * FROM values_d ORDER BY id_user ASC")
    LiveData<List<Values>> getAllValues();

    @Query("SELECT * FROM values_d WHERE id_user=:id ORDER BY id_values_type ASC")
    LiveData<List<Values>> getAllValuesByUserId(int id);

    @Query("SELECT * FROM values_d WHERE id_user=:id_user AND id_values_type=:id_values_type")
    LiveData<List<Values>> getAllValuesByUserIdAndType(int id_user,int id_values_type);

    @Query("SELECT count(id_values) FROM values_d WHERE id_user=:id_user AND id_values_type=:id_values_type")
    int countValuesByUserIdAndType(int id_user,int id_values_type);

}
