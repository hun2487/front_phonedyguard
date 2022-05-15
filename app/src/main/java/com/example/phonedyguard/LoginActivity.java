package com.example.phonedyguard;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText person_id , person_password;
    private ImageView login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        person_id = findViewById(R.id.editTextTextPersonName);
        person_password = findViewById(R.id.editTextTextPassword);

        login_btn = findViewById(R.id.login_imageView_btn);

        // 로그인 이미지 클릭시 시작
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = person_id.getText().toString();
                String userpassword = person_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

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
                queue.add(loginRequest);
            }
        });
    }
}
