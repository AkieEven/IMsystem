package com.example.serverdemo;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class StatusContainer {
    private static final StatusContainer INSTANCE = new StatusContainer();

    private StatusContainer() {

    }

    public static StatusContainer getINSTANCE() {
        return INSTANCE;
    }

    private final Vector<Integer> ONLINE = new Vector<>();

    public void userComeOnline(Integer userId) {
        ONLINE.add(userId);
    }

    public void userOffline(Integer userId) {
        ONLINE.remove(userId);
    }

    public boolean isOnline(Integer userId) {
        return ONLINE.contains(userId);
    }
}
