package com.example.didi.beans;

public class LoginBean {
    private String phone;
    private String pwd;
    private int type;

    public LoginBean(String phone, String pwd, int type) {
        this.phone = phone;
        this.pwd = pwd;
        this.type = type;
    }
    public LoginBean(){}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
