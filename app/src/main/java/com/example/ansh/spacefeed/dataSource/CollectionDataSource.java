package com.example.ansh.spacefeed.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDataSource extends PageKeyedDataSource<Integer, CollectionPhoto> {

    // API_KEY or CLIENT_ID
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    public static final String TAG_COLLECTION_DATA_SOURCE = CollectionDataSource.class.getSimpleName();
    public static final int ITEM_PER_PAGE = 10;
    private static int FIRST_PAGE = 1;

    private LoadInitialParams mLoadInitialParams;
    private LoadParams mLoadAfterParams;

    private MutableLiveData mNetworkState;
    private MutableLiveData mInitialLoading;
    private Executor mRetryExecutor;

    private String mCollectionsSortOrder;

    public CollectionDataSource(Executor retryExecutor, String collectionsSortOrder) {
        this.mRetryExecutor = retryExecutor;
        this.mNetworkState = new MutableLiveData();
        this.mInitialLoading = new MutableLiveData();

        this.mCollectionsSortOrder = collectionsSortOrder;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CollectionPhoto> callback) {
        ApiClient.getInstance().getApi().getCollections(mCollectionsSortOrder, CLIENT_ID, params.requestedLoadSize, FIRST_PAGE)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        if(response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), null, 3);
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "onResponse: Collection (Initial)--- " + response);
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "onResponse: Collection (Initial Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "Collection API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
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
        ApiClient.getInstance().getApi().getCollections(mCollectionsSortOrder, CLIENT_ID, params.requestedLoadSize, params.key)
                .enqueue(new Callback<List<CollectionPhoto>>() {
                    @Override
                    public void onResponse(Call<List<CollectionPhoto>> call, Response<List<CollectionPhoto>> response) {
                        if(response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), params.key+1);
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "onResponse: Collection (After)--- " + response);
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "onResponse: Collection (After Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_COLLECTION_DATA_SOURCE, "Collection API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
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
