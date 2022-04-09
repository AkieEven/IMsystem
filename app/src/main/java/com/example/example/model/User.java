package com.example.example.model;

import cn.jiguang.imui.commons.models.IUser;

public class User implements IUser {
    private String id;
    private String displayName;
    private String avatar;

    public User(String name){
        this.displayName = name;
        this.id = "?";
        this.avatar = "?";
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

    /**
     * Get user avatar file path.
     *
     * @return avatar file path
     */
    @Override
    public String getAvatarFilePath() {
        return avatar;
    }
}
