package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_PAYMENT {

    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("number_type")
    @Expose
    private String numberType;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("method_description")
    @Expose
    private String methodDescription;
    @SerializedName("success_message")
    @Expose
    private String successMessage;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

}