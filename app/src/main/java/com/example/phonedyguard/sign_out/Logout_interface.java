package com.example.phonedyguard.sign_out;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.Header;

public interface Logout_interface {

    @HTTP(method = "DELETE", path = "/users/events", hasBody = true)
    //@DELETE("/users/events")
    Call<Logout_rtf> deleteData(@Header("Authorization") String token, @Body Logout_rtf logout);// => 게시판 삭제
}
