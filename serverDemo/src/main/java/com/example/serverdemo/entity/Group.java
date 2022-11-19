package com.example.serverdemo.entity;

import lombok.Data;

@Data
public class Group {
    private long groupId;
    private int userId;
    private String groupName;

    public Group(int userId, String groupName) {
        this.userId = userId;
        this.groupName = groupName;
    }

    public Group(long groupId, int userId, String groupName) {
        this.groupId = groupId;
        this.userId = userId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupId+':'+groupName;
    }
}
