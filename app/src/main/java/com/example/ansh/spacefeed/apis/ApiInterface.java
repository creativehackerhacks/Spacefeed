package com.example.ansh.spacefeed.apis;

import com.example.ansh.spacefeed.pojos.UnSplashResponse;
import com.example.ansh.spacefeed.pojos.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api interface for RESTFUL APIs
 */
public interface ApiInterface {

    /**
     * This method returns the list of photos therefore surrounded by <List>.
     */
    @GET("photos")
    Call<List<UnSplashResponse>> getUnSplashResponse(
            @Query("client_id") String apiKey, // api_key
            @Query("per_page") int itemCount // Number of items to show per page.(Max : 30)
    );

}
