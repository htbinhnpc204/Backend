package com.htbinh.backend;

import com.htbinh.backend.Model.*;

import java.util.ArrayList;
import java.util.Map;

public class SessionHelper {
    private Map<String, String> cookies;
    private LoginModel user;
    private ArrayList<KetQuaHocTapChiTietModel> listKqChiTiet;
    private ArrayList<ScheduleModel> listTkb;
    private ArrayList<KetQuaHocTapModel> listKq;
    private ArrayList<NewsModel> listNews;
    private ArrayList<TuitionModel> listTuition;

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public LoginModel getUser() {
        return user;
    }

    public void setUser(LoginModel user) {
        this.user = user;
    }

    public ArrayList<KetQuaHocTapChiTietModel> getListKqChiTiet() {
        return listKqChiTiet;
    }

    public void setListKqChiTiet(ArrayList<KetQuaHocTapChiTietModel> listKqChiTiet) {
        this.listKqChiTiet = listKqChiTiet;
    }

    public ArrayList<ScheduleModel> getListTkb() {
        return listTkb;
    }

    public void setListTkb(ArrayList<ScheduleModel> listTkb) {
        this.listTkb = listTkb;
    }

    public ArrayList<KetQuaHocTapModel> getListKq() {
        return listKq;
    }

    public void setListKq(ArrayList<KetQuaHocTapModel> listKq) {
        this.listKq = listKq;
    }

    public ArrayList<NewsModel> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<NewsModel> listNews) {
        this.listNews = listNews;
    }

    public ArrayList<TuitionModel> getListTuition() {
        return listTuition;
    }

    public void setListTuition(ArrayList<TuitionModel> listTuition) {
        this.listTuition = listTuition;
    }

    public SessionHelper(Map<String, String> cookies, LoginModel user) {
        this.cookies = cookies;
        this.user = user;
    }
}
