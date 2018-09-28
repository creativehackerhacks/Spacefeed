package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("title")
    @Expose
    private String mTitle;



    //------------------ Constructors --------------//
    public Tag(String title) {
        mTitle = title;
    }

    // Default Constructor
    public Tag() {

    }



    //------------------ Getters & Setters --------------//
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }



    //------------------ Parcelable METHODS --------------//
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    // Parcel Constructor
    protected Tag(Parcel in) {
        mTitle = in.readString();
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
