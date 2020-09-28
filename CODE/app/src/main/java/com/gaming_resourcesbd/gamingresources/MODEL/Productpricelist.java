package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productpricelist {

    @SerializedName("listid")
    @Expose
    private Integer listid;
    @SerializedName("listprice")
    @Expose
    private Integer listprice;
    @SerializedName("listprice_with_currency")
    @Expose
    private String listpriceWithCurrency;
    @SerializedName("listname")
    @Expose
    private String listname;

    public Integer getListid() {
        return listid;
    }

    public void setListid(Integer listid) {
        this.listid = listid;
    }

    public Integer getListprice() {
        return listprice;
    }

    public void setListprice(Integer listprice) {
        this.listprice = listprice;
    }

    public String getListpriceWithCurrency() {
        return listpriceWithCurrency;
    }

    public void setListpriceWithCurrency(String listpriceWithCurrency) {
        this.listpriceWithCurrency = listpriceWithCurrency;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    @Override
    public String toString() {
        return "Productpricelist{" +
                "listid=" + listid +
                ", listprice=" + listprice +
                ", listpriceWithCurrency='" + listpriceWithCurrency + '\'' +
                ", listname='" + listname + '\'' +
                '}';
    }
}