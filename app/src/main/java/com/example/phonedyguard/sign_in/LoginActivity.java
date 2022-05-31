package com.example.phonedyguard.sign_in;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText person_id , person_password;
    private ImageView login_btn;

    private Token_interface token_interface;
    private String Token = "";
    private String refreshToken = "";

/*
    // Retrofit 인터페이스 구현
    private void tokenPost(Token_rtf data) {

        token_interface.tokenPost(data).enqueue(new Callback<Token_Response>() {
            @Override
            public void onResponse(Call<Token_Response> call, Response<Token_Response> response) {
                Token_Response result = response.body();

                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainDisplay.class);
                    //intent.putExtra("ID",  c_person_id); // getIntenet().getStringExtra("ID") (세트) -> id,토큰값 넘기기
                    startActivity(intent);

                    Token = result.getAcessToken();
                }
                else {
                    Log.e("통신 에러","코드번호:"+response.code()+",인터넷 연결 이상 발견");
                    Toast.makeText(getApplicationContext(), "로그인 실패(1)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token_Response> call, Throwable t) {
                Log.e("통신 에러","인터넷 연결 이상 발견");
                Toast.makeText(getApplicationContext(), "로그인 실패(2)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        person_id = findViewById(R.id.editTextTextPersonName);
        person_password = findViewById(R.id.editTextTextPassword);
        login_btn = findViewById(R.id.login_imageView_btn);

        // 로그인 토큰 Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.36.109.233/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        token_interface = retrofit.create(Token_interface.class);

        // OkHttp interceptor (토큰 인터셉터)
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

                Request newRequest;

                if(Token != null && !Token.equals("")) { // 토큰이 없는 경우
                    // Authorization 헤더에 토큰 추가
                    newRequest = chain.request().newBuilder().addHeader("Authorization", refreshToken).build();
                    return chain.proceed(newRequest);
                }
                else {
                    newRequest = chain.request();
                    return chain.proceed(newRequest);
                }
            }
        };

        // 로그인 이미지 클릭시 시작
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenPost(new Token_rtf(person_id.getText().toString(), person_password.getText().toString()));
            }
        });
    }

 */
}
