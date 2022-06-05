package com.example.phonedyguard.sign_out;

import com.google.gson.annotations.SerializedName;

public class Logout_Response {
    @SerializedName("state")
    private int state;

    public int getState() {
        return state;
    }
}
