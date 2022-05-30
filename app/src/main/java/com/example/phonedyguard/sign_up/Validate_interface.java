package com.example.phonedyguard.sign_up;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Validate_interface {

    // 아이디 중복 체크
    @POST("/users")
    Call<Validate_rtf> validatePost(@Body Validate_rtf validate_post);
}
