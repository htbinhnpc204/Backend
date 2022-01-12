package com.htbinh.backend.Model;

public class KetQuaHocTapChiTietModel {
    String tenMh, maHp, diemChu;
    int tinChi;
    float diemTk;

    public KetQuaHocTapChiTietModel(String tenMh, String maHp, int tinChi, float diemTk, String diemChu) {
        this.tenMh = tenMh;
        this.maHp = maHp;
        this.tinChi = tinChi;
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
