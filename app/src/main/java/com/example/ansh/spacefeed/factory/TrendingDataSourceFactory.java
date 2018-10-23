package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.dataSource.TrendingDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

public class TrendingDataSourceFactory extends DataSource.Factory {

    // Create the mutable live data
    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> mTrendingLiveDataSource;
    private TrendingDataSource mTrendingDataSource;

    // Constructor
    public TrendingDataSourceFactory() {
        mTrendingLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
        mTrendingDataSource = new TrendingDataSource();

        // posting the datasource to get the values
        mTrendingLiveDataSource.postValue(mTrendingDataSource);

        // return the datasource
        return mTrendingDataSource;
    }

    // getter for mPhotoLiveDataSource
//    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> getPhotoLiveDataSource() {
//        return mPhotoLiveDataSource;
//    }

}