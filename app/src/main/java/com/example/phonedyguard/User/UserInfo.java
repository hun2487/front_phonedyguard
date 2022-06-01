package com.example.phonedyguard.User;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_in.LoginActivity;
import com.example.phonedyguard.sign_in.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private UserInfo_interface userInfo_interface;

    private TextView user_id, user_name, user_sex, user_phone;
    private Button btn_back;

    String token = ((MainDisplay)MainDisplay.context_main).call_token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        user_id = findViewById(R.id.tv_getMail);
        user_name = findViewById(R.id.tv_getName);
        user_sex = findViewById(R.id.tv_sex);
        user_phone = findViewById(R.id.tv_phone);

    }

    public void startInfo() {

        retrofitClient = RetrofitClient.getInstance();
        userInfo_interface = RetrofitClient.getRetrofitInterface2();

        userInfo_interface.getInfo(token).enqueue(new Callback<UserInfo_Response>() {
            @Override
            public void onResponse(Call<UserInfo_Response> call, Response<UserInfo_Response> response) {
                Log.d("통신","통신 연결 확인");

                if(response.isSuccessful() && response.body() != null) {
                    UserInfo_Response result = response.body();

                    String getEmail = result.getEmail();
                    String getName = result.getName();
                    String getSex = result.getSex();
                    String getPhone = result.getPhone();

                    user_id.setText(getEmail);
                    user_name.setText(getName);
                    user_sex.setText(getSex);
                    user_phone.setText(getPhone);

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
