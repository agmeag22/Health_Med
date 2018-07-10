package com.secondsave.health_med.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.secondsave.health_med.Database.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();


    @Query("SELECT * FROM user ORDER BY username ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT COUNT(username) FROM user WHERE username LIKE :username AND password = :password")
    int isUserAndPasswordMatch(String username, String password);

    @Query("SELECT COUNT(username) FROM user WHERE username LIKE :username AND token = :token")
    int isUserAndTokenMatch(String username, String token);

    @Query("UPDATE user SET token = :token  where username LIKE :username")
    void updateUserToken(String username, String token);
}
