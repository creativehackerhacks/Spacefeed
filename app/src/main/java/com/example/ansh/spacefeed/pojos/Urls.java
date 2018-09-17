package com.example.ansh.spacefeed.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("full")
    @Expose
    private String mFullUrl;

    public Urls(String fullUrl) {
        mFullUrl = fullUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String fullUrl) {
        mFullUrl = fullUrl;
    }

}
