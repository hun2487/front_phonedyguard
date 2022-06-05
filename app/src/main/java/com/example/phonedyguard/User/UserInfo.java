package com.example.phonedyguard.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.Util.MyApp;
import com.example.phonedyguard.Util.RetrofitClient;
import com.example.phonedyguard.Util.Utils;
import com.example.phonedyguard.sign_in.LoginActivity;
import com.example.phonedyguard.sign_up.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {

    private UserInfo_interface userInfo_interface;
    private TextView user_id, user_name, user_sex, user_phone;
    private Button userinfo_back_btn, reset_btn, go_update_btn;


    private String getEmail;
    private String getName;
    private String getSex;
    private String getPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        Utils.init(MyApp.getContext());

        user_id = findViewById(R.id.tv_getMail);
        user_name = findViewById(R.id.tv_getName);
        user_sex = findViewById(R.id.tv_getSex);
        user_phone = findViewById(R.id.tv_getPhone);

        // 뒤로가기 버튼 클릭 시
        userinfo_back_btn = findViewById(R.id.userinfo_back_btn);
        userinfo_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfo.this, MainDisplay.class);
                startActivity(intent);
            }
        });

        // 새로고침 버튼
        reset_btn = findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInfo();
            }
        });

        // 정보수정 버튼
        go_update_btn = findViewById(R.id.go_updateInfo);
        go_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfo.this, UserUpdate.class);
                startActivity(intent);
            }
        });
    }

    public void startInfo() {

        userInfo_interface = RetrofitClient.RetrofitClient().create(UserInfo_interface.class);

        Call<UserInfo_Response> call = userInfo_interface.getInfo("");
        call.enqueue(new Callback<UserInfo_Response>() {
            @Override
            public void onResponse(Call<UserInfo_Response> call, Response<UserInfo_Response> response) {
                Log.d("통신_userinfo","통신 연결 확인");
                Log.d("통신_userinfo","응답 확인 코드" + response.code());
                Log.d("통신_userinfo","현재 토큰:" + Utils.getAccessToken(""));

                if(response.isSuccessful() && response.body() != null) {
                    UserInfo_Response result = response.body();

                    getEmail = result.getData().getEmail();
                    getName = result.getData().getName();
                    getSex = result.getData().getSex();
                    getPhone = result.getData().getPhone();

                    user_id.setText(getEmail);
                    user_name.setText(getName);
                    user_sex.setText(getSex);
                    user_phone.setText(getPhone);

                    Toast.makeText(UserInfo.this, "새로고침", Toast.LENGTH_SHORT).show();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                    builder.setTitle("알림")
                            .setMessage("정보를 불러오는데 실패하였습니다. 다시 시도해주세요.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserInfo_Response> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다. 다시 시도해주세요")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
