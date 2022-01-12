package com.htbinh.backend.Model;

public class TuitionModel {
    private String hocKy;
    private int soTinChi;
    private String hocPhi, noKyTruoc, tong;

    public TuitionModel(String hocKy) {
        this.hocKy = hocKy;
    }

    public TuitionModel(String hocKy, int soTinChi, String hocPhi, String noKyTruoc, String tong) {
        this.hocKy = hocKy;
        this.soTinChi = soTinChi;
        this.hocPhi = hocPhi;
        this.noKyTruoc = noKyTruoc;
        this.tong = tong;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(String hocPhi) {
        this.hocPhi = hocPhi;
    }

    public String getNoKyTruoc() {
        return noKyTruoc;
    }

    public void setNoKyTruoc(String noKyTruoc) {
        this.noKyTruoc = noKyTruoc;
    }

    public String getTong() {
        return tong;
    }

    public void setTong(String tong) {
        this.tong = tong;
    }
}
