package com.example.example.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.example.bean.Friendship;
import com.example.example.bean.User;
import com.example.example.database.entity.FriendshipToUser;

import java.util.List;

@Dao
public interface FriendshipDao {
    @Query("select friendId,friendName,friendAvatar,groupId from friendship where userId= :userId and friendId = :friendId")
    FriendshipToUser selectFriendById(String userId,String friendId);

    @Insert
    public void insertFriendship(Friendship friendship);
}
