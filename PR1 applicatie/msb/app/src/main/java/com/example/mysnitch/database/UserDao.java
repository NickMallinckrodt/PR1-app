package com.example.mysnitch.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mysnitch.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT id, username, password, mailAddress, snitchScore, leaderboardPosition FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id=:uid")
    User getUserById(int uid);

    @Query("SELECT * FROM User WHERE username=:uname")
    User getUserByName(String uname);

    @Query("SELECT COUNT(*) FROM User WHERE username=:uname ")
    int doesUserExist(String uname);

    @Insert
    void insertUser(User user);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User user);
}
