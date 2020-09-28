package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GET_CATEGORY2 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cat_array")
    @Expose
    private List<GET_CATEGORY> catArray = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GET_CATEGORY> getCatArray() {
        return catArray;
    }

    public void setCatArray(List<GET_CATEGORY> catArray) {
        this.catArray = catArray;
    }

}