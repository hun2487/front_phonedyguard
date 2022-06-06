package com.example.phonedyguard.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.Board.BoardActivity;
import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.map.route_register;
import com.example.phonedyguard.map.Tracking;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuardMenu extends AppCompatActivity {

    private final String BASEURL = "http://3.36.109.233/"; //url

    Button board, set_safe, see_safe;

    String token = ((MainDisplay) MainDisplay.context_main).call_token;
    String accessToken = ((MainDisplay) MainDisplay.context_main).origin_token;
    String refreshToken = ((MainDisplay) MainDisplay.context_main).call_refreshtoken;

    private Logout_interface Logout_interface;

    Toolbar mytoolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardmenu);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("보호자"); //툴바 타이틀 이름


        board = findViewById(R.id.bt_board);
        set_safe = findViewById(R.id.bt_setsafe);
        see_safe = findViewById(R.id.bt_seesafe);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Logout_interface = retrofit.create(Logout_interface.class);

        board.setOnClickListener(new View.OnClickListener() {  //게시판
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        set_safe.setOnClickListener(new View.OnClickListener() {  //안심경로 설정
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), route_register.class);
               startActivity(intent);
            }
        });

        see_safe.setOnClickListener(new View.OnClickListener() {  //안심경로 확인
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tracking.class);
                startActivity(intent);
            }
        });
        }
    public void deleteToken() {

        Logout_rtf post = new Logout_rtf(accessToken, refreshToken);
        Call<Logout_rtf> call = Logout_interface.deleteData(token, post);
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);
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
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

