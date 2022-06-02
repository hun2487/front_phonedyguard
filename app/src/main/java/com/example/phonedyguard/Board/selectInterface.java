package com.example.phonedyguard.Board;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface selectInterface {

    @GET("/board/{number}") //API 지정
    Call<getBoard> getData(@Header("Authorization") String token,@Path("number") long number);// => 변수 만들어서 본인 구분값 받기.

    @PUT("/board/{number}") //API 지정
    Call<putBoard> putData(@Header("Authorization") String token, @Path("number") long number, @Body putBoard putBoard);// => 게시판 수정

    @DELETE("/board/{number}") //API 지정
    Call<Void> deleteData(@Header("Authorization") String token, @Path("number") long number);// => 게시판 수정

}
