package com.example.phonedyguard.Board;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface selectInterface {

    @GET("/board/{num}") //API 지정
    Call<getBoard> getData(@Header("Authorization") String token,@Path("num") long num);// => 변수 만들어서 본인 구분값 받기.

    @GET("/board/{num}") //API 지정
    Call<getBoard> putData(@Path("num") long num);// => 게시판 수정
}
