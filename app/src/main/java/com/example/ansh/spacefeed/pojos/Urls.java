package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls implements Parcelable {

    //------------------ private member variables --------------//
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





    //------------------ Constructors --------------//
    public Urls(String rawUrl, String fullUrl, String regularUrl, String smallUrl, String thumbUrl) {
        mRawUrl = rawUrl;
        mFullUrl = fullUrl;
        mRegularUrl = regularUrl;
        mSmallUrl = smallUrl;
        mThumbUrl = thumbUrl;
    }

    // default Constructor
    public Urls() {

    }




    //------------------ Getters & Setters --------------//
    public String getRawUrl() {
        return mRawUrl;
    }

    public void setRawUrl(String rawUrl) {
        mRawUrl = rawUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String fullUrl) {
        mFullUrl = fullUrl;
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





    //------------------ Parcelable METHODS --------------//
    public static final Parcelable.Creator<Urls> CREATOR = new Creator<Urls>() {

        @SuppressWarnings({"unchecked"})
        public Urls createFromParcel(Parcel in) {
            return new Urls(in);
        }

        public Urls[] newArray(int size) {
            return (new Urls[size]);
        }
    };

    // Parcel Constructor
    protected Urls(Parcel in) {
        this.mRawUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mFullUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mRegularUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mSmallUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mThumbUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(mRawUrl);
        dest.writeValue(mFullUrl);
        dest.writeValue(mRegularUrl);
        dest.writeValue(mSmallUrl);
        dest.writeValue(mThumbUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
