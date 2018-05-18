package com.secondsave.health_med.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Entities.PersonalInfo;
import com.secondsave.health_med.Entities.User;

import java.util.List;

@Dao
public interface PersonalInfoDao {
    @Insert
    void insert(PersonalInfo personalInfo);

    @Query("DELETE FROM personal_info")
    void deleteAll();

    @Query("DELETE FROM personal_info WHERE id_personal_info=:id")
    void deleteById(int id);

    @Query("DELETE FROM personal_info WHERE id_user=:id")
    void deleteByUserId(int id);

    @Query("SELECT * FROM personal_info ORDER BY id_user ASC")
    LiveData<List<PersonalInfo>> getAllPersonalInfo();

    @Query("SELECT * FROM personal_info WHERE id_user = :id")
    PersonalInfo getPersonalInfoByUserId(int id);

}
