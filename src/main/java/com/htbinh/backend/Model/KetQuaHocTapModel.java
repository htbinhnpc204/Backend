package com.htbinh.backend.Model;

public class KetQuaHocTapModel {
    String hocKy, soTcTichLuy, xepLoai;
    float diemTbcHocKy, diemTbcHocBong;

    public KetQuaHocTapModel(String hocKy, String soTcTichLuy, String xepLoai, float diemTbcHocKy, float diemTbcHocBong) {
        this.hocKy = hocKy;
        this.soTcTichLuy = soTcTichLuy;
        this.xepLoai = xepLoai;
        this.diemTbcHocKy = diemTbcHocKy;
        this.diemTbcHocBong = diemTbcHocBong;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public String getSoTcTichLuy() {
        return soTcTichLuy;
    }

    public void setSoTcTichLuy(String soTcTichLuy) {
        this.soTcTichLuy = soTcTichLuy;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public float getDiemTbcHocKy() {
        return diemTbcHocKy;
    }

    public void setDiemTbcHocKy(float diemTbcHocKy) {
        this.diemTbcHocKy = diemTbcHocKy;
    }

    public float getDiemTbcHocBong() {
        return diemTbcHocBong;
    }

    public void setDiemTbcHocBong(float diemTbcHocBong) {
        this.diemTbcHocBong = diemTbcHocBong;
    }
}
