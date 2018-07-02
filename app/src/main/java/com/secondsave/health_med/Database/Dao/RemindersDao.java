package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Database.Entities.Reminder;

import java.util.List;

@Dao
public interface RemindersDao {
    @Insert
    void insert(Reminder Reminder);

    @Query("DELETE FROM reminder")
    void deleteAll();

    @Query("DELETE FROM reminder WHERE id_reminder=:id")
    void deleteById(int id);

    @Query("DELETE FROM reminder WHERE id_dose=:id")
    void deleteByDoseId(int id);

    @Query("SELECT * FROM reminder ORDER BY id_dose ASC")
    LiveData<List<Reminder>> getAllReminder();

    @Query("SELECT * FROM reminder WHERE id_dose=:id")
    Reminder getReminderByDoseId(int id);

}
