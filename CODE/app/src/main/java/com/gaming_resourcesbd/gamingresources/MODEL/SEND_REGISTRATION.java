package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_REGISTRATION {
    String full_name;
    String user_email;
    String password;
    String phone;

    public SEND_REGISTRATION(String full_name, String user_email, String password, String phone) {
        this.full_name = full_name;
        this.user_email = user_email;
        this.password = password;
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SEND_REGISTRATION{" +
                "full_name='" + full_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
