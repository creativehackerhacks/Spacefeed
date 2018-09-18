package com.example.ansh.spacefeed.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This the client class for the retrofit.
 * This makes ensure that there's one retrofit instance at a time.
 */
public class ApiClient {

    public static final String BASE_URL = "https://api.unsplash.com/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}
