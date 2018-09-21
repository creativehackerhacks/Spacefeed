package com.example.ansh.spacefeed.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("raw")
    @Expose
    private String mRawUrl;

    @SerializedName("full")
    @Expose
    private String mFullUrl;

    @SerializedName("regular")
    @Expose
    private String mRegularUrl;

    @SerializedName("small")
    @Expose
    private String mSmallUrl;

    @SerializedName("thumb")
    @Expose
    private String mThumbUrl;

    // Constructor

    public Urls(String rawUrl, String fullUrl, String regularUrl, String smallUrl, String thumbUrl) {
        mRawUrl = rawUrl;
        mFullUrl = fullUrl;
        mRegularUrl = regularUrl;
        mSmallUrl = smallUrl;
        mThumbUrl = thumbUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String fullUrl) {
        mFullUrl = fullUrl;
    }

    public String getRawUrl() {
        return mRawUrl;
    }

    public void setRawUrl(String rawUrl) {
        mRawUrl = rawUrl;
    }

    public String getRegularUrl() {
        return mRegularUrl;
    }

    public void setRegularUrl(String regularUrl) {
        mRegularUrl = regularUrl;
    }

    public String getSmallUrl() {
        return mSmallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        mSmallUrl = smallUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        mThumbUrl = thumbUrl;
    }
}
