package com.example.phonedyguard.sign_up;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Register_interface {

    // 회원가입 경로
    @POST("/users")

    /*     응답클래스                 보낼  클래스데이터     이름      */
    Call<Register_rtf> registerPost(@Body Register_rtf data);
}
