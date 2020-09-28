package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_HISTORY {
    String customer_id;
    String customer_email;

    public SEND_HISTORY(String customer_id, String customer_email) {
        this.customer_id = customer_id;
        this.customer_email = customer_email;
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

    @Override
    public String toString() {
        return "SEND_HISTORY{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_email='" + customer_email + '\'' +
                '}';
    }
}
