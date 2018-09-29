package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionTag implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("title")
    @Expose
    private String mTitle;



    //------------------ Constructors --------------//
    public CollectionTag(String title) {
        mTitle = title;
    }

    // Default Constructor
    public CollectionTag() {

    }



    //------------------ Getters & Setters --------------//
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }



    //------------------ Parcelable METHODS --------------//
    public static final Creator<CollectionTag> CREATOR = new Creator<CollectionTag>() {
        @Override
        public CollectionTag createFromParcel(Parcel in) {
            return new CollectionTag(in);
        }

        @Override
        public CollectionTag[] newArray(int size) {
            return new CollectionTag[size];
        }
    };

    // Parcel Constructor
    protected CollectionTag(Parcel in) {
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
