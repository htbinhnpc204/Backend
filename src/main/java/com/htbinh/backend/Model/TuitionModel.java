package com.htbinh.backend.Model;

public class TuitionModel {
    private String hocKy;
    private int soTinChi;
    private String hocPhi, noKyTruoc;

    public TuitionModel(String hocKy, int soTinChi, String hocPhi, String noKyTruoc) {
        this.hocKy = hocKy;
        this.soTinChi = soTinChi;
        this.hocPhi = hocPhi;
        this.noKyTruoc = noKyTruoc;
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
}
