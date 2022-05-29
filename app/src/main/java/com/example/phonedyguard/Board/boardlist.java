package com.example.phonedyguard.Board;

public class boardlist {
    private String title;
    private long   idx;

    boardlist(String title, long idx){
        this.title = title;
        this.idx = idx;
    }


    public String getTitle() {
        return title;
    }

    public long getIdx() {
        return idx;
    }
}
