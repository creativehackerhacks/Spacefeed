package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnSplashResponse implements Parcelable {

    @SerializedName("urls")
    @Expose
    private Urls mUrls;

    @SerializedName("likes")
    @Expose
    private Integer mNumOfLikes;

    @SerializedName("user")
    @Expose
    private User mUser;

    public UnSplashResponse(Urls urls, Integer numOfLikes, User user) {
        mUrls = urls;
        mNumOfLikes = numOfLikes;
        mUser = user;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mUrls);
        dest.writeValue(mNumOfLikes);
        dest.writeValue(mUser);
    }

    protected UnSplashResponse(Parcel in) {
        this.mUrls = ((Urls) in.readValue((Urls.class.getClassLoader())));
        this.mNumOfLikes = (Integer) in.readValue((Integer.class.getClassLoader()));
        this.mUser = (User) in.readValue((User.class.getClassLoader()));
    }

    public final static Parcelable.Creator<UnSplashResponse> CREATOR = new Creator<UnSplashResponse>() {

        @SuppressWarnings({"unchecked"})
        public UnSplashResponse createFromParcel(Parcel in) {
            return new UnSplashResponse(in);
        }

        public UnSplashResponse[] newArray(int size) {
            return (new UnSplashResponse[size]);
        }
    };

}
