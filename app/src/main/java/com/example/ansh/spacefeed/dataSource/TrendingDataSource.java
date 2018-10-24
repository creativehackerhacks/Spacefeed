package com.example.ansh.spacefeed.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingDataSource extends PageKeyedDataSource<Integer, Photo> {

    // API_KEY or CLIENT_ID
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    public static final String TAG_TRENDING_DATA_SOURCE = TrendingDataSource.class.getSimpleName();
    public static final int ITEM_PER_PAGE = 10;
    private static int FIRST_PAGE = 1;

    private LoadInitialParams mLoadInitialParams;
    private LoadParams mLoadAfterParams;

    private MutableLiveData mNetworkState;
    private MutableLiveData mInitialLoading;
    private Executor mRetryExecutor;


    private String mTrendingsSortOrder;

    public TrendingDataSource(Executor retryExecutor, String trendingsSortOrder) {
        this.mRetryExecutor = retryExecutor;
        this.mNetworkState = new MutableLiveData();
        this.mInitialLoading = new MutableLiveData();

        this.mTrendingsSortOrder = trendingsSortOrder;
    }


    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) {
        ApiClient.getInstance().getApi().getTrendingPhotos(CLIENT_ID, params.requestedLoadSize, FIRST_PAGE, mTrendingsSortOrder)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if (response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), null, 4);
                            Log.i(TAG_TRENDING_DATA_SOURCE, "onResponse: Trending (Initial)--- " + response);
                            Log.i(TAG_TRENDING_DATA_SOURCE, "onResponse: Trending (Initial Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_TRENDING_DATA_SOURCE, "Trending API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
        ApiClient.getInstance().getApi().getTrendingPhotos(CLIENT_ID, params.requestedLoadSize, params.key, mTrendingsSortOrder)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if (response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), params.key+1);
                            Log.i(TAG_TRENDING_DATA_SOURCE, "onResponse: Trending (Initial)--- " + response);
                            Log.i(TAG_TRENDING_DATA_SOURCE, "onResponse: Trending (Initial Size)--- " + response.body().size());
                        } else {
                            Log.i(TAG_TRENDING_DATA_SOURCE, "Trending API (After Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                    }
                });
    }
}