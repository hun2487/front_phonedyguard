package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

public class Certification_rtf {

    @SerializedName("title")
    private String Certification_num_rtf;
    @SerializedName("contents")
    private String Phone_num_rtf;

    public Certification_rtf(String Certification_num_rtf, String Phone_num_rtf) {
        this.Certification_num_rtf = Certification_num_rtf;
        this.Phone_num_rtf = Phone_num_rtf;
    }

    public String getCertificationn_rtf() {
        return Certification_num_rtf;
    }

    public String getPhone_num_rtf() {
        return Phone_num_rtf;
    }

    public void setCertification_num_rtf() { this.Certification_num_rtf = Certification_num_rtf; }

    public void setPhone_num_rtf() { this.Phone_num_rtf = Phone_num_rtf; }

}
