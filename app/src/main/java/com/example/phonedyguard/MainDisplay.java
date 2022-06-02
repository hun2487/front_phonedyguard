package com.example.phonedyguard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Board.BoardActivity;
import com.example.phonedyguard.tokenassistance.TokenAssistance;
import com.example.phonedyguard.tokenassistance.TokenAssistance_interface;
import com.example.phonedyguard.tokenassistance.TokenAssistance_rtf;

public class MainDisplay extends AppCompatActivity {

    public static Context context_main;
    public String call_token, call_refreshtoken;
    public Integer call_refreshtokne_time;
    Button guardbt;
    TextView tv_token, tv_tokenfire;

    TokenAssistance tokenAssistance;
    TokenAssistance_interface  tokenAssistance_interface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seperate);

        context_main = this;
        guardbt = findViewById(R.id.guardian);


        // 로그인 이후의 내부 저장소에 들어있는 값들을 다른 인탠트에서도 사용할 수 있게 세팅
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDB", MODE_PRIVATE);
        String gettoken = sharedPreferences.getString("token","");
        String getrefreshtoken = sharedPreferences.getString("refreshtoken", "");
        Integer getrefreshtoken_time = sharedPreferences.getInt("refreshtoken_time", 0);

        call_token = "Bearer " + gettoken;
        call_refreshtoken = getrefreshtoken;
        call_refreshtokne_time = getrefreshtoken_time;

        tv_token.setText(gettoken);

        SharedPreferences sharedPreferences_fire = getSharedPreferences("tokenDB_fire", MODE_PRIVATE);
        String gettoken_fire = sharedPreferences_fire.getString("token_fire","");

        tv_tokenfire.setText(gettoken_fire);

        /*test*/
        tokenAssistance = TokenAssistance.getInstance();
        tokenAssistance_interface = TokenAssistance.getRetrofitInterface_assistance();
        tokenAssistance.startAssistance(new TokenAssistance_rtf(call_token,call_refreshtoken));

        guardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
