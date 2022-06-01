package com.example.phonedyguard.sign_in;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// Data jsonObject
public class Data {

    @SerializedName("grantType")
    @Expose // object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략해 준다.
    private String grantType;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("refreshTokenExpirationTime")
    @Expose
    private Integer refreshTokenExpirationTime;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public void setRefreshTokenExpirationTime(Integer refreshTokenExpirationTime) {
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
