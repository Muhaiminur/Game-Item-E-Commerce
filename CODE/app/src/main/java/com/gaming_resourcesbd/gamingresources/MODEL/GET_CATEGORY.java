package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_CATEGORY {

    @SerializedName("catid")
    @Expose
    private Integer catid;
    @SerializedName("catname")
    @Expose
    private String catname;
    @SerializedName("catslug")
    @Expose
    private String catslug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("catthumb")
    @Expose
    private String catthumb;
    @SerializedName("bannerurl")
    @Expose
    private Boolean bannerurl;

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatslug() {
        return catslug;
    }

    public void setCatslug(String catslug) {
        this.catslug = catslug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatthumb() {
        return catthumb;
    }

    public void setCatthumb(String catthumb) {
        this.catthumb = catthumb;
    }

    public Boolean getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(Boolean bannerurl) {
        this.bannerurl = bannerurl;
    }

    @Override
    public String toString() {
        return "GET_CATEGORY{" +
                "catid=" + catid +
                ", catname='" + catname + '\'' +
                ", catslug='" + catslug + '\'' +
                ", description='" + description + '\'' +
                ", catthumb='" + catthumb + '\'' +
                ", bannerurl=" + bannerurl +
                '}';
    }
}