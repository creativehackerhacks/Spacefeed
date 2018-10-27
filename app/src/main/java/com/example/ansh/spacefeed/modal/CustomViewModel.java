package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.ansh.spacefeed.factory.CollectionDataSourceFactory;
import com.example.ansh.spacefeed.factory.CollectionPhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.PhotoDataSourceFactory;
import com.example.ansh.spacefeed.factory.TrendingDataSourceFactory;
import com.example.ansh.spacefeed.factory.UserCollectionsDataSourceFactory;
import com.example.ansh.spacefeed.factory.UserLikesDataSourceFactory;
import com.example.ansh.spacefeed.factory.UserPhotoDataSourceFactory;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.utils.NetworkState;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CustomViewModel extends ViewModel {

    public LiveData<NetworkState> mPhotoNetworkStateLiveData;
    public LiveData<NetworkState> mTrendingNetworkStateLiveData;
    public LiveData<NetworkState> mCollectionNetworkStateLiveData;
    public LiveData<NetworkState> mCollectionPhotoNetworkStateLiveData;
    public LiveData<NetworkState> mUserPhotoNetworkStateLiveData;
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

    // For UserPhoto
    public LiveData<PagedList<Photo>> mUserPhotoPagedList;
    private UserPhotoDataSourceFactory mUserPhotoDataSourceFactory;

    // For UserLikes
    public LiveData<PagedList<Photo>> mUserLikesPagedList;
    private UserLikesDataSourceFactory mUserLikesDataSourceFactory;

    //  For UserCollections
    public LiveData<PagedList<CollectionPhoto>> mUserCollectionsPagedList;
    private UserCollectionsDataSourceFactory mUserCollectionsDataSourceFactory;

    //constructor
    public CustomViewModel() {
        this.mExecutor = Executors.newFixedThreadPool(5);

        //getting our data source factory
        mCollectionsDataSourceFactory = new CollectionDataSourceFactory(mExecutor);
        mPhotoDataSourceFactory = new PhotoDataSourceFactory(mExecutor);
        mTrendingDataSourceFactory = new TrendingDataSourceFactory(mExecutor);
        mCollectionPhotoDataSourceFactory = new CollectionPhotoDataSourceFactory();
        mUserPhotoDataSourceFactory = new UserPhotoDataSourceFactory(mExecutor);
        mUserLikesDataSourceFactory = new UserLikesDataSourceFactory(mExecutor);
        mUserCollectionsDataSourceFactory = new UserCollectionsDataSourceFactory(mExecutor);

        mPhotoNetworkStateLiveData = Transformations.switchMap(
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
                        .setInitialLoadSizeHint(10)
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        PagedList.Config userPhotoListConfig =
                (new PagedList.Config.Builder())
                        .setInitialLoadSizeHint(30)
                        .setPrefetchDistance(10)
                        .setPageSize(10)
                        .build();

        //Building the paged list
        mPhotoPagedList = (new LivePagedListBuilder(mPhotoDataSourceFactory, photoListConfig))
                .setFetchExecutor(mExecutor)
                .build();
        mTrendingPagedList = (new LivePagedListBuilder(mTrendingDataSourceFactory, photoListConfig))
                .build();
        mCollectionsPagedList = (new LivePagedListBuilder(mCollectionsDataSourceFactory, collectionListConfig))
                .setFetchExecutor(mExecutor)
                .build();
        mCollectionPhotoPagedList = (new LivePagedListBuilder(mCollectionPhotoDataSourceFactory, collectionPhotoListConfig))
                .build();

        mUserPhotoPagedList = (new LivePagedListBuilder(mUserPhotoDataSourceFactory, userPhotoListConfig))
                .setFetchExecutor(mExecutor)
                .build();

        mUserLikesPagedList = (new LivePagedListBuilder(mUserLikesDataSourceFactory, userPhotoListConfig))
                .setFetchExecutor(mExecutor)
                .build();

        mUserCollectionsPagedList = (new LivePagedListBuilder(mUserCollectionsDataSourceFactory, userPhotoListConfig))
                .setFetchExecutor(mExecutor)
                .build();

    }

    public LiveData<PagedList<CollectionPhoto>> getCollectionsPagedList() {
        return mCollectionsPagedList;
    }

    public LiveData<PagedList<Photo>> getCollectionPhotoPagedList() {
        return mCollectionPhotoPagedList;
    }

    public LiveData<PagedList<Photo>> getPhotoPagedList() {
        return mPhotoPagedList;
    }

    public LiveData<PagedList<Photo>> getTrendingPagedList() {
        return mTrendingPagedList;
    }

    public LiveData<PagedList<Photo>> getUserPhotoPagedList() {
        return mUserPhotoPagedList;
    }

    // Setters
    public void setPhotoSortOrder(String photoSortOrder) {
        mPhotoDataSourceFactory.setPhotoSortOrder(photoSortOrder);
    }

    public void setTrendingsSortOrder(String trendingSortOrder) {
        mTrendingDataSourceFactory.setTrendingsSortOrder(trendingSortOrder);
    }

    public void setCollectionSortOrder(String collectionSortOrder) {
        mCollectionsDataSourceFactory.setCollectionSortOrder(collectionSortOrder);
    }

    public void setCollectionId(int collectionId) {
        mCollectionPhotoDataSourceFactory.setCollectionId(collectionId);
    }

    public void setUserPhotoOptions(String username, String userPhotoSortOrder) {
        mUserPhotoDataSourceFactory.setUserOptions(username, userPhotoSortOrder);
    }

    public void setUserLikesOptions(String username, String userLikesSortOrder) {
        mUserLikesDataSourceFactory.setUserOptions(username, userLikesSortOrder);
    }

    public void setUserCollectionsOptions(String username) {
        mUserCollectionsDataSourceFactory.setUserOptions(username);
    }


    // On Refresh
    public void onPhotoDataSourceRefresh() {
        mPhotoDataSourceFactory.mPhotoLiveDataSource.getValue().invalidate();
    }

    public void onTrendingDataSourceRefresh() {
        mTrendingDataSourceFactory.mTrendingLiveDataSource.getValue().invalidate();
    }


}
