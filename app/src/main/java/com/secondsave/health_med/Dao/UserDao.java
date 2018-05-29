package com.secondsave.health_med.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("DELETE FROM user WHERE id_user=:id")
    void deleteById(int id);

    @Query("SELECT * FROM user ORDER BY id_user ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id_user = :id")
    User getUserById(int id);

    @Query("SELECT COUNT(username) FROM user WHERE username=:username AND password = :password")
    int isUserAndPasswordMatch(String username,String password);

    @Query("SELECT COUNT(username) FROM user WHERE username=:username AND token = :token")
    int isUserAndTokenMatch(String username,String token);

    @Query("UPDATE user SET token = :token  where username = :username")
    void updateUserToken(String username, String token);
}
