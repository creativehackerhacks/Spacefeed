package com.example.ansh.spacefeed.factory;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

public class PhotoDataSourceFactory extends DataSource.Factory {

    // Create the mutable live data
    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> mPhotoLiveDataSource;
    private PhotoDataSource mPhotoDataSource;
    private String mPhotoSortOrder;

    // Constructor
    public PhotoDataSourceFactory() {
        mPhotoLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
         mPhotoDataSource = new PhotoDataSource(mPhotoSortOrder);

        // posting the datasource to get the values
        mPhotoLiveDataSource.postValue(mPhotoDataSource);

        // return the datasource
        return mPhotoDataSource;
    }

    public void setPhotoSortOrder(String photoSortOrder) {
        mPhotoSortOrder = photoSortOrder;
    }

    // getter for mPhotoLiveDataSource
//    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> getPhotoLiveDataSource() {
//        return mPhotoLiveDataSource;
//    }

}
