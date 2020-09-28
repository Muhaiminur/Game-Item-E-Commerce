package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_USER {
    String username;
    String password;

    public SEND_USER(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SEND_USER{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
