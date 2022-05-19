package com.example.phonedyguard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface restful {

/*
    @FormUrlEncoded
    @POST("Posts")
    Call<result> createPost(
            @Field("title") int title,
            @Field("content") String content
    );*/
//
//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
//    @POST("/test2/")
//    Call<result> createPost(@FieldMap Map<String, String> fields);

    @POST("/test2/")
    Call<result> createPost(@Body result post);
}
