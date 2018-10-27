package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.ansh.spacefeed.dataSource.UserLikesDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.concurrent.Executor;

public class UserLikesDataSourceFactory extends DataSource.Factory<Integer, Photo> {

    // Create the mutable live data
    private MutableLiveData<UserLikesDataSource> mUserLikesDataSourceMutableLiveData;
    private UserLikesDataSource mUserLikesDataSource;
    private Executor mExecutor;
    private String mUsername, mUserLikesSortOrder;

    // Constructor
    public UserLikesDataSourceFactory(Executor executor) {
        this.mExecutor = executor;
        this.mUserLikesDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        mUserLikesDataSource = new UserLikesDataSource(mExecutor, mUsername, mUserLikesSortOrder);

        mUserLikesDataSourceMutableLiveData.postValue(mUserLikesDataSource);

        return mUserLikesDataSource;
    }

    public void setUserOptions(String username, String userLikesSortOrder) {
        mUsername = username;
        mUserLikesSortOrder = userLikesSortOrder;
    }

    public MutableLiveData<UserLikesDataSource> getUserLikesDataSourceMutableLiveData() {
        return mUserLikesDataSourceMutableLiveData;
    }

    public UserLikesDataSource getUserLikesDataSource() {
        return mUserLikesDataSource;
    }

}