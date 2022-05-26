package com.example.phonedyguard;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface Token_interface {

    @FormUrlEncoded
    @POST("/user/events")
    Call<Map<String, String>> sendPost(@Header("Authorization") String token, @Field("userID") String userID, @Field("userPassword") String userPassword);

    /*
    @FormUrlEncoded
    @PATCH("/auth/token")
    Call<Map<String,String>> refreshToken(@Field("refresh_token") String refreshToken); */
}
