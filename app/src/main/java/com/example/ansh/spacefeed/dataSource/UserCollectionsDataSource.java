package com.example.ansh.spacefeed.dataSource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCollectionsDataSource extends PageKeyedDataSource<Integer, CollectionPhoto> {

    private static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    private static final String TAG_COLLECTION_PHOTO_DATA_SOURCE = CollectionPhotoDataSource.class.getSimpleName();
    public static final int ITEM_PER_PAGE = 10;
    private static int FIRST_PAGE = 1;

    // Initialization of API service instance.
    private ApiClient mUnsplashService;

    private String mUsername;

    public UserCollectionsDataSource(Executor executor, String username) {
        mUnsplashService = mUnsplashService.getInstance();
        this.mUsername = username;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CollectionPhoto> callback) {
        mUnsplashService.getApi().getUserCollections(mUsername, CLIENT_ID, params.requestedLoadSize, 1)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        if(response.isSuccessful() && response.code() == 200) {
                            callback.onResult(response.body(), null, 2);
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "onResponse: Collection Photo (Initial)--- " + response);
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "onResponse: Collection Photo (Initial Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "Collection Photo API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CollectionPhoto>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CollectionPhoto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CollectionPhoto> callback) {
        mUnsplashService.getApi().getUserCollections(mUsername, CLIENT_ID, params.requestedLoadSize, params.key)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        if(response.isSuccessful() && response.code() == 200) {
                            callback.onResult(response.body(), params.key+1);
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "onResponse: Collection Photo (After)--- " + response);
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "onResponse: Collection Photo (After Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_COLLECTION_PHOTO_DATA_SOURCE, "Collection Photo API (After Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CollectionPhoto>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                    }
                });
    }

}
