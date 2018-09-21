package com.example.ansh.spacefeed.client;

import com.example.ansh.spacefeed.apis.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This the client class for the retrofit.
 * This makes ensure that there's one retrofit instance at a time.
 */
public class ApiClient {

    public static final String BASE_URL = "https://api.unsplash.com/";
    private  Retrofit mRetrofit = null;
    private static ApiClient mApiClient;

    private ApiClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance() {
        if(mApiClient == null) {
            mApiClient = new ApiClient();
        }
        return mApiClient;
    }

    public ApiInterface getApi() {
        return mRetrofit.create(ApiInterface.class);
    }

}
