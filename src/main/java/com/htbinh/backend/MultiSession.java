package com.htbinh.backend;

import java.util.HashMap;
import java.util.Map;

public class MultiSession {
    public static Map<String, SessionHelper> sessionDic;

    public static void init() {
        sessionDic = new HashMap<>();
    }

    public static SessionHelper getSessionByID(String id) {
        return sessionDic.get(id);
    }
}
