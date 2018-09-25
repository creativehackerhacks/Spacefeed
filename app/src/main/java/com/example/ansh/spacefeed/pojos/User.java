package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("bio")
    @Expose
    private String mBio;

    @SerializedName("location")
    @Expose
    private String mLocation;

    public User(String name, String bio, String location) {
        mName = name;
        mBio = bio;
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return (new User[size]);
        }
    };

    public User(Parcel parcel) {
        this.mName = (String) parcel.readValue(String.class.getClassLoader());
        this.mBio = (String) parcel.readValue(String.class.getClassLoader());
        this.mLocation = (String) parcel.readValue(String.class.getClassLoader());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(mName);
        parcel.writeValue(mBio);
        parcel.writeValue(mLocation);
    }

}
