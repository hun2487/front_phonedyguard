package com.example.phonedyguard.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface map_restful {

    @POST("/maps/indices/") //
    Call<latlng_result> createPost(@Header("Authorization") String token, @Body latlng_result post);

    @POST ("/maps/routes")
    Call<route> postSafeLatlng(@Header("Authorization") String token, @Body ArrayList<LatLng> post);

}
