package com.example.ansh.spacefeed.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    //------------------ private member variables --------------//
    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;

    @SerializedName("username")
    @Expose
    private String mUsername;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("first_name")
    @Expose
    private String mFirstName;

    @SerializedName("last_name")
    @Expose
    private String mLastName;

    @SerializedName("twitter_username")
    @Expose
    private String mTwitterUsername;

    @SerializedName("portfolio_url")
    @Expose
    private String mPortfolioUrl;

    @SerializedName("bio")
    @Expose
    private String mBio;

    @SerializedName("location")
    @Expose
    private String mLocation;

    @SerializedName("links")
    @Expose
    private Links_ mLinks_;

    @SerializedName("profile_image")
    @Expose
    private ProfileImage mProfileImage;

    @SerializedName("instagram_username")
    @Expose
    private String mInstagramUsername;

    @SerializedName("total_collections")
    @Expose
    private Integer mTotalCollections;

    @SerializedName("total_likes")
    @Expose
    private Integer mTotalLikes;

    @SerializedName("total_photos")
    @Expose
    private Integer mTotalPhotos;




    //------------------ Constructors --------------//
    public User(String id, String updatedAt, String username, String name, String firstName, String lastName, String twitterUsername, String portfolioUrl, String bio, String location, Links_ links_, ProfileImage profileImage, String instagramUsername, Integer totalCollections, Integer totalLikes, Integer totalPhotos) {
        mId = id;
        mUpdatedAt = updatedAt;
        mUsername = username;
        mName = name;
        mFirstName = firstName;
        mLastName = lastName;
        mTwitterUsername = twitterUsername;
        mPortfolioUrl = portfolioUrl;
        mBio = bio;
        mLocation = location;
        mLinks_ = links_;
        mProfileImage = profileImage;
        mInstagramUsername = instagramUsername;
        mTotalCollections = totalCollections;
        mTotalLikes = totalLikes;
        mTotalPhotos = totalPhotos;
    }

    // default Constructor
    public User() {

    }



    //------------------ Getters & Setters --------------//
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getTwitterUsername() {
        return mTwitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        mTwitterUsername = twitterUsername;
    }

    public String getPortfolioUrl() {
        return mPortfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        mPortfolioUrl = portfolioUrl;
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

    public Links_ getLinks_() {
        return mLinks_;
    }

    public void setLinks_(Links_ links_) {
        mLinks_ = links_;
    }

    public ProfileImage getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        mProfileImage = profileImage;
    }

    public String getInstagramUsername() {
        return mInstagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        mInstagramUsername = instagramUsername;
    }

    public Integer getTotalCollections() {
        return mTotalCollections;
    }

    public void setTotalCollections(Integer totalCollections) {
        mTotalCollections = totalCollections;
    }

    public Integer getTotalLikes() {
        return mTotalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        mTotalLikes = totalLikes;
    }

    public Integer getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        mTotalPhotos = totalPhotos;
    }




    //------------------ Parcelable METHODS --------------//
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

    // Parcel Constructor
    public User(Parcel in) {
        this.mId = ((String) in.readValue((String.class.getClassLoader())));
        this.mUpdatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.mUsername = ((String) in.readValue((String.class.getClassLoader())));
        this.mName = ((String) in.readValue((String.class.getClassLoader())));
        this.mFirstName = ((String) in.readValue((String.class.getClassLoader())));
        this.mLastName = ((String) in.readValue((String.class.getClassLoader())));
        this.mTwitterUsername = ((String) in.readValue((String.class.getClassLoader())));
        this.mPortfolioUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.mBio = ((String) in.readValue((String.class.getClassLoader())));
        this.mLocation = ((String) in.readValue((String.class.getClassLoader())));
        this.mLinks_ = ((Links_) in.readValue((Links_.class.getClassLoader())));
        this.mProfileImage = ((ProfileImage) in.readValue((ProfileImage.class.getClassLoader())));
        this.mInstagramUsername = ((String) in.readValue((String.class.getClassLoader())));
        this.mTotalCollections = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mTotalLikes = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mTotalPhotos = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    // Parcel write
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(mId);
        dest.writeValue(mUpdatedAt);
        dest.writeValue(mUsername);
        dest.writeValue(mName);
        dest.writeValue(mFirstName);
        dest.writeValue(mLastName);
        dest.writeValue(mTwitterUsername);
        dest.writeValue(mPortfolioUrl);
        dest.writeValue(mBio);
        dest.writeValue(mLocation);
        dest.writeValue(mLinks_);
        dest.writeValue(mProfileImage);
        dest.writeValue(mInstagramUsername);
        dest.writeValue(mTotalCollections);
        dest.writeValue(mTotalLikes);
        dest.writeValue(mTotalPhotos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
