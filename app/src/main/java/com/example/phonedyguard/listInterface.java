package com.example.phonedyguard;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface listInterface {
    @GET("/test4/") //API 지정
    Call<List<getBoard>> getPost();
}
