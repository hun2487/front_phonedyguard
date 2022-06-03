package com.example.phonedyguard.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Board.BoardActivity;
import com.example.phonedyguard.R;
import com.example.phonedyguard.map.Navigation;

public class WardMenu extends AppCompatActivity {

    Button board, see_safe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wardmenu);

        board = findViewById(R.id.bt_board);
        see_safe = findViewById(R.id.bt_seesafe);

        board.setOnClickListener(new View.OnClickListener() {  //게시판
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        see_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Navigation.class);
                startActivity(intent);
            }
        });
    }

}
