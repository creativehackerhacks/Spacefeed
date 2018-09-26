package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.ansh.spacefeed.dataSource.PhotoDataSource;
import com.example.ansh.spacefeed.factory.PhotoDataSourceFactory;
import com.example.ansh.spacefeed.pojos.Photo;

public class PhotoViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<Photo>> mPhotoPagedList;
//    public LiveData<PageKeyedDataSource<Integer, Photo>> mPhotoLiveDataSource;

    //constructor
    public PhotoViewModel() {
        //getting our data source factory
        PhotoDataSourceFactory photoDataSourceFactory = new PhotoDataSourceFactory();

        //getting the live data source from data source factory
//        mPhotoLiveDataSource = photoDataSourceFactory.getPhotoLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(PhotoDataSource.ITEM_PER_PAGE).build();

        //Building the paged list
        mPhotoPagedList = (new LivePagedListBuilder(photoDataSourceFactory, pagedListConfig))
                .build();
    }

    public LiveData<PagedList<Photo>> getPhotoPagedList() {
        return mPhotoPagedList;
    }
}
