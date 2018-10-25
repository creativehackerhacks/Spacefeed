package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.dataSource.UserPhotosDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.concurrent.Executor;

public class UserPhotoDataSourceFactory extends DataSource.Factory<Integer, Photo> {

    // Create the mutable live data
    private MutableLiveData<UserPhotosDataSource> mUserPhotosDataSourceMutableLiveData;
    private UserPhotosDataSource mUserPhotosDataSource;
    private Executor mExecutor;
    private String mUsername, mUserPhotoSortOrder;

    // Constructor
    public UserPhotoDataSourceFactory(Executor executor) {
        this.mExecutor = executor;
        this.mUserPhotosDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        mUserPhotosDataSource = new UserPhotosDataSource(mExecutor, mUsername, mUserPhotoSortOrder);

        mUserPhotosDataSourceMutableLiveData.postValue(mUserPhotosDataSource);

        return mUserPhotosDataSource;
    }

    public void setUserOptions(String username, String userPhotoSortOrder) {
        mUsername = username;
        mUserPhotoSortOrder = userPhotoSortOrder;
    }

    public MutableLiveData<UserPhotosDataSource> getUserPhotosDataSourceMutableLiveData() {
        return mUserPhotosDataSourceMutableLiveData;
    }

    public UserPhotosDataSource getUserPhotosDataSource() {
        return mUserPhotosDataSource;
    }

}
