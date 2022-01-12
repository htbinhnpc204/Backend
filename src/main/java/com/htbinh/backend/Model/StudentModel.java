package com.htbinh.backend.Model;

public class StudentModel {
    private String ma_sv, ten_sv, lop, nganh;
    private String ngaySinh, soCMND, noiSinh, soDienThoai, Email;

    public StudentModel(String ma_sv, String ten_sv, String lop, String nganh, String ngaySinh, String soCMND, String noiSinh, String soDienThoai, String email) {
        this.ma_sv = ma_sv;
        this.ten_sv = ten_sv;
        this.lop = lop;
        this.nganh = nganh;
        this.ngaySinh = ngaySinh;
        this.soCMND = soCMND;
        this.noiSinh = noiSinh;
        this.soDienThoai = soDienThoai;
        this.Email = email;
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

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
