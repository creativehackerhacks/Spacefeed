package com.example.ansh.spacefeed.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnSplashResponse {

    @SerializedName("urls")
    @Expose
    private Urls mUrls;

    @SerializedName("likes")
    @Expose
    private Integer mNumOfLikes;

    @SerializedName("user")
    @Expose
    private User mUser;

    @SerializedName("id")
    @Expose
    private String mId;

    public UnSplashResponse(Urls urls, Integer numOfLikes, User user, String id) {
        mUrls = urls;
        mNumOfLikes = numOfLikes;
        mUser = user;
        mId = id;
    }

    public UnSplashResponse() {

    }

    public Urls getUrls() {
        return mUrls;
    }

    public void setUrls(Urls urls) {
        mUrls = urls;
    }

    public Integer getNumOfLikes() {
        return mNumOfLikes;
    }

    public void setNumOfLikes(Integer numOfLikes) {
        mNumOfLikes = numOfLikes;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        UnSplashResponse unSplashResponse = (UnSplashResponse) obj;
        return unSplashResponse.getId() == this.getId() && unSplashResponse.getUser().getName() == this.getUser().getName();
    }

}
