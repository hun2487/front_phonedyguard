package com.example.phonedyguard;

import com.google.gson.annotations.SerializedName;

/* 회원가입 값 요청을 요청 */

public class Register_rtf {

    @SerializedName("userID")
    private String userID;

    @SerializedName("userPassword")
    private String userPassword;

    @SerializedName("userRole")
    private String userRole;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userSex")
    private String userSex;

    @SerializedName("userPhonenumber")
    private String userPhonenumber;

    @SerializedName("userBirth")
    private String userBirth;

    public Register_rtf(String userID, String userPassword, String userRole, String userName, String userSex,
                        String userPhonenumber, String userBirth) {

        this.userID = userID;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhonenumber = userPhonenumber;
        this.userBirth = userBirth;

    }


}
