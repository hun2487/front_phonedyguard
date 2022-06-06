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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardMod  extends AppCompatActivity {
    String token = ((MainDisplay)MainDisplay.context_main).call_token;
    String accessToken = ((MainDisplay) MainDisplay.context_main).origin_token;
    String refreshToken = ((MainDisplay) MainDisplay.context_main).call_refreshtoken;

    long num = ((BoardActivity)BoardActivity.context_main).number; //게시판 위치 얻어옴

    private final String BASEURL = "http://3.36.109.233/"; //url
    private TextView title_et, content_et, id_et;
    private selectInterface selectInterface;
    private Logout_interface Logout_interface;

    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_mod);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("게시글 수정"); //툴바 타이틀 이름

        Button back_bt = (Button) findViewById(R.id.b_button);
        Button mod_bt = (Button) findViewById(R.id.mod_button);

        title_et = findViewById(R.id.title_et);  //제목
        content_et = findViewById(R.id.content_et); //내용
        id_et = findViewById(R.id.id_et); //아이디

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Boardselect.class);
                startActivity(intent);
            }
        });

        mod_bt.setOnClickListener(new View.OnClickListener() {  //수정하기
            @Override
            public void onClick(View view) {
                if(title_et.getText().length() == 0){
                    Toast.makeText(BoardMod.this, "제목을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else if(content_et.getText().length() == 0){
                    Toast.makeText(BoardMod.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else{
                putBoard();
                Intent intent = new Intent(getApplicationContext(), Boardselect.class);
                startActivity(intent);}
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        selectInterface = retrofit.create(selectInterface.class);
        Logout_interface = retrofit.create(Logout_interface.class);

        Call<getBoard> call = selectInterface.getData(token,num);
        call.enqueue(new Callback<getBoard>()  {
            @Override
            public void onResponse(Call<getBoard> call, Response<getBoard> response) {
                if (!response.isSuccessful()){
                    Log.d("통신: ", response.message());
                    return;
                }
                getBoard result = response.body();

                title_et.setText(result.getTitle());
                content_et.setText(result.getContent());
                id_et.setText(result.getEmail());
            }

            @Override
            public void onFailure(Call<getBoard> call, Throwable t) {
                Log.d("통신 실패: ", t.getMessage());
            }
        });
    }

    public void putBoard(){ //수정하기 실행
        putBoard mod = new putBoard(num,title_et.getText().toString(), content_et.getText().toString());
        Call <putBoard> put = selectInterface.putData(token,num,mod);

        put.enqueue(new Callback<putBoard>()  {

            @Override
            public void onResponse(Call<putBoard> call, Response<putBoard> response) {
                if (!response.isSuccessful()){
                    Log.d("통신: ", response.message());
                    return;
                }
                putBoard result = response.body();
                result.getNumber();
                result.getTitle();
                result.getContent();
            }

            @Override
            public void onFailure(Call<putBoard> put, Throwable t) {
                Log.d("통신 실패:", t.getMessage());
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
