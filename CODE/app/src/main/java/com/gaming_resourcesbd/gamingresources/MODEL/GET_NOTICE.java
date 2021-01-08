package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_NOTICE {

    @SerializedName("show_app_notice")
    @Expose
    private Boolean showAppNotice;
    @SerializedName("app_notice")
    @Expose
    private String appNotice;

    public Boolean getShowAppNotice() {
        return showAppNotice;
    }

    public void setShowAppNotice(Boolean showAppNotice) {
        this.showAppNotice = showAppNotice;
    }

    public String getAppNotice() {
        return appNotice;
    }

    public void setAppNotice(String appNotice) {
        this.appNotice = appNotice;
    }

    @Override
    public String toString() {
        return "GET_NOTICE{" +
                "showAppNotice=" + showAppNotice +
                ", appNotice='" + appNotice + '\'' +
                '}';
    }
}