package com.example.phonedyguard.tokenassistance;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenAssistance_interface {

    @POST("/users/tokens")
    Call<TokenAssistance_Response> getRefresh(@Header("Authorization") String token, @Body TokenAssistance_rtf data);

}
