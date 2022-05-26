package com.example.phonedyguard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Certification_interface {

    // SMS 문자 인증
    @POST("/users/sms")
    /* sendPost 함수로 Certification_rtf.java에서 정의한 데이터를 서버 Body에 보내고 Certification_rtf로 데이터 받음*/
    Call<Certification_rtf> smsPost(@Body Certification_rtf certification_post);
}
