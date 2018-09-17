package com.example.ansh.spacefeed;

import com.example.ansh.spacefeed.pojos.UnSplashResponse;
import com.example.ansh.spacefeed.pojos.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("photos")
    Call<List<UnSplashResponse>> getUnSplashResponse(
            @Query("client_id") String apiKey,
            @Query("per_page") int itemCount
    );

}
