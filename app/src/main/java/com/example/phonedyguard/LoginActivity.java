package com.example.phonedyguard;


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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
    private String s_Token;

    // Retrofit 인터페이스 구현
    private void sendPost() {

        Call<Map<String, String>> call = token_interface.sendPost(Token, person_id.getText().toString(), person_password.getText().toString());

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("통신 성공","코드번호:" + response.code() + "토큰값:" + Token);
                    s_Token = Token;
                }
                else {
                    Log.e("통신 에러","코드번호:"+response.code()+",인터넷 연결 이상 발견");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("통신 에러","인터넷 연결 이상 발견");
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
                .baseUrl("http://3.36.109.233:8089/")
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
                    newRequest = chain.request().newBuilder().addHeader("Authorization", Token).build();
                    return chain.proceed(newRequest);
                }
                else {
                    newRequest = chain.request();
                    return chain.proceed(newRequest);
                }
            }
        };

        /*
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build(); */




        // 로그인 이미지 클릭시 시작
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = person_id.getText().toString();
                String userpassword = person_password.getText().toString();

                // Token + id + password 통신
                sendPost();


                // 로그인 Volley
                /*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            // 로그인 성공
                            if(success) {
                                String userid = jsonObject.getString("ID");
                                String userpassword = jsonObject.getString("PASSWORD");
                                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainDisplay.class);
                                intent.putExtra("log", "User");
                                intent.putExtra("ID",userid);
                                startActivity(intent);

                            }
                            // 로그인 실패
                            else {
                                Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                         }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userid, userpassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest); */
            }
        });
    }
}
