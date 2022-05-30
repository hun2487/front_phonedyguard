package com.example.phonedyguard.sign_up;


import com.google.gson.annotations.SerializedName;

/* 받을 데이터 */
public class Certification_Response {
    @SerializedName("SMS")
    private String SMS;

    public String getSMS() {
        return SMS;
    }
}
