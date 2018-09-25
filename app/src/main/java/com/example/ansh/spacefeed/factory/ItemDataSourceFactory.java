package com.example.ansh.spacefeed.factory;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.ansh.spacefeed.dataSource.ItemDataSource;
import com.example.ansh.spacefeed.pojos.Photo;

public class ItemDataSourceFactory extends DataSource.Factory {

    // Create the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Photo>> itemLiveDataSource;

    public ItemDataSourceFactory() {
        itemLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Photo> create() {
        // getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        // posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        // return the datasource
        return itemDataSource;
    }

    // getter for itemLiveDataSource


//    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> getItemLiveDataSource() {
//        return itemLiveDataSource;
//    }
}
