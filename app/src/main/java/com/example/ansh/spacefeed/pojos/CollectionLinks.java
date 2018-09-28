package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionLinks implements Parcelable {

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

    @SerializedName("related")
    @Expose
    private String mRelated;



    //------------------ Constructors --------------//
    public CollectionLinks(String self, String html, String photos, String related) {
        mSelf = self;
        mHtml = html;
        mPhotos = photos;
        mRelated = related;
    }

    // Default Constructor
    public CollectionLinks() {

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

    public String getRelated() {
        return mRelated;
    }

    public void setRelated(String related) {
        mRelated = related;
    }



    //------------------ Parcelable METHODS --------------//
    public static final Creator<CollectionLinks> CREATOR = new Creator<CollectionLinks>() {
        @Override
        public CollectionLinks createFromParcel(Parcel in) {
            return new CollectionLinks(in);
        }

        @Override
        public CollectionLinks[] newArray(int size) {
            return new CollectionLinks[size];
        }
    };

    // Parcel Constructor
    protected CollectionLinks(Parcel in) {
        mSelf = in.readString();
        mHtml = in.readString();
        mPhotos = in.readString();
        mRelated = in.readString();
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSelf);
        dest.writeString(mHtml);
        dest.writeString(mPhotos);
        dest.writeString(mRelated);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
