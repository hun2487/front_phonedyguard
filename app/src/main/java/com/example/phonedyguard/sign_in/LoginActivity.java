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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.Util.RetrofitClient;
import com.example.phonedyguard.Util.Utils;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Utils.init(getApplicationContext());

        // 안드로이드 고유 기기 토큰 값
        SharedPreferences sharedPreferences_fire = getSharedPreferences("tokenDB_fire", MODE_PRIVATE);
        String gettoken_fire = sharedPreferences_fire.getString("token_fire","");

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
                    tokenPost(new Login_rtf(id,pw,gettoken_fire)); // 서버 응답
                }
            }
        });
    }

    public void tokenPost(Login_rtf data) {

        Login_interface login_interface = RetrofitClient.LoginRetrofitClient().create(Login_interface.class);
        Utils.clearToken(); // 로그인전 내부 저장소 토큰 비움

        Call<Login_Response> call= login_interface.tokenPost(data);
        call.enqueue(new Callback<Login_Response>() {
            // 통신 성공
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                Log.d("통신_login","통신 연결 확인");
                Log.e("통신_login", String.valueOf(response.code()));

                // response 응답성공 코드 200 확인과 body()가 비어있는지 확인 필요
                if(response.isSuccessful() && response.body() != null) {
                    Login_Response result = response.body();
                    Log.d("통신_login","통신 연결 성공");

                    // 응답받은 json data 받아오기
                    int stateCode = result.getState(); // 성공 200 실패 400
                    String resultCode = result.getResult(); // success or false
                    String token = result.getData().getAccessToken(); // 토큰
                    String refreshToken = result.getData().getRefreshToken(); // refresh 토큰
                    Integer refreshToken_time = result.getData().getRefreshTokenExpirationTime(); // refresh 토큰의 만료 시간

                    Log.d("통신_login", "stateCode:" + stateCode + "\n" + "resultCode:" + resultCode + "\n"
                                           + "token:" + token  + "\n" + "refreshToken:" + refreshToken + "\n"
                                           + "refreshToken_time:" + refreshToken_time);

                    if(stateCode == 200 && resultCode.equals("success")) {
                        String user_id = person_id.getText().toString();

                        Toast.makeText(LoginActivity.this, user_id + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainDisplay.class);
                        //intent.putExtra("user_Id", user_id); // 화면이 넘어가서도 값이 유지되도록 intent와 함께 id값 넘김

                        // 내부 저장소에 가져온 토근값 저장
                        Utils.setAccessToken(token);
                        Utils.setRefreshToken(refreshToken);

                        // 다음 화면
                        startActivity(intent);

                        // activity 끝
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
                    Log.e("통신 에러_login","코드번호:"+response.code()+",인터넷 연결 이상 발견");
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("알림")
                            .setMessage("통신 에러")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            // 통신 실패
            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
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
