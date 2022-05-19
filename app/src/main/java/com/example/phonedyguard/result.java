package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

public class result {

    @SerializedName("testStr")
    private String Title;
    @SerializedName("testStr2")
    private String Content;

    public result(String Title, String Content)
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
