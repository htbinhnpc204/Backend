package com.htbinh.backend.Model;

public class LoginModel {

    String Msv, Password;

    public LoginModel(String msv, String password) {
        Msv = msv;
        Password = password;
    }

    public String getMsv() {
        return Msv;
    }

    public void setMsv(String msv) {
        Msv = msv;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
