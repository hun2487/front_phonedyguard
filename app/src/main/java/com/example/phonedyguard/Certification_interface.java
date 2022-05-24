package com.example.phonedyguard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Certification_interface {
    @POST("/test2/") // 요청할 어노테이션 넣기
    /* sendPost 함수로 Certification_rtf.java에서 정의한 데이터를 서버 Body에 보내고 Certification_rtf로 데이터 받음*/
    Call<Certification_rtf> sendPost(@Body Certification_rtf certification_post);
}
