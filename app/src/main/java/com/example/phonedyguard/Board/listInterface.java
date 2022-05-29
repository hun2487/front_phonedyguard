package com.example.phonedyguard.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface listInterface {
    @GET("/board/") //API 지정
    Call<List<getBoard>> getPost();
}
