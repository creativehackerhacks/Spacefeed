package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.ansh.spacefeed.dataSource.UserCollectionsDataSource;
import com.example.ansh.spacefeed.dataSource.UserPhotosDataSource;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.concurrent.Executor;

public class UserCollectionsDataSourceFactory extends DataSource.Factory<Integer, CollectionPhoto> {

    // Create the mutable live data
    private MutableLiveData<UserCollectionsDataSource> mUserCollectionsDataSourceMutableLiveData;
    private UserCollectionsDataSource mUserCollectionsDataSource;
    private Executor mExecutor;
    private String mUsername;

    public UserCollectionsDataSourceFactory(Executor executor) {
        mExecutor = executor;
        this.mUserCollectionsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, CollectionPhoto> create() {
        mUserCollectionsDataSource = new UserCollectionsDataSource(mExecutor, mUsername);

        mUserCollectionsDataSourceMutableLiveData.postValue(mUserCollectionsDataSource);

        return mUserCollectionsDataSource;
    }

    public void setUserOptions(String username) {
        mUsername = username;
    }

    public MutableLiveData<UserCollectionsDataSource> getUserCollectionsDataSourceMutableLiveData() {
        return mUserCollectionsDataSourceMutableLiveData;
    }

    public UserCollectionsDataSource getUserCollectionsDataSource() {
        return mUserCollectionsDataSource;
    }

}
