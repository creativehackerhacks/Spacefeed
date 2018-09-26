package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("self")
    @Expose
    private String mSelf;

    @SerializedName("html")
    @Expose
    private String mHtml;

    @SerializedName("download")
    @Expose
    private String mDownload;

    @SerializedName("download_location")
    @Expose
    private String mDownloadLocation;




    //------------------ Constructors --------------//
    public Links(String self, String html, String download, String downloadLocation) {
        mSelf = self;
        mHtml = html;
        mDownload = download;
        mDownloadLocation = downloadLocation;
    }

    // default Constructor
    public Links() {

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

    public String getDownload() {
        return mDownload;
    }

    public void setDownload(String download) {
        mDownload = download;
    }

    public String getDownloadLocation() {
        return mDownloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        mDownloadLocation = downloadLocation;
    }





    //------------------ Parcelable METHODS --------------//
    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel parcel) {
            return new Links(parcel);
        }

        @Override
        public Links[] newArray(int size) {
            return (new Links[size]);
        }
    };

    // Parcel Constructor
    protected Links(Parcel in) {
        this.mSelf = ((String) in.readValue((String.class.getClassLoader())));
        this.mHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.mDownload = ((String) in.readValue((String.class.getClassLoader())));
        this.mDownloadLocation = ((String) in.readValue((String.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mSelf);
        dest.writeValue(mHtml);
        dest.writeValue(mDownload);
        dest.writeValue(mDownloadLocation );
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
