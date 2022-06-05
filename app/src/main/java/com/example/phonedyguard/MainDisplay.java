package com.example.phonedyguard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.menu.GuardMenu;
import com.example.phonedyguard.menu.WardMenu;
import com.example.phonedyguard.User.UserInfo;
import com.example.phonedyguard.Util.MyApp;
import com.example.phonedyguard.Util.Utils;


public class MainDisplay extends AppCompatActivity {

    public static Context context_main;
    public String call_token, call_refreshtoken, origin_token;
    Button guardbt;
    Button wardbt;
    //TextView tv_token, tv_tokenfire;
    Button userinfo; // 유저 정보 확인 테스트용 (나중에 다른곳에 연결)


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seperate);

        // 수정 삭제 필요
        context_main = this;

        guardbt = findViewById(R.id.guardian);
        wardbt = findViewById(R.id.ward);
        userinfo = findViewById(R.id.userinfo);

        // 내부 저장소
        Utils.init(MyApp.getContext());
        String gettoken = Utils.getAccessToken("");
        String getrefreshtoken = Utils.getRefreshToken("");


        Log.d("통신_main","토큰 Check:" + "Token: " + gettoken + "\n" + "refreshToken: " + getrefreshtoken);

        // 수정 삭제 필요
        call_token = "Bearer " + gettoken;
        origin_token = gettoken;
        call_refreshtoken = getrefreshtoken;

        //보호자 버튼
        guardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuardMenu.class);
                startActivity(intent);
            }
        });

        wardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WardMenu.class);
                startActivity(intent);
            }
        });

        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                startActivity(intent);
            }
        });
    }
}
