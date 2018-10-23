package com.example.ansh.spacefeed.dataSource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDataSource extends PageKeyedDataSource<Integer, CollectionPhoto> {

    // API_KEY or CLIENT_ID
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    /**
     * Private member variables.
     */
    //the size of a page that we want
    public static final int PAGE_SIZE = 300;
    public static final int ITEM_PER_PAGE = 10;
    //we will start from the first page which is 1
    private static int FIRST_PAGE = 1;
    //we need to fetch from UnSplash
    private static final String SITE_NAME = "unsplash";



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CollectionPhoto> callback) {
        ApiClient.getInstance().getApi().getCollections(CLIENT_ID, ITEM_PER_PAGE, FIRST_PAGE)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        if(response.body() != null) {
                            callback.onResult(response.body(), null, 2);
                            Log.i("FUCK", "onResponse: Initial " + response);
//                            Log.i("FUCK", "onResponse: Initial Size " + response.body().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CollectionPhoto>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CollectionPhoto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CollectionPhoto> callback) {
        ApiClient.getInstance().getApi().getCollections(CLIENT_ID, ITEM_PER_PAGE, params.key)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        Integer key = (params.key >= PAGE_SIZE) ? null : params.key + 1;
                        if(response.body() != null) {
                            callback.onResult(response.body(), key);
                            Log.i("FUCK", "onResponse: After " + response);
//                            Log.i("FUCK", "onResponse: After Size " + response.body().size());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CollectionPhoto>> call, Throwable t) {
                    }
                });
    }
}
