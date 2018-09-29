package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionPhoto implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("id")
    @Expose
    private Integer mId;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("description")
    @Expose
    private Object mDescription;

    @SerializedName("published_at")
    @Expose
    private String mPublishedAt;
    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;

    @SerializedName("curated")
    @Expose
    private Boolean mCurated;

    @SerializedName("featured")
    @Expose
    private Boolean mFeatured;

    @SerializedName("total_photos")
    @Expose
    private Integer mTotalPhotos;

    @SerializedName("private")
    @Expose
    private Boolean mPrivate;
    @SerializedName("share_key")
    @Expose
    private String mShareKey;

    @SerializedName("tags")
    @Expose
    private List<CollectionTag> mCollectionTags = null;

    @SerializedName("cover_photo")
    @Expose
    private Photo mCoverPhoto;

    @SerializedName("preview_photos")
    @Expose
    private List<PreviewPhoto> mPreviewPhotos = null;

    @SerializedName("user")
    @Expose
    private User mUser;

    @SerializedName("links")
    @Expose
    private CollectionLinks mLinks;



    //------------------ Constructors --------------//
    public CollectionPhoto(Integer id, String title, Object description, String publishedAt, String updatedAt, Boolean curated, Boolean featured, Integer totalPhotos, Boolean aPrivate, String shareKey, List<CollectionTag> collectionTags, Photo coverPhoto, List<PreviewPhoto> previewPhotos, User user, CollectionLinks links) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mPublishedAt = publishedAt;
        mUpdatedAt = updatedAt;
        mCurated = curated;
        mFeatured = featured;
        mTotalPhotos = totalPhotos;
        mPrivate = aPrivate;
        mShareKey = shareKey;
        mCollectionTags = collectionTags;
        mCoverPhoto = coverPhoto;
        mPreviewPhotos = previewPhotos;
        mUser = user;
        mLinks = links;
    }

    // Default Constructor
    public CollectionPhoto() {

    }


    //------------------ Getters & Setters --------------//
    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Boolean getCurated() {
        return mCurated;
    }

    public void setCurated(Boolean curated) {
        mCurated = curated;
    }

    public Boolean getFeatured() {
        return mFeatured;
    }

    public void setFeatured(Boolean featured) {
        mFeatured = featured;
    }

    public Integer getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        mTotalPhotos = totalPhotos;
    }

    public Boolean getPrivate() {
        return mPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        mPrivate = aPrivate;
    }

    public String getShareKey() {
        return mShareKey;
    }

    public void setShareKey(String shareKey) {
        mShareKey = shareKey;
    }

    public List<CollectionTag> getCollectionTags() {
        return mCollectionTags;
    }

    public void setCollectionTags(List<CollectionTag> collectionTags) {
        mCollectionTags = collectionTags;
    }

    public Photo getCoverPhoto() {
        return mCoverPhoto;
    }

    public void setCoverPhoto(Photo coverPhoto) {
        mCoverPhoto = coverPhoto;
    }

    public List<PreviewPhoto> getPreviewPhotos() {
        return mPreviewPhotos;
    }

    public void setPreviewPhotos(List<PreviewPhoto> previewPhotos) {
        mPreviewPhotos = previewPhotos;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public CollectionLinks getLinks() {
        return mLinks;
    }

    public void setLinks(CollectionLinks links) {
        mLinks = links;
    }


    //------------------ Parcelable METHODS --------------//
    public static final Creator<CollectionPhoto> CREATOR = new Creator<CollectionPhoto>() {
        @Override
        public CollectionPhoto createFromParcel(Parcel in) {
            return new CollectionPhoto(in);
        }

        @Override
        public CollectionPhoto[] newArray(int size) {
            return new CollectionPhoto[size];
        }
    };

    // Parcel constructor
    protected CollectionPhoto(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mTitle = in.readString();
        mPublishedAt = in.readString();
        mUpdatedAt = in.readString();
        byte tmpMCurated = in.readByte();
        mCurated = tmpMCurated == 0 ? null : tmpMCurated == 1;
        byte tmpMFeatured = in.readByte();
        mFeatured = tmpMFeatured == 0 ? null : tmpMFeatured == 1;
        if (in.readByte() == 0) {
            mTotalPhotos = null;
        } else {
            mTotalPhotos = in.readInt();
        }
        byte tmpMPrivate = in.readByte();
        mPrivate = tmpMPrivate == 0 ? null : tmpMPrivate == 1;
        mShareKey = in.readString();
        mCollectionTags = in.createTypedArrayList(CollectionTag.CREATOR);
        mCoverPhoto = in.readParcelable(Photo.class.getClassLoader());
        mUser = in.readParcelable(User.class.getClassLoader());
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
        dest.writeString(mTitle);
        dest.writeString(mPublishedAt);
        dest.writeString(mUpdatedAt);
        dest.writeByte((byte) (mCurated == null ? 0 : mCurated ? 1 : 2));
        dest.writeByte((byte) (mFeatured == null ? 0 : mFeatured ? 1 : 2));
        if (mTotalPhotos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mTotalPhotos);
        }
        dest.writeByte((byte) (mPrivate == null ? 0 : mPrivate ? 1 : 2));
        dest.writeString(mShareKey);
        dest.writeTypedList(mCollectionTags);
        dest.writeParcelable(mCoverPhoto, flags);
        dest.writeParcelable(mUser, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
