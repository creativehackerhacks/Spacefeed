package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

import java.util.concurrent.Executor;

public class CollectionDataSourceFactory extends DataSource.Factory<Integer, CollectionPhoto> {

    // Create the mutable live data
    private MutableLiveData<CollectionDataSource> mCollectionLiveDataSource;
    private CollectionDataSource mCollectionDataSource;
    private Executor mExecutor;
    private String mCollectionSortOrder;

    // Constructor
    public CollectionDataSourceFactory(Executor executor) {
        this.mExecutor = executor;
        this.mCollectionLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, CollectionPhoto> create() {
        mCollectionDataSource = new CollectionDataSource(mExecutor, mCollectionSortOrder);

        mCollectionLiveDataSource.postValue(mCollectionDataSource);

        return mCollectionDataSource;
    }

    public void setCollectionSortOrder(String collectionSortOrder) {
        mCollectionSortOrder = collectionSortOrder;
    }

    public MutableLiveData<CollectionDataSource> getCollectionLiveDataSource() {
        return mCollectionLiveDataSource;
    }

    public CollectionDataSource getCollectionDataSource() {
        return mCollectionDataSource;
    }

}
