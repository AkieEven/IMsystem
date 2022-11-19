package com.example.serverdemo.entity;

import lombok.Data;

@Data
public class Friendship {
    private long friendshipId;
    private int userId;
    private int friendId;
    private long groupId;
    private String friendAvatar;
    private String friendName;

    public Friendship(int userId, int friendId, long groupId, String friendAvatar, String friendName) {
        this.userId = userId;
        this.friendId = friendId;
        this.groupId = groupId;
        this.friendAvatar = friendAvatar;
        this.friendName = friendName;
    }
}
