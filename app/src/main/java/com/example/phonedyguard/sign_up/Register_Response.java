package com.example.phonedyguard.sign_up;

import com.google.gson.annotations.SerializedName;

public class Register_Response {

    @SerializedName("state")
    private int state;

    public int getState() {
        return state;
    }
}
