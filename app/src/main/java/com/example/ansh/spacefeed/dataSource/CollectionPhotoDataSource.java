package com.example.ansh.spacefeed.dataSource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionPhotoDataSource extends PageKeyedDataSource<Integer, Photo> {

    private static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";
    //the size of a page that we want
    public static final int PAGE_SIZE = 300;
    public static final int ITEM_PER_PAGE = 10;

    private static final String TAG = CollectionPhotoDataSource.class.getSimpleName();

    // Initialization of API service instance.
    private ApiClient mUnsplashService;

    private int mCollectionId;

    public CollectionPhotoDataSource(int collectionId) {
        this.mCollectionId = collectionId;
        mUnsplashService = mUnsplashService.getInstance();
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) {
        mUnsplashService.getApi().getCollectionPhoto(mCollectionId, CLIENT_ID, 1)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if(response.isSuccessful()) {
                            callback.onResult(response.body(), null, 2);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
        mUnsplashService.getApi().getCollectionPhoto(mCollectionId, CLIENT_ID, params.key)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if(response.isSuccessful()) {
                            Integer key = (params.key >= PAGE_SIZE) ? null : params.key + 1;
                            callback.onResult(response.body(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {

    }

}
