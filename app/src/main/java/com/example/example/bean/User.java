package com.example.example.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cn.jiguang.imui.commons.models.IUser;

@Entity
public class User implements IUser {
    @PrimaryKey
    @NonNull
    private String id;
    private String displayName;
    @Ignore
    private boolean online;
    private String avatar;

    public User(String name) {
        this.displayName = name;
        this.id = "0";
        this.online = false;
        this.avatar = "R.drawable.background";
    }

    public User(String id, String displayName, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    public User(String name,String id) {
        this.displayName = name;
        this.id = id;
        this.online = false;
        this.avatar = "R.drawable.background";
    }

    public User(String id, String displayName, boolean online, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.online = online;
        this.avatar = avatar;
    }

    /**
     * User id.
     *
     * @return user id, unique
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Display name of user
     *
     * @return display name
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Get user avatar file path.
     *
     * @return avatar file path
     */
    @Override
    public String getAvatarFilePath() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isOffline() {
        return !online;
    }

    public String getStatus() {
        return online?"在线":"离线";
    }


}
