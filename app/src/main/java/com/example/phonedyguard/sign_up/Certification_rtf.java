package com.example.phonedyguard.sign_up;

import com.google.gson.annotations.SerializedName;


/* 보낼 데이터 */
public class Certification_rtf {

    @SerializedName("phone")
    private String phone;

    public Certification_rtf(String phone) {
        this.phone = phone;
    }
}
