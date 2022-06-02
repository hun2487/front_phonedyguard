package com.example.phonedyguard.sign_up;

/* 회원가입 처리 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_in.LoginActivity;
import com.example.phonedyguard.sign_in.RetrofitClient;
import com.example.phonedyguard.sign_in.Token_interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText userid, userpassword, userpwck, username, userphonenumber;
    private Button sign_up_btn, sign_up_back_btn;
    private RadioButton  radio_man, radio_woman;
    private AlertDialog dialog;

    private RetrofitClient retrofitClient;
    private Register_interface register_interface;

    // 성별 리턴 함수
    public String getSex(View view) {
        radio_man = findViewById(R.id.man_radio);
        radio_woman = findViewById(R.id.woman_radio);
        String Sex = null;

        if(radio_man.isChecked()) {
            Sex = radio_man.getText().toString();
        } else if(radio_woman.isChecked()) {
            Sex = radio_woman.getText().toString();
        }
        return Sex;
    }

    // register Retrofit
    private void registerPost(Register_rtf data) {

        retrofitClient = RetrofitClient.getInstance();
        register_interface = RetrofitClient.getRetrofitInterface_register();

        register_interface.registerPost(data).enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("통신","통신 연결");

                    Register_Response result = response.body();

                    int state = result.getState();

                    if(state == 200) {
                        Toast.makeText(getApplicationContext(), String.format("회원가입 완료. 로그인을 진행해주세요."), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "중복된 ID 입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "예기치 못한 문제 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up); // 회원가입 화면

        // 아이디 지정 (editText)
        userid = findViewById(R.id.userid);
        userpassword = findViewById(R.id.userpassword);
        username = findViewById(R.id.username);
        userphonenumber = findViewById(R.id.userphonenumber);
        userpwck = findViewById(R.id.pwck);

        // 뒤로가기 버튼 클릭 시
        sign_up_back_btn = findViewById(R.id.sign_up_back_btn);
        sign_up_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입 버튼 클릭 시 수행
        sign_up_btn = findViewById(R.id.sign_up_btn); // 회원가입
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText에 작성된 값 가져오기
                final String check_userid = userid.getText().toString();
                final String check_userpassword = userpassword.getText().toString();
                final String check_userpwck = userpwck.getText().toString();
                final String check_name = username.getText().toString();
                final String check_phonenumber = userphonenumber.getText().toString();

                // Radiobtn : 성별
                final String check_sex = getSex(view);

                // 한 칸이라도 입력 안한 경우
                if (check_userid.equals("") || check_userpassword.equals("") || check_name.equals("") || check_phonenumber.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                // 라디오 박스: 성별 유형을 선택 안한 경우
                else if (check_sex.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("성별 유형을 선택해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                // 비밀번호와 비밀번호 확인이 같아야 회원가입 가능
                if (check_userpassword.equals(check_userpwck)) {
                    registerPost(new Register_rtf(check_userid, check_userpassword, check_name, check_sex, check_phonenumber));
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });
    }
}
