package com.gaming_resourcesbd.gamingresources.MODEL;

public class SEND_SUBCATEGORY {
    String category_id;

    public SEND_SUBCATEGORY(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "SEND_SUBCATEGORY{" +
                "category_id='" + category_id + '\'' +
                '}';
    }
}
