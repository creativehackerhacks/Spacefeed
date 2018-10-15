package com.example.ansh.spacefeed.apis;

import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api interface for RESTFUL APIs
 */
public interface ApiInterface {

    /**
     * This method returns the list of photos therefore surrounded by <List>.
     */
    @GET("photos")
    Call<List<Photo>> getPhotos(
            @Query("client_id") String apiKey, // api_key
            @Query("per_page") int itemCount, // Number of items to show per page.(Max : 30)
            @Query("page") int pageCount
    );

    @GET("collections")
    Call<List<CollectionPhoto>> getCollections(
            @Query("client_id") String apiKey, // api_key
            @Query("per_page") int itemCount, // Number of items to show per page.(Max : 30)
            @Query("page") int pageCount
    );

    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhoto(
            @Path("id") Integer id,
            @Query("client_id") String apiKey, // api_key
            @Query("page") int pageCount
    );

}
