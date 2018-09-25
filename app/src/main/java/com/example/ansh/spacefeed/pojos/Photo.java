package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

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

    @SerializedName("color")
    @Expose
    private String mColor;

    public Photo(Urls urls, Integer numOfLikes, User user, String id, String color) {
        mUrls = urls;
        mNumOfLikes = numOfLikes;
        mUser = user;
        mId = id;
        mColor = color;
    }

    public Photo() {

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

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        Photo photo = (Photo) obj;
        return photo.getId() == this.getId() && photo.getUser().getName() == this.getUser().getName();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel parcel) {
            return new Photo(parcel);
        }

        @Override
        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }
    };

    protected Photo(Parcel parcel) {
        this.mUrls = (Urls) parcel.readValue(Urls.class.getClassLoader());
        this.mNumOfLikes = (Integer) parcel.readValue(Integer.class.getClassLoader());
        this.mUser = (User) parcel.readValue(User.class.getClassLoader());
        this.mId = (String) parcel.readValue(String.class.getClassLoader());
        this.mColor = (String) parcel.readValue(String.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(mUrls);
        parcel.writeValue(mNumOfLikes);
        parcel.writeValue(mUser);
        parcel.writeValue(mId);
        parcel.writeValue(mColor);
    }

}
