package com.example.phonedyguard.sign_out;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface Logout_interface {

    @DELETE("/users/events")
    Call<Logout_Response> getLogout(@Header("Authorization") String token, @Body Logout_rtf data);
}
