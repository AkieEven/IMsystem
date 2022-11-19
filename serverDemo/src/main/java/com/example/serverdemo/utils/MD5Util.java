package com.example.serverdemo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {
    public static String generate(String password, String salt) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bs = md.digest((password + salt).getBytes(StandardCharsets.UTF_8));
            StringBuffer hexValue = new StringBuffer();
            for (byte b : bs) {
                int val = ((int) b) & 0xff;
                if (val < 16) hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            result = hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean check(String password, String salt, String hashcode) {
        String hashedPassword = generate(password, salt);
        return hashcode.equals(hashedPassword);
    }
}
