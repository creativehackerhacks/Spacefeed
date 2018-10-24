package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.dataSource.TrendingDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.concurrent.Executor;

public class TrendingDataSourceFactory extends DataSource.Factory<Integer, Photo> {

    // Create the mutable live data
    public MutableLiveData<TrendingDataSource> mTrendingLiveDataSource;
    private TrendingDataSource mTrendingDataSource;
    private Executor mExecutor;
    private String mTrendingsSortOrder;

    // Constructor
    public TrendingDataSourceFactory(Executor executor) {
        this.mExecutor = executor;
        this.mTrendingLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
        mTrendingDataSource = new TrendingDataSource(mExecutor, mTrendingsSortOrder);

        // posting the datasource to get the values
        mTrendingLiveDataSource.postValue(mTrendingDataSource);

        // return the datasource
        return mTrendingDataSource;
    }

    // getter for mPhotoLiveDataSource
    public MutableLiveData<TrendingDataSource> getTrendingLiveDataSource() {
        return mTrendingLiveDataSource;
    }

    public TrendingDataSource getTrendingDataSource() {
        return mTrendingDataSource;
    }
    public void setTrendingsSortOrder(String trendingsSortOrder) {
        mTrendingsSortOrder = trendingsSortOrder;
    }


}