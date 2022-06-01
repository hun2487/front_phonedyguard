package com.example.phonedyguard.sign_in;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Token_interface {

    // 로그인 경로
    @POST("/users/events")
    //tokenPost 함수로 Token_rtf.java에서 정의한 데이터들을 서버 Body에 보낸 후 Token_Response로 데이터 받음
    Call<Token_Response> tokenPost(@Body Token_rtf data);

}
