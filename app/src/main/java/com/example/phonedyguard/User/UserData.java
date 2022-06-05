package com.example.phonedyguard.User;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private String sex;

    @SerializedName("phone")
    private String phone;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }
}
