package com.example.phonedyguard.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserInfo_interface {

    @GET("/users")
    Call<UserInfo_Response> getInfo(@Header("Authorization") String token);
}
