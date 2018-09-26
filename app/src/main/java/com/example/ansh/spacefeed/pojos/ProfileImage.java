package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImage implements Parcelable{

    //------------------ private member variables --------------//
    @SerializedName("small")
    @Expose
    private String mSmall;

    @SerializedName("medium")
    @Expose
    private String mMedium;

    @SerializedName("large")
    @Expose
    private String mLarge;





    //------------------ Constructors --------------//
    public ProfileImage(String small, String medium, String large) {
        this.mSmall = small;
        this.mMedium = medium;
        this.mLarge = large;
    }

    // default Constructor
    public ProfileImage() {

    }




    //------------------ Getters & Setters --------------//
    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        this.mSmall = small;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        this.mMedium = medium;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
        this.mLarge = large;
    }




    //------------------ Parcelable METHODS --------------//
    public static final Creator<ProfileImage> CREATOR = new Creator<ProfileImage>() {
        @Override
        public ProfileImage createFromParcel(Parcel in) {
            return new ProfileImage(in);
        }

        @Override
        public ProfileImage[] newArray(int size) {
            return new ProfileImage[size];
        }
    };

    // Parcel Constructor
    protected ProfileImage(Parcel in) {
        this.mSmall = ((String) in.readValue((String.class.getClassLoader())));
        this.mMedium = ((String) in.readValue((String.class.getClassLoader())));
        this.mLarge = ((String) in.readValue((String.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mSmall);
        dest.writeValue(mMedium);
        dest.writeValue(mLarge);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
