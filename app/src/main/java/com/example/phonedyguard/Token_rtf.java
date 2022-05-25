package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Token_rtf {

    @SerializedName("testStr")
    private String Token;

    @SerializedName("userID")
    private String userID;

    @SerializedName("userPassword")
    private String userPassword;



    public Token_rtf(String Token, String userID, String userPassword) {
        this.Token = Token;
        this.userID = userID;
        this.userPassword = userPassword;
    }

    public String getToken() { return Token; }

    public String getUserID() { return userID; }

    public String getUserPassword() { return userPassword; }

    public void setToken() { this.Token = Token; }

    public void setUserID() { this.userID = userID; }

    public void setUserPassword() { this.userPassword = userPassword; }
}
