package com.example.phonedyguard.sign_in;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Token_interface {

    // 로그인 경로
    @FormUrlEncoded
    @POST("/user/events")
    Call<Token_Response> tokenPost(@Header("Authorization") String token, @Body Token_rtf data );

}
