package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_PRODUCTID {
    String product_id;

    public SEND_PRODUCTID(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "SEND_PRODUCTID{" +
                "product_id='" + product_id + '\'' +
                '}';
    }
}
