package com.example.phonedyguard.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Board.BoardActivity;
import com.example.phonedyguard.map.route_register;
import com.example.phonedyguard.map.Tracking;
import com.example.phonedyguard.R;

public class GuardMenu extends AppCompatActivity {

    Button board, set_safe, see_safe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardmenu);

        board = findViewById(R.id.bt_board);
        set_safe = findViewById(R.id.bt_setsafe);
        see_safe = findViewById(R.id.bt_seesafe);

        board.setOnClickListener(new View.OnClickListener() {  //게시판
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        set_safe.setOnClickListener(new View.OnClickListener() {  //안심경로 설정
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), route_register.class);
               startActivity(intent);
            }
        });

        see_safe.setOnClickListener(new View.OnClickListener() {  //안심경로 확인
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tracking.class);
                startActivity(intent);
            }
        });
        }
}

