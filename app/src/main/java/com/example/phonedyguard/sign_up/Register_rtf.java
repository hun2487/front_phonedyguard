package com.example.phonedyguard.sign_up;

import com.google.gson.annotations.SerializedName;

/* 보낼 데이터 */

public class Register_rtf {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private String sex;

    @SerializedName("phone")
    private String phone;


    public Register_rtf(String email, String password, String name, String sex, String phone) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

}
