package com.htbinh.backend.Model;

public class KetQuaHocTapChiTietModel {
    String tenMh, maHp, diemChu;
    int tinChi;
    float diemCc, diemGk, diemCk, diemTk;

    public KetQuaHocTapChiTietModel(String tenMh, String maHp, int tinChi, float diemCc, float diemGk, float diemCk, float diemTk, String diemChu) {
        this.tenMh = tenMh;
        this.maHp = maHp;
        this.tinChi = tinChi;
        this.diemCc = diemCc;
        this.diemGk = diemGk;
        this.diemCk = diemCk;
        this.diemTk = diemTk;
        this.diemChu = diemChu;
    }

    public String getTenMh() {
        return tenMh;
    }

    public void setTenMh(String tenMh) {
        this.tenMh = tenMh;
    }

    public String getMaHp() {
        return maHp;
    }

    public void setMaHp(String maHp) {
        this.maHp = maHp;
    }

    public int getTinChi() {
        return tinChi;
    }

    public void setTinChi(int tinChi) {
        this.tinChi = tinChi;
    }

    public float getDiemCc() {
        return diemCc;
    }

    public void setDiemCc(float diemCc) {
        this.diemCc = diemCc;
    }

    public float getDiemGk() {
        return diemGk;
    }

    public void setDiemGk(float diemGk) {
        this.diemGk = diemGk;
    }

    public float getDiemCk() {
        return diemCk;
    }

    public void setDiemCk(float diemCk) {
        this.diemCk = diemCk;
    }

    public float getDiemTk() {
        return diemTk;
    }

    public void setDiemTk(float diemTk) {
        this.diemTk = diemTk;
    }

    public String getDiemChu() {
        return diemChu;
    }

    public void setDiemChu(String diemChu) {
        this.diemChu = diemChu;
    }
}
