package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviewPhoto implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("urls")
    @Expose
    private Urls mUrls;



    //------------------ Constructors --------------//
    public PreviewPhoto(Integer id, Urls urls) {
        mId = id;
        mUrls = urls;
    }

    // Default Constructor
    public PreviewPhoto() {
    }



    //------------------ Getters & Setters --------------//
    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Urls getUrls() {
        return mUrls;
    }

    public void setUrls(Urls urls) {
        mUrls = urls;
    }



    //------------------ Parcelable METHODS --------------//
    public static final Creator<PreviewPhoto> CREATOR = new Creator<PreviewPhoto>() {
        @Override
        public PreviewPhoto createFromParcel(Parcel in) {
            return new PreviewPhoto(in);
        }

        @Override
        public PreviewPhoto[] newArray(int size) {
            return new PreviewPhoto[size];
        }
    };

    // Parcel Constructor
    protected PreviewPhoto(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mUrls = in.readParcelable(Urls.class.getClassLoader());
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mId);
        }
        dest.writeParcelable(mUrls, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
