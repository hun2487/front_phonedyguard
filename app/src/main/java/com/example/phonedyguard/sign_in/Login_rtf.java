package com.example.phonedyguard.sign_in;

import com.google.gson.annotations.SerializedName;

// 데이터 보내기 (로그인 정보)
public class Login_rtf {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    public Login_rtf(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() { return token; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) { this.token = token; }

}
