package com.gaming_resourcesbd.gamingresources.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_CONTACT_POLICY {

    @SerializedName("page_title")
    @Expose
    private String pageTitle;
    @SerializedName("page_url")
    @Expose
    private String pageUrl;
    @SerializedName("page_content")
    @Expose
    private String pageContent;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    @Override
    public String toString() {
        return "GET_CONTACT_POLICY{" +
                "pageTitle='" + pageTitle + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", pageContent='" + pageContent + '\'' +
                '}';
    }
}