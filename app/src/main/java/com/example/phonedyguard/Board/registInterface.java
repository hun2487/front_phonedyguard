package com.example.phonedyguard.Board;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface registInterface {

    @POST("/board/") //API 지정
    Call<PostBoard> createPost(@Header("Authorization") String token, @Body PostBoard post);
}
