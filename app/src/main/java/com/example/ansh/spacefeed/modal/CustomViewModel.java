package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.factory.CollectionDataSourceFactory;
import com.example.ansh.spacefeed.factory.CollectionPhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.PhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.TrendingDataSourceFactory;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

public class CustomViewModel extends ViewModel {

    // For Collections.
    public LiveData<PagedList<CollectionPhoto>> mCollectionsPagedList;
    private CollectionDataSourceFactory mCollectionsDataSourceFactory;

    // For Photos.
    public LiveData<PagedList<Photo>> mPhotoPagedList;
    private PhotoDataSourceFactory mPhotoDataSourceFactory;

    // For Trending
    public LiveData<PagedList<Photo>> mTrendingPagedList;
    private TrendingDataSourceFactory mTrendingDataSourceFactory;

    // For CollectionPhotos.
    public LiveData<PagedList<Photo>> mCollectionPhotoPagedList;
    private CollectionPhotoDataSourceFactory mCollectionPhotoDataSourceFactory;

    //constructor
    public CustomViewModel() {
        //getting our data source factory
        mCollectionsDataSourceFactory = new CollectionDataSourceFactory();
        mPhotoDataSourceFactory = new PhotoDataSourceFactory();
        mTrendingDataSourceFactory = new TrendingDataSourceFactory();
        mCollectionPhotoDataSourceFactory = new CollectionPhotoDataSourceFactory();

        //getting the live data source from data source factory
//        mPhotoLiveDataSource = collectionPhotoDataSourceFactory.getPhotoLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(CollectionDataSource.ITEM_PER_PAGE).build();

        //Building the paged list
        mCollectionsPagedList = (new LivePagedListBuilder(mCollectionsDataSourceFactory, pagedListConfig))
                .build();

        mPhotoPagedList = (new LivePagedListBuilder(mPhotoDataSourceFactory, pagedListConfig))
                .build();

        mTrendingPagedList = (new LivePagedListBuilder(mTrendingDataSourceFactory, pagedListConfig))
                .build();

        mCollectionPhotoPagedList = (new LivePagedListBuilder(mCollectionPhotoDataSourceFactory, pagedListConfig)).build();
    }

    public LiveData<PagedList<CollectionPhoto>> getCollectionsPagedList() {
        return mCollectionsPagedList;
    }

    public LiveData<PagedList<Photo>> getPhotoPagedList() {
        return mPhotoPagedList;
    }

    public LiveData<PagedList<Photo>> getTrendingPagedList() {
        return mTrendingPagedList;
    }

    public void setPhotoSortOrder(String photoSortOrder) {
        mPhotoDataSourceFactory.setPhotoSortOrder(photoSortOrder);
    }

    public void setCollectionId(int collectionId) {
        mCollectionPhotoDataSourceFactory.setCollectionId(collectionId);
    }

    public void onPhotoDataSourceRefresh() {
        mPhotoDataSourceFactory.mPhotoLiveDataSource.getValue().invalidate();
    }

    public void onTrendingDataSourceRefresh() {
        mTrendingDataSourceFactory.mTrendingLiveDataSource.getValue().invalidate();
    }

}
