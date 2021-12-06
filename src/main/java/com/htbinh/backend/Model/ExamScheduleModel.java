package com.htbinh.backend.Model;

public class ExamScheduleModel {
    private String ngaythi;
    private String tenlhp;
    private String tenhp;
    private String giangvien;
    private String giothi;
    private String phongthi;


    public ExamScheduleModel(String s, String s1, String s2) {
    }

    public ExamScheduleModel(String ngaythi, String tenlhp, String tenhp, String giangvien, String giothi, String phongthi) {
        this.ngaythi = ngaythi;
        this.tenlhp = tenlhp;
        this.tenhp = tenhp;
        this.giangvien = giangvien;
        this.giothi = giothi;
        this.phongthi = phongthi;
    }

    public String getNgaythi() {
        return ngaythi;
    }

    public void setNgaythi(String ngaythi) {
        this.ngaythi = ngaythi;
    }

    public String getTenlhp() {
        return tenlhp;
    }

    public void setTenlhp(String malhp) {
        this.tenlhp = malhp;
    }

    public String getTenhp() {
        return tenhp;
    }

    public void setTenhp(String tenhp) {
        this.tenhp = tenhp;
    }

    public String getGiangvien() {
        return giangvien;
    }

    public void setGiangvien(String giangvien) {
        this.giangvien = giangvien;
    }

    public String getGiothi() {
        return giothi;
    }

    public void setGiothi(String giothi) {
        this.giothi = giothi;
    }

    public String getPhongthi() {
        return phongthi;
    }

    public void setPhongthi(String phongthi) {
        this.phongthi = phongthi;
    }
}
