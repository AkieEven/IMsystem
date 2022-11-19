package com.example.example.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Friendship {
    @PrimaryKey
    private long friendShipId;
    private String userId;
    private String friendId;
    private long groupId;
    private String friendName;

    public Friendship(long friendShipId, String userId, String friendId, long groupId, String friendName, String friendAvatar) {
        this.friendShipId = friendShipId;
        this.userId = userId;
        this.friendId = friendId;
        this.groupId = groupId;
        this.friendName = friendName;
        this.friendAvatar = friendAvatar;
    }

    private String friendAvatar;

    public long getFriendShipId() {
        return friendShipId;
    }

    public void setFriendShipId(long friendShipId) {
        this.friendShipId = friendShipId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendAvatar() {
        return friendAvatar;
    }

    public void setFriendAvatar(String friendAvatar) {
        this.friendAvatar = friendAvatar;
    }
}
