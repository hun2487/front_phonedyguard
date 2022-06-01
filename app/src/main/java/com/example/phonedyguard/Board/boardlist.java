package com.example.phonedyguard.Board;

public class boardlist {
    private String title;
    //private String id;
    private long num;

    boardlist(String title, long num){
        this.title = title;
        this.num = num;
    }


    public String getTitle() {
        return title;
    }

    public long getNum() {
        return num;
    }
}
