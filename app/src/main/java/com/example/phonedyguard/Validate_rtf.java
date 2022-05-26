package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

/* 아이디 중복 확인 */
public class Validate_rtf {

    @SerializedName("userID")
    private String userID;

    @SerializedName("checkID")
    private boolean checkID;

    public Validate_rtf(String userID) {
        this.userID = userID;
    }

    public boolean getCheckID() { return checkID; }
}
