package com.example.phonedyguard.Board;

import com.google.gson.annotations.SerializedName;

public class putBoard {

    @SerializedName("number")
    private long number;
    @SerializedName("title") //서버 변수
    private String Title; //클라이언트 변수
    @SerializedName("content")
    private String Content;

    public putBoard(long number, String Title, String Content)
    {
        this.number = number;
        this.Title = Title;
        this.Content = Content;
    }
    public long getNumber() {return number; }
    public String getTitle() {
        return Title;
    }
    public String getContent() {
        return Content;
    }
}
