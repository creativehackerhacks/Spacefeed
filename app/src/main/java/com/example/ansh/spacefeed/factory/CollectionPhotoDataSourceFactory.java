package com.example.ansh.spacefeed.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.ansh.spacefeed.dataSource.CollectionPhotoDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

public class CollectionPhotoDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<CollectionPhotoDataSource> mCollectionPhotoLiveData;
    private CollectionPhotoDataSource mCollectionPhotoDataSource;
    private int mCollectionId;

    public CollectionPhotoDataSourceFactory() {
        mCollectionPhotoLiveData = new MutableLiveData<CollectionPhotoDataSource>();
    }

    @Override
    public DataSource create() {
        mCollectionPhotoDataSource = new CollectionPhotoDataSource(mCollectionId);

        mCollectionPhotoLiveData.postValue(mCollectionPhotoDataSource);

        return mCollectionPhotoDataSource;
    }

    public void setCollectionId(int collectionId) {
        mCollectionId = collectionId;
    }

}
