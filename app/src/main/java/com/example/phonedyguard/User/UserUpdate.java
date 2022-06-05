package com.example.phonedyguard.User;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.R;
import com.example.phonedyguard.Util.RetrofitClient;
import com.example.phonedyguard.Util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserUpdate extends AppCompatActivity {

    private EditText update_name, update_phone;
    private Button update_btn, update_back_btn;
    private RadioButton update_man, update_woman;
    private RadioGroup rg_sex;

    private UserUpdate_interface userUpdate_interface;

    // 성별 리턴 함수
    public String getSex(View view) {
        update_man = findViewById(R.id.update_man_radio);
        update_woman = findViewById(R.id.update_woman_radio);
        String Sex = null;

        if(update_man.isChecked()) {
            Sex = update_man.getText().toString();
        } else if(update_woman.isChecked()) {
            Sex = update_woman.getText().toString();
        }
        return Sex;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userupdate);

        update_name = findViewById(R.id.update_name);
        update_phone = findViewById(R.id.update_phone);
        update_btn = findViewById(R.id.update_btn);

        //내부 저장소를 사용하기위한 전역 Context를 가져옴
        Utils.init(getApplicationContext());

        // 수정 버튼
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ck_name = update_name.getText().toString();
                final String ck_phone = update_phone.getText().toString();
                final String ck_sex = getSex(view);

                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdate.this);
                builder.setTitle("알림"); // 알림 타이틀
                builder.setMessage("정보를 다음과 같이 수정할려고합니다. \n" +
                                   "변경할 이름: " + ck_name + "\n" +
                                   "변경할 전화번호: " + ck_phone + "\n" +
                                   "변경할 성별: " + ck_sex); // 알림의 내용
                /* 알림창: 예, 취소*/
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 예 버튼 누른 경우
                        UserUpdate_rtf data = new UserUpdate_rtf(Utils.getUser_ID(""),ck_name,ck_sex,ck_phone);
                        userUpdate_interface = RetrofitClient.RetrofitClient().create(UserUpdate_interface.class);
                        Call<UserUpdate_Response> call = userUpdate_interface.getUpdate("",data);
                        call.enqueue(new Callback<UserUpdate_Response>() {
                            @Override
                            public void onResponse(Call<UserUpdate_Response> call, Response<UserUpdate_Response> response) {
                                Log.d("통신_userupdate","통신 연결 확인");

                                if(response.isSuccessful() && response.body() != null) {

                                    UserUpdate_Response result = response.body();

                                    int state = result.getState();

                                    if(state == 200) {
                                        Toast.makeText(getApplicationContext(),"User update success",Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdate.this);
                                    builder.setTitle("알림")
                                            .setMessage("정보를 수정하는데 실패하였습니다.(Error)")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserUpdate_Response> call, Throwable t) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdate.this);
                                builder.setTitle("알림")
                                        .setMessage("정보를 수정하는데 실패하였습니다.(Error)")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소 버튼 누른 경우
                        Toast.makeText(getApplicationContext(),"취소했습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        // 뒤로가기 버튼
        update_back_btn = findViewById(R.id.update_back_btn);
        update_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserUpdate.this, UserInfo.class);
                startActivity(intent);
            }
        });
    }
}
