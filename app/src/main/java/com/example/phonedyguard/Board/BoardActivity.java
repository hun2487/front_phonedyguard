package com.example.phonedyguard.Board;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardActivity extends AppCompatActivity {

    public static Context context_main;
    public long num;

    private final String BASEURL = "http://3.36.109.233/"; //url
    private ListView boardlist;

    ArrayList<com.example.phonedyguard.Board.boardlist> boardlists = new ArrayList<com.example.phonedyguard.Board.boardlist>();
    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

        context_main = this;

        boardlist = findViewById(R.id.listView);

        Button boardwt = (Button) findViewById(R.id.reg_button); //등록 버튼

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        listInterface listInterface = retrofit.create(listInterface.class);

        Call<List<getBoard>> call = listInterface.getPost();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BoardActivity.this, android.R.layout.simple_list_item_1, array);

        call.enqueue(new Callback<List<getBoard>>() {

            @Override
            public void onResponse(Call<List<getBoard>> call, Response<List<getBoard>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<getBoard> posts = response.body();

                //잔짜 db값과의 매핑
                for (getBoard post : posts) {
                    com.example.phonedyguard.Board.boardlist temp_list = new boardlist(post.getTitle(), post.getNum());
                    boardlists.add(temp_list); //ArrayList로 받은 객체 list에 삽입
                }

                //listview에 출력
                for(int i = 0; i < boardlists.toArray().length; i++) {
                    array.add("제목: " + boardlists.get(i).getTitle());
                }
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

        boardlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                Intent intent = new Intent(getApplicationContext(), Boardselect.class);
                startActivity(intent);

                num = boardlists.get(position).getNum(); //게시판 번호
                boardlists.get(position);
            }
        });
    }
}
