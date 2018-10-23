package com.example.ansh.spacefeed.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.utils.NetworkState;
import com.example.ansh.spacefeed.utils.Status;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PageKeyedDataSource<Integer, Photo> {

    // API_KEY or CLIENT_ID
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    public static final String TAG_PHOTO_DATA_SOURCE = PhotoDataSource.class.getSimpleName();
    public static final int ITEM_PER_PAGE = 10;
    private static int FIRST_PAGE = 1;

    private LoadInitialParams mLoadInitialParams;
    private LoadParams mLoadAfterParams;

    private MutableLiveData mNetworkState;
    private MutableLiveData mInitialLoading;
    private Executor mRetryExecutor;

    private String mPhotoSortOrder;

    public PhotoDataSource(Executor retryExecutor, String photoSortOrder) {
        this.mRetryExecutor = retryExecutor;
        this.mNetworkState = new MutableLiveData();
        this.mInitialLoading = new MutableLiveData();

        this.mPhotoSortOrder = photoSortOrder;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) {
        mLoadInitialParams = params;
        mInitialLoading.postValue(NetworkState.LOADING);
        mNetworkState.postValue(NetworkState.LOADING);
        ApiClient.getInstance().getApi().getPhotos(CLIENT_ID, params.requestedLoadSize, FIRST_PAGE, mPhotoSortOrder)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if(response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), null, 4);
                            mInitialLoading.postValue(NetworkState.LOADED);
                            mNetworkState.postValue(NetworkState.LOADED);
                            mLoadInitialParams = null;
                            Log.i(TAG_PHOTO_DATA_SOURCE, "onResponse: Photo (Initial)--- " + response);
                            Log.i(TAG_PHOTO_DATA_SOURCE, "onResponse: Photo (Initial Size)--- " + response.body().size());
                        } else {
                            mInitialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                            mNetworkState.postValue(new NetworkState(Status.FAILED, response.message()));
                            Log.i(TAG_PHOTO_DATA_SOURCE, "Photo API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                        mNetworkState.postValue(new NetworkState(Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
//        ApiClient.getInstance().getApi().getPhotos(CLIENT_ID, PAGE_SIZE, FIRST_PAGE)
//                .enqueue(new Callback<List<Photo>>() {
//                    @Override
//                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
//                        //if the current page is greater than one
//                        //we are decrementing the page number
//                        //else there is no previous page
//                        Integer adjacentKey = (params.key > 1) ? params.key -1 : null;
//                        if(response.body() != null) {
//                            callback.onResult(response.body(), adjacentKey);
//                            Log.i("FUCK", "onResponse: Before " + response);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Photo>> call, Throwable t) {
//
//                    }
//                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
        mLoadAfterParams = params;
        mInitialLoading.postValue(NetworkState.LOADING);
        mNetworkState.postValue(NetworkState.LOADING);
        ApiClient.getInstance().getApi().getPhotos(CLIENT_ID, params.requestedLoadSize, params.key, mPhotoSortOrder)
                .enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if(response.body() != null && response.code() == 200) {
                            callback.onResult(response.body(), params.key+1);
                            mInitialLoading.postValue(NetworkState.LOADED);
                            mNetworkState.postValue(NetworkState.LOADED);
                            mLoadInitialParams = null;
                            Log.i(TAG_PHOTO_DATA_SOURCE, "onResponse: Photo (After)--- " + response);
                            Log.i(TAG_PHOTO_DATA_SOURCE, "onResponse: Photo (After Size)--- " + response.body().size());
                        } else {
                            mInitialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                            mNetworkState.postValue(new NetworkState(Status.FAILED, response.message()));
                            Log.i(TAG_PHOTO_DATA_SOURCE, "Photo API (Initial Code)--- " + response.code() + " and MESSAGE:: " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {
                        String errorMessage = t.getMessage();
                        if(t == null) {
                            errorMessage = "error";
                        }
                        mNetworkState.postValue(new NetworkState(Status.FAILED, errorMessage));
                    }
                });
    }


    public MutableLiveData getNetworkState() {
        return mNetworkState;
    }

    public MutableLiveData getInitialLoading() {
        return mInitialLoading;
    }


}
