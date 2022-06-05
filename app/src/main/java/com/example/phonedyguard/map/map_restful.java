package com.example.phonedyguard.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface map_restful {

    @POST("/maps/indices/") //
    Call<latlng_result> createPost(@Header("Authorization") String token, @Body latlng_result post);

    @GET("/maps/indices/") //
    Call<latlng_result> latlng_get(@Header("Authorization") String token);

    @GET("/maps/routes") //
    Call<safe_routes> get_saferoutes(@Header("Authorization") String token);

    @POST ("/maps/routes")
    Call<List<routes>> postSafeLatlng(@Header("Authorization") String token, @Body List<routes> post);

}
