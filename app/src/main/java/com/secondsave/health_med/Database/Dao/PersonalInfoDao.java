package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.secondsave.health_med.Database.Entities.PersonalInfo;
import com.secondsave.health_med.Database.Entities.User;

import java.util.List;

@Dao
public interface PersonalInfoDao {
    @Insert
    void insert(PersonalInfo personalInfo);

    @Update
    void update(PersonalInfo personalInfo);

    @Query("DELETE FROM personal_info")
    void deleteAll();

    @Query("DELETE FROM personal_info WHERE id_personal_info=:id")
    void deleteById(int id);

    @Query("DELETE FROM personal_info WHERE username LIKE :username")
    void deleteByUserId(String username);

    @Query("SELECT * FROM personal_info ORDER BY username ASC")
    LiveData<List<PersonalInfo>> getAllPersonalInfo();

    @Query("SELECT * FROM personal_info WHERE username LIKE :username")
    PersonalInfo getPersonalInfoByUserId(String username);

}
