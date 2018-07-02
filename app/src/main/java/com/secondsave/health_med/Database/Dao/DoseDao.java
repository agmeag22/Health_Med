package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Database.Entities.Dose;

import java.util.List;

@Dao
public interface DoseDao {
    @Insert
    void insert(Dose Dose);

    @Query("DELETE FROM dose")
    void deleteAll();

    @Query("DELETE FROM dose WHERE id_dose=:id")
    void deleteById(int id);

    @Query("DELETE FROM dose WHERE id_user=:id")
    void deleteByUserId(int id);

    @Query("SELECT * FROM dose ORDER BY id_user ASC")
    LiveData<List<Dose>> getAllDose();

    @Query("SELECT * FROM dose WHERE id_user=:id")
    LiveData<List<Dose>> getDoseByUserId(int id);

}
