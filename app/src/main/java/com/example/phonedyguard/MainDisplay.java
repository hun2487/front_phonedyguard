package com.example.phonedyguard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.menu.GuardMenu;
import com.example.phonedyguard.menu.WardMenu;
import com.example.phonedyguard.User.UserInfo;
import com.example.phonedyguard.Util.MyApp;
import com.example.phonedyguard.Util.Utils;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainDisplay extends AppCompatActivity {

    private final String BASEURL = "http://3.36.109.233/"; //url

    public static Context context_main;
    public String call_token, call_refreshtoken, origin_token;
    Button guardbt, wardbt;

    private Logout_interface Logout_interface;

    Toolbar mytoolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seperate);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Logout_interface = retrofit.create(Logout_interface.class);

        // 수정 삭제 필요
        context_main = this;

        guardbt = findViewById(R.id.guardian);
        wardbt = findViewById(R.id.ward);

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
    }
    public void deleteToken() {
        Logout_rtf post = new Logout_rtf(origin_token, call_refreshtoken);
        Call<Logout_rtf> call = Logout_interface.deleteData(call_token, post);
        call.enqueue(new Callback<Logout_rtf>() {
            @Override
            public void onResponse(Call<Logout_rtf> call, Response<Logout_rtf> response) {
            }

            @Override
            public void onFailure(Call<Logout_rtf> call, Throwable t) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu user){
        getMenuInflater().inflate(R.menu.user, user);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bt_logout:
                deleteToken();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.bt_info:
                Intent a = new Intent(getApplicationContext(), UserInfo.class);
                startActivity(a);
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
