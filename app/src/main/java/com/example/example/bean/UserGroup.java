package com.example.example.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserGroup {
    @PrimaryKey
    private long groupId;
    private String userId;
    private String groupName;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
