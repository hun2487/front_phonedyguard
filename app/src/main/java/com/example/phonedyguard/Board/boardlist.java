package com.example.phonedyguard.Board;

public class boardlist {
    private String title; //제목
    private long num; //게시판 번호

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
