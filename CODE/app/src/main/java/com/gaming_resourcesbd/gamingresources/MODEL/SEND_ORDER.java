package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_ORDER {

    String customer_id;

    String customer_email;

    String product_id;

    String listid;

    String payment_type;

    String mobile_number;

    String transaction_id;

    String account_email_username;

    String account_passord;

    String user_game_id;

    String user_game_nickname;

    String customer_note;

    String device_id;

    public String getCustomer_id() {
        return this.customer_id;
    }

    public void setCustomer_id(String value) {
        this.customer_id = value;
    }

    public String getCustomer_email() {
        return this.customer_email;
    }

    public void setCustomer_email(String value) {
        this.customer_email = value;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String value) {
        this.product_id = value;
    }

    public String getListid() {
        return this.listid;
    }

    public void setListid(String value) {
        this.listid = value;
    }

    public String getPayment_type() {
        return this.payment_type;
    }

    public void setPayment_type(String value) {
        this.payment_type = value;
    }

    public String getMobile_number() {
        return this.mobile_number;
    }

    public void setMobile_number(String value) {
        this.mobile_number = value;
    }

    public String getTransaction_id() {
        return this.transaction_id;
    }

    public void setTransaction_id(String value) {
        this.transaction_id = value;
    }

    public String getAccount_email_username() {
        return this.account_email_username;
    }

    public void setAccount_email_username(String value) {
        this.account_email_username = value;
    }

    public String getAccount_passord() {
        return this.account_passord;
    }

    public void setAccount_passord(String value) {
        this.account_passord = value;
    }

    public String getUser_game_id() {
        return this.user_game_id;
    }

    public void setUser_game_id(String value) {
        this.user_game_id = value;
    }

    public String getUser_game_nickname() {
        return this.user_game_nickname;
    }

    public void setUser_game_nickname(String value) {
        this.user_game_nickname = value;
    }

    public String getCustomer_note() {
        return this.customer_note;
    }

    public void setCustomer_note(String value) {
        this.customer_note = value;
    }

    public String getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(String value) {
        this.device_id = value;
    }

    @Override
    public String toString() {
        return "SEND_ORDER{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", product_id='" + product_id + '\'' +
                ", listid='" + listid + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", account_email_username='" + account_email_username + '\'' +
                ", account_passord='" + account_passord + '\'' +
                ", user_game_id='" + user_game_id + '\'' +
                ", user_game_nickname='" + user_game_nickname + '\'' +
                ", customer_note='" + customer_note + '\'' +
                ", device_id='" + device_id + '\'' +
                '}';
    }
}