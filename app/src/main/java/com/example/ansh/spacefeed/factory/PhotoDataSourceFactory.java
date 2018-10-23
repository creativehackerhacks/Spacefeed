package com.example.ansh.spacefeed.factory;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.concurrent.Executor;

public class PhotoDataSourceFactory extends DataSource.Factory<Integer, Photo> {

    // Create the mutable live data
    public MutableLiveData<PhotoDataSource> mPhotoLiveDataSource;
    private PhotoDataSource mPhotoDataSource;
    private Executor mExecutor;
    private String mPhotoSortOrder;

    // Constructor
    public PhotoDataSourceFactory(Executor executor) {
        this.mExecutor = executor;
        this.mPhotoLiveDataSource = new MutableLiveData();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
         mPhotoDataSource = new PhotoDataSource(mExecutor, mPhotoSortOrder);

        // posting the datasource to get the values
        mPhotoLiveDataSource.postValue(mPhotoDataSource);

        // return the datasource
        return mPhotoDataSource;
    }

    public void setPhotoSortOrder(String photoSortOrder) {
        mPhotoSortOrder = photoSortOrder;
    }

    // getter for mPhotoLiveDataSource


    public MutableLiveData<PhotoDataSource> getPhotoLiveDataSource() {
        return mPhotoLiveDataSource;
    }

    public PhotoDataSource getPhotoDataSource() {
        return mPhotoDataSource;
    }
}
