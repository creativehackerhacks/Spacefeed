package com.example.ansh.spacefeed.apis;

import android.support.annotation.Nullable;

import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;

import java.util.List;

import okhttp3.ResponseBody;
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
            @Query("page") int pageCount,
            @Query("order_by") String order
    );

    @GET("photos/{id}/download")
    Call<ResponseBody> getDownload(
            @Path("id") String id,
            @Query("client_id") String api_key
    );

    /**
     * For Trending
     */
    @GET("photos/curated")
    Call<List<Photo>> getTrendingPhotos(
            @Query("client_id") String apiKey,
            @Query("per_page") int itemCount,
            @Query("page") int pageCount,
            @Query("order_by") String order
    );

    /**
     * FOR COLLECTIONS
     * */
    @GET("collections/{sorting}")
    Call<List<CollectionPhoto>> getCollections(
            @Path("sorting") String sorting,
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

    /**
     * FOR USERS
     */
    @GET("users/{username}/photos")
    Call<List<Photo>> getUserPhotos(
            @Path("username") String username,
            @Query("client_id") String apiKey,
            @Query("per_page") int itemCount,
            @Query("page") int pageCount,
            @Query("order_by") String order
    );

    @GET("users/{username}/likes")
    Call<List<Photo>> getUserLikes(
            @Path("username") String username,
            @Query("client_id") String apiKey,
            @Query("per_page") int itemCount,
            @Query("page") int pageCount
    );

    @GET("users/{username}/collections")
    Call<List<Photo>> getUserCollections(
            @Path("username") String username,
            @Query("client_id") String apiKey,
            @Query("per_page") int itemCount,
            @Query("page") int pageCount
    );

}
