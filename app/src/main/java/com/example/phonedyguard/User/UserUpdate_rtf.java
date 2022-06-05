package com.example.phonedyguard.User;

import com.google.gson.annotations.SerializedName;

public class UserUpdate_rtf {

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private String sex;

    @SerializedName("phone")
    private String phone;

    public UserUpdate_rtf(String email, String name, String sex, String phone) {

        this.email = email;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }
}
