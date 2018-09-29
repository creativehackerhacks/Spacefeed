package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.ansh.spacefeed.dataSource.CollectionDataSource;
import com.example.ansh.spacefeed.factory.CollectionDataSourceFactory;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

public class CollectionPhotoViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<CollectionPhoto>> mCollectionPhotoPagedList;
//    public LiveData<PageKeyedDataSource<Integer, Photo>> mPhotoLiveDataSource;

    //constructor
    public CollectionPhotoViewModel() {
        //getting our data source factory
        CollectionDataSourceFactory collectionPhotoDataSourceFactory = new CollectionDataSourceFactory();

        //getting the live data source from data source factory
//        mPhotoLiveDataSource = collectionPhotoDataSourceFactory.getPhotoLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(CollectionDataSource.ITEM_PER_PAGE).build();

        //Building the paged list
        mCollectionPhotoPagedList = (new LivePagedListBuilder(collectionPhotoDataSourceFactory, pagedListConfig))
                .build();
    }

    public LiveData<PagedList<CollectionPhoto>> getCollectionPhotoPagedList() {
        return mCollectionPhotoPagedList;
    }

}
