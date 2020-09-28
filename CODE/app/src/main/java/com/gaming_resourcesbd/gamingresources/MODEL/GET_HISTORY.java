package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_HISTORY {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_name")
    @Expose
    private String orderName;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_datetime")
    @Expose
    private String orderDatetime;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_estimated_delivery_datetime")
    @Expose
    private String orderEstimatedDeliveryDatetime;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("order_currency")
    @Expose
    private String orderCurrency;
    @SerializedName("order_quantity")
    @Expose
    private Integer orderQuantity;
    @SerializedName("order_payment_method")
    @Expose
    private String orderPaymentMethod;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderEstimatedDeliveryDatetime() {
        return orderEstimatedDeliveryDatetime;
    }

    public void setOrderEstimatedDeliveryDatetime(String orderEstimatedDeliveryDatetime) {
        this.orderEstimatedDeliveryDatetime = orderEstimatedDeliveryDatetime;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    @Override
    public String toString() {
        return "GET_HISTORY{" +
                "orderId='" + orderId + '\'' +
                ", orderName='" + orderName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDatetime='" + orderDatetime + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderEstimatedDeliveryDatetime='" + orderEstimatedDeliveryDatetime + '\'' +
                ", orderTotal='" + orderTotal + '\'' +
                ", orderCurrency='" + orderCurrency + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", orderPaymentMethod='" + orderPaymentMethod + '\'' +
                '}';
    }
}