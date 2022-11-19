package com.example.example.utils;

import java.util.UUID;

public class RandomID {
    public static long getID() {
        return UUID.randomUUID().getLeastSignificantBits();
    }

    public static String getStringID() {
        return Long.toString(UUID.randomUUID().getLeastSignificantBits());
    }
}
