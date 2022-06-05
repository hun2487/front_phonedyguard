package com.example.phonedyguard.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface UserUpdate_interface {

    @PUT("/users")
    Call<UserUpdate_Response> getUpdate(@Header("Authorization") String token, @Body UserUpdate_rtf data);
}
