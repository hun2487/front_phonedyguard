package com.example.phonedyguard.sign_in;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private Token_interface token_interface;
    private String Token = "";
    private String refreshToken = "";


    // Retrofit 인터페이스 구현
//    private void tokenPost(Token_rtf data) {
//
//        token_interface.tokenPost(data).enqueue(new Callback<Token_Response>() {
//            @Override
//            public void onResponse(Call<Token_Response> call, Response<Token_Response> response) {
//                Token_Response result = response.body();
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainDisplay.class);
//                    //intent.putExtra("ID",  c_person_id); // getIntenet().getStringExtra("ID") (세트) -> id,토큰값 넘기기
//                    startActivity(intent);
//
//                    Token = result.getAcessToken();
//                }
//                else {
//                    Log.e("통신 에러","코드번호:"+response.code()+",인터넷 연결 이상 발견");
//                    Toast.makeText(getApplicationContext(), "로그인 실패(1)", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Token_Response> call, Throwable t) {
//                Log.e("통신 에러","인터넷 연결 이상 발견");
//                Toast.makeText(getApplicationContext(), "로그인 실패(2)", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private SharedPreferences preferences;
    String getToken;
    String getRefreshToken;


    private EditText person_id , person_password;
    private ImageView login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        person_id = findViewById(R.id.editTextTextPersonName);
        person_password = findViewById(R.id.editTextTextPassword);
        login_btn = findViewById(R.id.login_imageView_btn);

        // 내부 DB 저장 (SharedPreferences 사용, 앱 삭제 시 데이터도 삭제됨.)
        // getSharedPreferences("파일이름", '모드') , MOODE_PRIVATE : 이 앱에서만 허용
        preferences = getSharedPreferences("UserToken", MODE_PRIVATE);


        // 로그인 이미지 클릭시 시작
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = person_id.getText().toString();
                String pw = person_password.getText().toString();
                hideKeyboard();
                
                // id, pw 미입력 시
                if(id.trim().length() == 0 || pw.trim().length() == 0 || id == null || pw == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("알림")
                            .setMessage("로그인 정보를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    tokenPost(new Token_rtf(id,pw)); // 서버 응답
                }
            }
        });
    }

    public void tokenPost(Token_rtf data) {

        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        token_interface = RetrofitClient.getRetrofitInterface();

        // 내부 저장소 사용 선언
        SharedPreferences.Editor editor = preferences.edit();

        token_interface.tokenPost(data).enqueue(new Callback<Token_Response>() {
            // 통신 성공
            @Override
            public void onResponse(Call<Token_Response> call, Response<Token_Response> response) {
                Log.d("통신","통신 연결 확인");

                // response 응답성공 코드 200 확인과 body()가 비어있는지 확인 필요
                if(response.isSuccessful() && response.body() != null) {
                    Token_Response result = response.body();
                    Log.d("통신","통신 연결 성공");

                    // 응답받은 json data 받아오기
                    int stateCode = result.getState(); // 성공 200 실패 400
                    String resultCode = result.getResult(); // success or false
                    String token = result.getData().getAccessToken(); // 토큰
                    String refreshToken = result.getData().getRefreshToken(); // refresh 토큰
                    Integer refreshToken_time = result.getData().getRefreshTokenExpirationTime(); // refresh 토큰의 만료 시간

                    Log.d("통신", "stateCode:" + stateCode + "\n" + "resultCode:" + resultCode + "\n"
                                           + "token:" + token  + "\n" + "refreshToken:" + refreshToken + "\n"
                                           + "refreshToken_time:" + refreshToken_time);

                    editor.putString("token", token);
                    editor.putString("refrshToken", refreshToken);
                    editor.commit();

                    if(stateCode == 200 && resultCode.equals("success")) {
                        String user_id = person_id.getText().toString();
                        String user_pw = person_password.getText().toString();

                        Toast.makeText(LoginActivity.this, user_id + "님 환영합니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainDisplay.class);
                        intent.putExtra("user_Id", user_id); // 화면이 넘어가서도 값이 유지되도록 intent와 함께 id값 넘김
                        intent.putExtra("token",getToken);
                        intent.putExtra("refreshToken",getRefreshToken);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("알림")
                                .setMessage("예기치 못한 오류가 발생하였습니다. 다시 시도해주세요")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                }
                else {
                    Log.e("통신 에러","코드번호:"+response.code()+",인터넷 연결 이상 발견");
                }
            }

        // 로그인 이미지 클릭시 시작
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tokenPost(new Token_rtf(person_id.getText().toString(), person_password.getText().toString()));
//            }
//        });
            // 통신 실패
            @Override
            public void onFailure(Call<Token_Response> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다. 다시 시도해주세요")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    //키보드 숨기기
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(person_id.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(person_password.getWindowToken(), 0);
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
}
