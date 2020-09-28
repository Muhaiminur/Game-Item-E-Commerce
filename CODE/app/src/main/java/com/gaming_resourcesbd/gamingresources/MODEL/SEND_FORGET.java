package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_FORGET {
    String customer_login;

    public SEND_FORGET(String customer_login) {
        this.customer_login = customer_login;
    }

    public String getCustomer_login() {
        return customer_login;
    }

    public void setCustomer_login(String customer_login) {
        this.customer_login = customer_login;
    }

    @Override
    public String toString() {
        return "SEND_FORGET{" +
                "customer_login='" + customer_login + '\'' +
                '}';
    }
}
