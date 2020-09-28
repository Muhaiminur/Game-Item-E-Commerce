package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GET_PRODUCTDETAILS {

    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("productthumb")
    @Expose
    private String productthumb;
    @SerializedName("producttitle")
    @Expose
    private String producttitle;
    @SerializedName("productdescription")
    @Expose
    private String productdescription;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currencySymble")
    @Expose
    private String currencySymble;
    @SerializedName("topUpType")
    @Expose
    private String topUpType;
    @SerializedName("productpricelists")
    @Expose
    private List<Productpricelist> productpricelists = null;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
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

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
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

    public String getTopUpType() {
        return topUpType;
    }

    public void setTopUpType(String topUpType) {
        this.topUpType = topUpType;
    }

    public List<Productpricelist> getProductpricelists() {
        return productpricelists;
    }

    public void setProductpricelists(List<Productpricelist> productpricelists) {
        this.productpricelists = productpricelists;
    }

    @Override
    public String toString() {
        return "GET_PRODUCTDETAILS{" +
                "productid='" + productid + '\'' +
                ", productthumb='" + productthumb + '\'' +
                ", producttitle='" + producttitle + '\'' +
                ", productdescription='" + productdescription + '\'' +
                ", currency='" + currency + '\'' +
                ", currencySymble='" + currencySymble + '\'' +
                ", topUpType='" + topUpType + '\'' +
                ", productpricelists=" + productpricelists +
                '}';
    }
}