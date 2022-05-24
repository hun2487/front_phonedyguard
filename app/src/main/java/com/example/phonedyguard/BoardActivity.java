package com.example.phonedyguard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardActivity extends AppCompatActivity {

    private final String BASEURL = "http://3.36.109.233/"; //url
    private ListView boardlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);
        boardlist = findViewById(R.id.listView);

        Button boardwt = (Button) findViewById(R.id.reg_button); //등록 버튼

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        listInterface listInterface = retrofit.create(listInterface.class);

        Call<List<getBoard>> call = listInterface.getPost();
        call.enqueue(new Callback<List<getBoard>>() {
            @Override
            public void onResponse(Call<List<getBoard>> call, Response<List<getBoard>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<getBoard> posts = response.body();
                ArrayList<String> array = new ArrayList<>();

                for (getBoard post : posts) {
                    array.add(post.getTitle()); //ArrayList로 받은 객체 list에 삽입
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(BoardActivity.this, android.R.layout.simple_list_item_1, array);
                boardlist.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<getBoard>> call, Throwable t) {
                Log.d("msg", t.getMessage()); //서버 통신 실패시 로그 메시지
            }
        });



        boardwt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardRegister.class);
                startActivity(intent);
            }
        });
    }
}
