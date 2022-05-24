package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

public class getBoard {

    @SerializedName("testStr") //서버 변수
    private String Title; //클라이언트 변수
    @SerializedName("testStr2")
    private String Content;

    public getBoard(String Title, String Content)
    {
        this.Title = Title;
        this.Content = Content;
    }
    public String getTitle() {
        return Title;
    }
    public String getContent() {
        return Content;
    }

}
