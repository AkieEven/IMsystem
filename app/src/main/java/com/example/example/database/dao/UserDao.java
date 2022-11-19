package com.example.example.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.example.bean.User;

@Dao
public interface UserDao {
    @Insert
    public void insertUser(User user);

    @Update
    public void updateUser(User user);

    @Query("select * from User where id = :userId")
    public User selectByUserId(String userId);
}
