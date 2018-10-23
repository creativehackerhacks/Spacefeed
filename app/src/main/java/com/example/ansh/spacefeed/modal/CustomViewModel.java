package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.content.ClipData.Item;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.factory.CollectionDataSourceFactory;
import com.example.ansh.spacefeed.factory.CollectionPhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.PhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.TrendingDataSourceFactory;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class CustomViewModel extends ViewModel {

    public LiveData<NetworkState> mNetworkStateLiveData;
    private Executor mExecutor;

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
        this.mExecutor = Executors.newFixedThreadPool(5);

        //getting our data source factory
        mCollectionsDataSourceFactory = new CollectionDataSourceFactory();
        mPhotoDataSourceFactory = new PhotoDataSourceFactory(mExecutor);
        mTrendingDataSourceFactory = new TrendingDataSourceFactory();
        mCollectionPhotoDataSourceFactory = new CollectionPhotoDataSourceFactory();

        mNetworkStateLiveData = Transformations.switchMap(
                mPhotoDataSourceFactory.getPhotoLiveDataSource(),
                dataSource -> mPhotoDataSourceFactory.getPhotoDataSource().getNetworkState());

        //Getting PagedList config
        PagedList.Config photoListConfig =
                (new PagedList.Config.Builder())
                        .setInitialLoadSizeHint(30)
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        PagedList.Config collectionListConfig =
                (new PagedList.Config.Builder())
                        .setInitialLoadSizeHint(20)
//                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        PagedList.Config collectionPhotoListConfig =
                (new PagedList.Config.Builder())
//                        .setInitialLoadSizeHint(10)
//                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        //Building the paged list
        mPhotoPagedList = (new LivePagedListBuilder(mPhotoDataSourceFactory, photoListConfig))
                .setFetchExecutor(mExecutor)
                .build();
        mTrendingPagedList = (new LivePagedListBuilder(mTrendingDataSourceFactory, photoListConfig))
                .build();
        mCollectionsPagedList = (new LivePagedListBuilder(mCollectionsDataSourceFactory, collectionListConfig))
                .build();
        mCollectionPhotoPagedList = (new LivePagedListBuilder(mCollectionPhotoDataSourceFactory, collectionPhotoListConfig))
                .build();
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
