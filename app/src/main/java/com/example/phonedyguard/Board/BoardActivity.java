package com.example.phonedyguard.Board;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardActivity extends AppCompatActivity {

    String token = ((MainDisplay) MainDisplay.context_main).call_token;
    String accessToken = ((MainDisplay) MainDisplay.context_main).origin_token;
    String refreshToken = ((MainDisplay) MainDisplay.context_main).call_refreshtoken;

    private Logout_interface Logout_interface;

    public static Context context_main;
    public long number;

    private final String BASEURL = "http://3.36.109.233/"; //url
    private ListView boardlist;

    Toolbar mytoolbar;

    ArrayList<com.example.phonedyguard.Board.boardlist> boardlists = new ArrayList<com.example.phonedyguard.Board.boardlist>();
    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("게시판"); //툴바 타이틀 이름


        context_main = this;

        boardlist = findViewById(R.id.listView);

        Button boardwt = (Button) findViewById(R.id.reg_button); //등록 버튼

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Logout_interface = retrofit.create(Logout_interface.class);
        listInterface listInterface = retrofit.create(listInterface.class);

        Call<List<getBoard>> call = listInterface.getPost();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BoardActivity.this, android.R.layout.simple_list_item_1, array);

        call.enqueue(new Callback<List<getBoard>>() {

            @Override
            public void onResponse(Call<List<getBoard>> call, Response<List<getBoard>> response) {
                if (!response.isSuccessful()) {
                    Log.d("통신 :", response.message());
                    return;
                }

                List<getBoard> posts = response.body();

                //db값과의 매핑
                for (getBoard post : posts) {
                    com.example.phonedyguard.Board.boardlist temp_list = new boardlist(post.getTitle(), post.getNumber());
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
                Log.d("통신 실패 : ", t.getMessage()); //서버 통신 실패시 로그 메시지
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

                number = boardlists.get(position).getNum(); //게시판 번호
                boardlists.get(position);
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
