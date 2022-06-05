package com.example.phonedyguard.Util;

import com.example.phonedyguard.sign_in.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthToken_Response {

    @SerializedName("state")
    @Expose // object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략해 준다.
    private Integer state;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("massage")
    @Expose
    private String massage;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }
}
