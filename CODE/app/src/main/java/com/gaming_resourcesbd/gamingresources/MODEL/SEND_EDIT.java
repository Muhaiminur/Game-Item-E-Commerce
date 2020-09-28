package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_EDIT {
    String customer_id;
    String customer_email;
    String full_name;
    String phone;

    public SEND_EDIT(String customer_id, String customer_email, String full_name, String phone) {
        this.customer_id = customer_id;
        this.customer_email = customer_email;
        this.full_name = full_name;
        this.phone = phone;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SEND_EDIT{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", full_name='" + full_name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
