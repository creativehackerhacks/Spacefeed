package com.example.ansh.spacefeed.modal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.ansh.spacefeed.dataSource.ItemDataSource;
import com.example.ansh.spacefeed.factory.ItemDataSourceFactory;
import com.example.ansh.spacefeed.pojos.Photo;

public class ItemViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<Photo>> itemPagedList;
//    public LiveData<PageKeyedDataSource<Integer, Photo>> liveDataSource;

    //constructor
    public ItemViewModel() {
        //getting our data source factory
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
//        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.ITEM_PER_PAGE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

    public LiveData<PagedList<Photo>> getItemPagedList() {
        return itemPagedList;
    }
}
