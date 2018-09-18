package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls implements Parcelable {

    @SerializedName("full")
    @Expose
    private String mFullUrl;

    public Urls(String fullUrl) {
        mFullUrl = fullUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String fullUrl) {
        mFullUrl = fullUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mFullUrl);
    }

    protected Urls(Parcel in) {
        this.mFullUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public final static Parcelable.Creator<Urls> CREATOR = new Creator<Urls>() {

        @SuppressWarnings({"unchecked"})
        public Urls createFromParcel(Parcel in) {
            return new Urls(in);
        }

        public Urls[] newArray(int size) {
            return (new Urls[size]);
        }
    };


}
