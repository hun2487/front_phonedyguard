package com.example.phonedyguard.Board;

import com.google.gson.annotations.SerializedName;

public class getBoard {

    @SerializedName("title") //서버 변수
    private String Title; //클라이언트 변수
    @SerializedName("number")
    private long number;
    @SerializedName("email")
    private String email;
    @SerializedName("content")
    private String Content;
    @SerializedName("check")
    private String check;

    public getBoard(String Title, long number, String Content, String check, String email)
    {
        this.email = email;
        this.check = check;
        this.Content = Content;
        this.Title = Title;
        this.number = number;

    }
    public String getEmail() {return email;}
    public String getCheck() { return check; }
    public String getContent() { return Content; }
    public String getTitle() {
        return Title;
    }
    public long getNumber() { return number; }
}
