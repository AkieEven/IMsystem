package com.example.example;

import com.example.example.bean.ContactGroup;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserData {
    private static final UserData instance = new UserData();

    private UserData() {
    }

    public static UserData getInstance() {
        return instance;
    }

    private static final ArrayList<ContactGroup> contactGroups = new ArrayList<>();
    private static final ConcurrentHashMap<String,Integer> groupPosition = new ConcurrentHashMap<>();

    public static ArrayList<ContactGroup> getContactGroups() {
        return contactGroups;
    }
    public static ConcurrentHashMap<String,Integer> getGroupPosition() {return groupPosition;}
}