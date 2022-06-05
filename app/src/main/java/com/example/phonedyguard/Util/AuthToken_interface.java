package com.example.phonedyguard.Util;

import com.example.phonedyguard.Util.AuthToken_Response;
import com.example.phonedyguard.Util.AuthToken_rtf;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthToken_interface {

    @POST("/users/tokens")
    Call<AuthToken_Response> getReIssue(@Body AuthToken_rtf data);

}
