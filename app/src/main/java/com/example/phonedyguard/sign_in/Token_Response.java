package com.example.phonedyguard.sign_in;


import com.google.gson.annotations.SerializedName;

/* 받을 데이터  (토큰) */
public class Token_Response {

    @SerializedName("grantType")
    private String grantType;

    @SerializedName("acessToken")
    private String acessToken;

    @SerializedName("refreshToken")
    private String refreshToken;


    public String getAcessToken() {
        return acessToken;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
