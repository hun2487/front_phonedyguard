package com.example.phonedyguard.Board;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface selectInterface {

    @GET("/board/{num}") //API 지정
    Call<getBoard> getData(@Path("num") long num);// => 변수 만들어서 본인 구분값 받기.

    /* 토큰 값 전송 POST
    @POST("")
     */

}
