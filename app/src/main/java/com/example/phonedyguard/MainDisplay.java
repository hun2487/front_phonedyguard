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
import com.example.phonedyguard.menu.GuardMenu;
import com.example.phonedyguard.menu.WardMenu;

public class MainDisplay extends AppCompatActivity {

    public static Context context_main;
    public String call_token;
    Button guardbt, wardbt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seperate);

        context_main = this;

        guardbt = findViewById(R.id.guardian);
        wardbt = findViewById(R.id.ward);

        SharedPreferences sharedPreferences = getSharedPreferences("tokenDB", MODE_PRIVATE);
        String gettoken = sharedPreferences.getString("token","");
        call_token = "Bearer " + gettoken; //토큰

        guardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuardMenu.class);
                startActivity(intent);
            }
        });

        wardbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WardMenu.class);
                startActivity(intent);
            }
        });
    }
}
