package com.example.phonedyguard.tokenassistance;

import com.google.gson.annotations.SerializedName;

public class TokenAssistance_Response {

    @SerializedName("grantType")
    private String grantType;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("refreshTokenExpirationTime")
    private Long refreshTokenExpirationTime;

    @SerializedName("state")
    private int state;

    public int getState() {
        return state;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setRefreshTokenExpirationTime(Long refreshTokenExpirationTime) {
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
