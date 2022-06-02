package com.example.phonedyguard.Board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Boardselect extends AppCompatActivity {

    String token = ((MainDisplay)MainDisplay.context_main).call_token;
    long num = ((BoardActivity)BoardActivity.context_main).number; //게시판 위치 얻어옴

    private final String BASEURL = "http://3.36.109.233/"; //url
    private TextView title_et, content_et, id_et;
    private selectInterface selectInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_select);

        Button backbt = (Button) findViewById(R.id.back_button);
        Button mod_bt = (Button) findViewById(R.id.mod_button);
        Button del_bt = (Button) findViewById(R.id.del_button);

        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        id_et = findViewById(R.id.id_et);

        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        mod_bt.setOnClickListener(new View.OnClickListener() {  //수정하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardMod.class);
                startActivity(intent);;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        selectInterface = retrofit.create(selectInterface.class);

        Call <getBoard> call = selectInterface.getData(token,num);
        call.enqueue(new Callback<getBoard>()  {
            @Override
            public void onResponse(Call<getBoard> call, Response<getBoard> response) {

                if (!response.isSuccessful()) {
                    Log.d("통신 : ", response.message());
                    return;
                }

                getBoard result = response.body();

                title_et.setText(result.getTitle());
                content_et.setText(result.getContent());
                id_et.setText(result.getEmail());

                 String check = result.getCheck(); //게시판 권한
                 String write = "W";
                 String read = "R";

                 if(check.equals(write)){
                     del_bt.setVisibility(View.VISIBLE);
                     mod_bt.setVisibility(View.VISIBLE);
                 }else if(check.equals(read)){
                     del_bt.setVisibility(View.INVISIBLE);
                     mod_bt.setVisibility(View.INVISIBLE);
                 }
            }

            @Override
            public void onFailure(Call<getBoard> call, Throwable t) {
                Log.d("통신 실패 : ", t.getMessage());
            }
        });
        del_bt.setOnClickListener(new View.OnClickListener() {  //수정하기
            @Override
            public void onClick(View view) {
                deleteBoard();
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);;
            }
        });
    }

    private void deleteBoard(){
        //deleteBoard del = new deleteBoard(num);
        Call<Void> call = selectInterface.deleteData(token,num);
        call.enqueue(new Callback<Void>()  {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}