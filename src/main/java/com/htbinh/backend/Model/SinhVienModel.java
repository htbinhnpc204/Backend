package com.htbinh.backend.Model;

public class SinhVienModel {
    String ma_sv, ten_sv, lop, nganh, khoa;

    String ngaySinh;


    public SinhVienModel(String ma_sv, String ten_sv, String lop, String nganh, String khoa, String ngaySinh) {
        this.ma_sv = ma_sv;
        this.ten_sv = ten_sv;
        this.lop = lop;
        this.nganh = nganh;
        this.khoa = khoa;
        this.ngaySinh = ngaySinh;
    }

    public String getMa_sv() {
        return ma_sv;
    }

    public void setMa_sv(String ma_sv) {
        this.ma_sv = ma_sv;
    }

    public String getTen_sv() {
        return ten_sv;
    }

    public void setTen_sv(String ten_sv) {
        this.ten_sv = ten_sv;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
