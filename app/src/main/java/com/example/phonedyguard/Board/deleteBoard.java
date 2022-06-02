package com.example.phonedyguard.Board;

import com.google.gson.annotations.SerializedName;

public class deleteBoard {

    @SerializedName("number")
    private long number;

    public deleteBoard(long number)
    {
        this.number = number;

    }

    public long getNumber() {
        return number;
    }
}
