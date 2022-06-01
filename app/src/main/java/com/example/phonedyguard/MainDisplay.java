package com.example.phonedyguard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Board.BoardActivity;

public class MainDisplay extends AppCompatActivity {

    public static Context context_main;
    public String call_token;
    Button guardbt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seperate);

        context_main = this;

        TextView id;

        id = findViewById(R.id.test_id);
        guardbt = findViewById(R.id.guardian);

        SharedPreferences sharedPreferences = getSharedPreferences("tokenDB", MODE_PRIVATE);
        String gettoken = sharedPreferences.getString("token","");
        call_token = "Bearer " + gettoken;

        id.setText(gettoken);

        guardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });


    }
}
