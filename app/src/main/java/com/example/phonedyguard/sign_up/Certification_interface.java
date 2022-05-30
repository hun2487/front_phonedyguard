package com.example.phonedyguard.sign_up;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Certification_interface {

    // SMS 문자 인증 경로
    @POST("/users/sms")

    /*     응답클래스                            보낼  클래스데이터     이름      */
    Call<Certification_Response> smsPost(@Body Certification_rtf data);
}
