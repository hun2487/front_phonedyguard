package com.example.phonedyguard.Board;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface selectInterface {

    @GET("/board/{num}") //API 지정
    Call<getBoard> getData(@Path("num") long num);

}
