package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ implements Parcelable{

    //------------------ private member variables --------------//
    @SerializedName("self")
    @Expose
    private String mSelf;

    @SerializedName("html")
    @Expose
    private String mHtml;

    @SerializedName("photos")
    @Expose
    private String mPhotos;

    @SerializedName("likes")
    @Expose
    private String mLikes;

    @SerializedName("portfolio")
    @Expose
    private String mPortfolio;

    @SerializedName("following")
    @Expose
    private String mFollowing;

    @SerializedName("followers")
    @Expose
    private String mFollowers;





    //------------------ Constructors --------------//
    public Links_(String self, String html, String photos, String likes, String portfolio, String following, String followers) {
        mSelf = self;
        mHtml = html;
        mPhotos = photos;
        mLikes = likes;
        mPortfolio = portfolio;
        mFollowing = following;
        mFollowers = followers;
    }

    // default Constructor
    public Links_() {

    }




    //------------------ Getters & Setters --------------//
    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        mSelf = self;
    }

    public String getHtml() {
        return mHtml;
    }

    public void setHtml(String html) {
        mHtml = html;
    }

    public String getPhotos() {
        return mPhotos;
    }

    public void setPhotos(String photos) {
        mPhotos = photos;
    }

    public String getLikes() {
        return mLikes;
    }

    public void setLikes(String likes) {
        mLikes = likes;
    }

    public String getPortfolio() {
        return mPortfolio;
    }

    public void setPortfolio(String portfolio) {
        mPortfolio = portfolio;
    }

    public String getFollowing() {
        return mFollowing;
    }

    public void setFollowing(String following) {
        mFollowing = following;
    }

    public String getFollowers() {
        return mFollowers;
    }

    public void setFollowers(String followers) {
        mFollowers = followers;
    }





    //------------------ Parcelable METHODS --------------//
    public static final Creator<Links_> CREATOR = new Creator<Links_>() {
        @Override
        public Links_ createFromParcel(Parcel in) {
            return new Links_(in);
        }

        @Override
        public Links_[] newArray(int size) {
            return new Links_[size];
        }
    };

    // Parcel Constructor
    protected Links_(Parcel in) {
        this.mSelf = ((String) in.readValue((String.class.getClassLoader())));
        this.mHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.mPhotos = ((String) in.readValue((String.class.getClassLoader())));
        this.mLikes = ((String) in.readValue((String.class.getClassLoader())));
        this.mPortfolio = ((String) in.readValue((String.class.getClassLoader())));
        this.mFollowing = ((String) in.readValue((String.class.getClassLoader())));
        this.mFollowers = ((String) in.readValue((String.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mSelf);
        dest.writeValue(mHtml);
        dest.writeValue(mPhotos);
        dest.writeValue(mLikes);
        dest.writeValue(mPortfolio);
        dest.writeValue(mFollowing);
        dest.writeValue(mFollowers);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
