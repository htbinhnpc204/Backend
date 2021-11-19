package com.htbinh.backend.Model;

public class ScheduleModel {
    private String tenHp, tenLopHp, giangVien, Thu, Tiet, Phong;

    public ScheduleModel(String tenHp, String tenLopHp, String giangVien, String thu, String tiet, String phong) {
        this.tenHp = tenHp;
        this.tenLopHp = tenLopHp;
        this.giangVien = giangVien;
        Thu = thu;
        Tiet = tiet;
        Phong = phong;
    }

    public String getTenHp() {
        return tenHp;
    }

    public void setTenHp(String tenHp) {
        this.tenHp = tenHp;
    }

    public String getTenLopHp() {
        return tenLopHp;
    }

    public void setTenLopHp(String tenLopHp) {
        this.tenLopHp = tenLopHp;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public String getTiet() {
        return Tiet;
    }

    public void setTiet(String tiet) {
        Tiet = tiet;
    }

    public String getPhong() {
        return Phong;
    }

    public void setPhong(String phong) {
        Phong = phong;
    }
}
