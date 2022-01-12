package com.htbinh.backend;

import com.htbinh.backend.Model.*;

import java.util.HashMap;
import java.util.Map;

public class SessionHelper {
    private static Map<String, LoginModel> user = new HashMap<>();
    private static String Notification;

    public static String getNotification() {
        return Notification;
    }

    public static void setNotification(String notification) {
        Notification = notification;
    }

    public static LoginModel getUserByID(String msv) {
        return user.get(msv);
    }

    public static void setUser(LoginModel user) {
        SessionHelper.user.put(user.getMsv(), user);
    }

    public static void removeUser(String msv){ SessionHelper.user.remove(msv); }
}
