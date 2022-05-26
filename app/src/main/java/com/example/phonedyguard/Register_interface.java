package com.example.phonedyguard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Register_interface {

    @POST("/users")
    Call<Register_rtf> registerPost(@Body Register_rtf register_post);
}
