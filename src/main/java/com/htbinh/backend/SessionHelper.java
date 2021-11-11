package com.htbinh.backend;

import com.htbinh.backend.Model.LoginModel;
import com.htbinh.backend.Model.SinhVienModel;
import org.jsoup.Jsoup;

import java.util.Map;

public class SessionHelper {
    private static Map<String, String> cookies;
    private static LoginModel sv;

    public static LoginModel getSv() {
        return sv;
    }

    public static void setSv(LoginModel sv) {
        SessionHelper.sv = sv;
    }

    public static Map<String, String> getCookies() {
        return cookies;
    }

    public static void setCookies(Map<String, String> cookies) {
        SessionHelper.cookies = cookies;
    }
}
