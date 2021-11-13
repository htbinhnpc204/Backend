package com.htbinh.backend;

import com.htbinh.backend.Model.*;

import java.util.ArrayList;
import java.util.Map;

public class SessionHelper {
    private static Map<String, String> cookies;
    private static LoginModel user;
    private static ArrayList<KetQuaHocTapChiTietModel> listKqChiTiet;
    private static StudentModel infoSinhVien;
    private static ArrayList<ScheduleModel> listTkb;
    private static ArrayList<KetQuaHocTapModel> listKq;

    public static Map<String, String> getCookies() {
        return cookies;
    }

    public static void setCookies(Map<String, String> cookies) {
        SessionHelper.cookies = cookies;
    }

    public static LoginModel getUser() {
        return user;
    }

    public static void setUser(LoginModel user) {
        SessionHelper.user = user;
    }

    public static ArrayList<KetQuaHocTapChiTietModel> getListKqChiTiet() {
        return listKqChiTiet;
    }

    public static void setListKqChiTiet(ArrayList<KetQuaHocTapChiTietModel> listKqChiTiet) {
        SessionHelper.listKqChiTiet = listKqChiTiet;
    }

    public static StudentModel getInfoSinhVien() {
        return infoSinhVien;
    }

    public static void setInfoSinhVien(StudentModel infoSinhVien) {
        SessionHelper.infoSinhVien = infoSinhVien;
    }

    public static ArrayList<ScheduleModel> getListTkb() {
        return listTkb;
    }

    public static void setListTkb(ArrayList<ScheduleModel> listTkb) {
        SessionHelper.listTkb = listTkb;
    }

    public static ArrayList<KetQuaHocTapModel> getListKq() {
        return listKq;
    }

    public static void setListKq(ArrayList<KetQuaHocTapModel> listKq) {
        SessionHelper.listKq = listKq;
    }
}
