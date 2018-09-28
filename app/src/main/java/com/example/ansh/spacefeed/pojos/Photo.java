package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("created_at")
    @Expose
    private String mCreatedAt;

    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;

    @SerializedName("width")
    @Expose
    private Integer mWidth;

    @SerializedName("height")
    @Expose
    private Integer mHeight;

    @SerializedName("color")
    @Expose
    private String mColor;

    @SerializedName("description")
    @Expose
    private Object mDescription;

    @SerializedName("urls")
    @Expose
    private Urls mUrls;

    @SerializedName("links")
    @Expose
    private PhotoLinks mPhotoLinks;

    @SerializedName("categories")
    @Expose
    private List<Object> mCategories = null;

    @SerializedName("sponsored")
    @Expose
    private Boolean mSponsored;

    @SerializedName("likes")
    @Expose
    private Integer mLikes;

    @SerializedName("liked_by_user")
    @Expose
    private Boolean mLikedByUser;

    @SerializedName("current_user_collections")
    @Expose
    private List<Object> mCurrentUserCollections = null;

    @SerializedName("slug")
    @Expose
    private Object mSlug;

    @SerializedName("user")
    @Expose
    private User mUser;





    //------------------ Constructors --------------//
    public Photo(String id, String createdAt, String updatedAt, Integer width, Integer height, String color, Object description, Urls urls, PhotoLinks photoLinks, List<Object> categories, Boolean sponsored, Integer likes, Boolean likedByUser, List<Object> currentUserCollections, Object slug, User user) {
        mId = id;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
        mWidth = width;
        mHeight = height;
        mColor = color;
        mDescription = description;
        mUrls = urls;
        mPhotoLinks = photoLinks;
        mCategories = categories;
        mSponsored = sponsored;
        mLikes = likes;
        mLikedByUser = likedByUser;
        mCurrentUserCollections = currentUserCollections;
        mSlug = slug;
        mUser = user;
    }

    // Default Constructor
    public Photo() {

    }





    //------------------ Getters & Setters --------------//
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Integer getWidth() {
        return mWidth;
    }

    public void setWidth(Integer width) {
        mWidth = width;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public void setHeight(Integer height) {
        mHeight = height;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

    public Urls getUrls() {
        return mUrls;
    }

    public void setUrls(Urls urls) {
        mUrls = urls;
    }

    public PhotoLinks getPhotoLinks() {
        return mPhotoLinks;
    }

    public void setPhotoLinks(PhotoLinks photoLinks) {
        mPhotoLinks = photoLinks;
    }

    public List<Object> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Object> categories) {
        mCategories = categories;
    }

    public Boolean getSponsored() {
        return mSponsored;
    }

    public void setSponsored(Boolean sponsored) {
        mSponsored = sponsored;
    }

    public Integer getLikes() {
        return mLikes;
    }

    public void setLikes(Integer likes) {
        mLikes = likes;
    }

    public Boolean getLikedByUser() {
        return mLikedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        mLikedByUser = likedByUser;
    }

    public List<Object> getCurrentUserCollections() {
        return mCurrentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        mCurrentUserCollections = currentUserCollections;
    }

    public Object getSlug() {
        return mSlug;
    }

    public void setSlug(Object slug) {
        mSlug = slug;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }





    //------------------ Overridden equals() method for adapter class --------------//
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        Photo photo = (Photo) obj;
        return photo.getId() == this.getId() && photo.getUser().getName() == this.getUser().getName();
    }




    //------------------ Parcelable METHODS --------------//
    public static final Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel parcel) {
            return new Photo(parcel);
        }

        @Override
        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }
    };

    // Parcel constructor
    protected Photo(Parcel in) {
        this.mId = ((String) in.readValue((String.class.getClassLoader())));
        this.mCreatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.mUpdatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.mWidth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mHeight = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mColor = ((String) in.readValue((String.class.getClassLoader())));
        this.mDescription = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mUrls = ((Urls) in.readValue((Urls.class.getClassLoader())));
        this.mPhotoLinks = ((PhotoLinks) in.readValue((PhotoLinks.class.getClassLoader())));
        in.readList(this.mCategories, (Object.class.getClassLoader()));
        this.mSponsored = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.mLikes = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mLikedByUser = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.mCurrentUserCollections, (Object.class.getClassLoader()));
        this.mSlug = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mUser = ((User) in.readValue((User.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(mId);
        dest.writeValue(mCreatedAt);
        dest.writeValue(mUpdatedAt);
        dest.writeValue(mWidth);
        dest.writeValue(mHeight);
        dest.writeValue(mColor);
        dest.writeValue(mDescription);
        dest.writeValue(mUrls);
        dest.writeValue(mPhotoLinks);
        dest.writeList(mCategories);
        dest.writeValue(mSponsored);
        dest.writeValue(mLikes);
        dest.writeValue(mLikedByUser);
        dest.writeList(mCurrentUserCollections);
        dest.writeValue(mSlug);
        dest.writeValue(mUser);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
