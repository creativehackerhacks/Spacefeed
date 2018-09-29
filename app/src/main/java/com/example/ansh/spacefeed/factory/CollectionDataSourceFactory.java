package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

public class CollectionDataSourceFactory extends DataSource.Factory {

    // Create the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, CollectionPhoto>> mCollectionLiveDataSource;

    // Constructor
    public CollectionDataSourceFactory() {
        mCollectionLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        CollectionDataSource collectionDataSource = new CollectionDataSource();

        mCollectionLiveDataSource.postValue(collectionDataSource);

        return collectionDataSource;
    }



}
