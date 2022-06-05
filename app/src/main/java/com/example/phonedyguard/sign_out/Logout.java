package com.example.phonedyguard.sign_out;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Util.MyApp;
import com.example.phonedyguard.Util.RetrofitClient;
import com.example.phonedyguard.Util.Utils;
import com.example.phonedyguard.sign_in.LoginActivity;
import com.example.phonedyguard.sign_up.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLogout();

    }

    public void startLogout() {
        Utils.init(MyApp.getContext());

        Logout_rtf data = new Logout_rtf(Utils.getAccessToken(""),Utils.getRefreshToken(""));
        Logout_interface logout_interface = RetrofitClient.LoginRetrofitClient().create(Logout_interface.class);
        Call<Logout_Response> call = logout_interface.getLogout("",data);
        call.enqueue(new Callback<Logout_Response>() {
            @Override
            public void onResponse(Call<Logout_Response> call, Response<Logout_Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("통신","통신 연결");
                    Logout_Response result = response.body();

                    int state = result.getState();

                    if(state == 200) {
                        Toast.makeText(getApplicationContext(), String.format("로그아웃 완료"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Logout.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), String.format("Error(1)"), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), String.format("Error(2)"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Logout_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Error(3)"), Toast.LENGTH_SHORT).show();
            }
        });

    }



}


