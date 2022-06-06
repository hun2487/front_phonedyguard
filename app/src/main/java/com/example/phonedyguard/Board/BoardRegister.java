package com.example.phonedyguard.Board;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_in.LoginActivity;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BoardRegister extends AppCompatActivity {

    String token = ((MainDisplay) MainDisplay.context_main).call_token;
    String accessToken = ((MainDisplay) MainDisplay.context_main).origin_token;
    String refreshToken = ((MainDisplay) MainDisplay.context_main).call_refreshtoken;

    private final String BASEURL = "http://3.36.109.233/"; //url
    private EditText title_et, content_et;

    private registInterface registInterface;
    private Logout_interface Logout_interface;

    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("게시글 등록"); //툴바 타이틀 이름

        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);

        Button boardwt = (Button) findViewById(R.id.reg_button); //등록 버튼

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registInterface = retrofit.create(registInterface.class);
        Logout_interface = retrofit.create(Logout_interface.class);

        boardwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title_et.getText().length() == 0){
                    Toast.makeText(BoardRegister.this, "제목을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else if(content_et.getText().length() == 0){
                    Toast.makeText(BoardRegister.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else{
                createPost();}
            }
        });
    }

    private void createPost() {

        PostBoard post = new PostBoard(title_et.getText().toString(), content_et.getText().toString()); //String으로 변환하여 값을 보냄.

        Call<PostBoard> call = registInterface.createPost(token,post);

        call.enqueue(new Callback<PostBoard>() {
            @Override
            public void onResponse(Call<PostBoard> call, Response<PostBoard> response) {
                if (!response.isSuccessful()) {
                    Log.d("통신 : ", response.message());
                    return;
                }

                PostBoard postResponse = response.body(); //post로 값 받아옴

                String content = "";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Content: " + postResponse.getContent() + "\n";

                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PostBoard> call, Throwable t) {
                Log.d("통신 실패 : ", t.getMessage()); //서버 통신 실패시
            }
        });
    }
    //화면 터치 시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
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

