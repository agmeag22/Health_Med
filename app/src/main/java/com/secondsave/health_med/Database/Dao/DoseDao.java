package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.secondsave.health_med.Database.Entities.Dose;

import java.util.List;

@Dao
public interface DoseDao {
    @Insert
    void insert(Dose Dose);

    @Update
    void update(Dose dose);

    @Query("DELETE FROM dose")
    void deleteAll();

    @Query("DELETE FROM dose WHERE id_dose=:id")
    void deleteById(int id);

    @Query("DELETE FROM dose WHERE username LIKE :username")
    void deleteByUsername(String username);

    @Query("SELECT * FROM dose ORDER BY username ASC")
    LiveData<List<Dose>> getAllDose();

    @Query("SELECT * FROM dose WHERE username LIKE :id")
    LiveData<List<Dose>> getDoseByUserId(String id);

}
