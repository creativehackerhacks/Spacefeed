package com.example.ansh.spacefeed.factory;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

public class PhotoDataSourceFactory extends DataSource.Factory {

    // Create the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Photo>> mPhotoLiveDataSource;

    // Constructor
    public PhotoDataSourceFactory() {
        mPhotoLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
        PhotoDataSource photoDataSource = new PhotoDataSource();

        // posting the datasource to get the values
        mPhotoLiveDataSource.postValue(photoDataSource);

        // return the datasource
        return photoDataSource;
    }

    // getter for mPhotoLiveDataSource
//    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> getPhotoLiveDataSource() {
//        return mPhotoLiveDataSource;
//    }

}
