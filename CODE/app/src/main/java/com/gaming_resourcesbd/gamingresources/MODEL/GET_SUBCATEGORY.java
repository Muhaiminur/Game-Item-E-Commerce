package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_SUBCATEGORY {

    @SerializedName("productid")
    @Expose
    private Integer productid;
    @SerializedName("productthumb")
    @Expose
    private String productthumb;
    @SerializedName("producttitle")
    @Expose
    private String producttitle;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currencySymble")
    @Expose
    private String currencySymble;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductthumb() {
        return productthumb;
    }

    public void setProductthumb(String productthumb) {
        this.productthumb = productthumb;
    }

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencySymble() {
        return currencySymble;
    }

    public void setCurrencySymble(String currencySymble) {
        this.currencySymble = currencySymble;
    }

    @Override
    public String toString() {
        return "GET_SUBCATEGORY{" +
                "productid=" + productid +
                ", productthumb='" + productthumb + '\'' +
                ", producttitle='" + producttitle + '\'' +
                ", currency='" + currency + '\'' +
                ", currencySymble='" + currencySymble + '\'' +
                '}';
    }
}