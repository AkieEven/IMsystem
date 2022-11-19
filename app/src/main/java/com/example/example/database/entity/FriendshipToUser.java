package com.example.example.database.entity;

import androidx.room.ColumnInfo;

public class FriendshipToUser {
    @ColumnInfo(name = "friendId")
    public String friendId;

    @ColumnInfo(name = "friendName")
    public String friendName;

    @ColumnInfo(name = "friendAvatar")
    public String friendAvatar;

    @ColumnInfo(name = "groupId")
    public long groupId;
}
