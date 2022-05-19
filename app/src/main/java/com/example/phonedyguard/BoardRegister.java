package com.example.phonedyguard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BoardRegister extends AppCompatActivity {

    private final String BASEURL = "http://3.36.109.233:8089/";
    private EditText title_et, content_et;

    private restful restful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        Button boardwt = (Button) findViewById(R.id.reg_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restful = retrofit.create(restful.class);

        boardwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });
    }

    private void createPost() {

        result post = new result(title_et.getText().toString(), content_et.getText().toString());

        Call<result> call = restful.createPost(post);

        call.enqueue(new Callback<result>() {
            @Override
            public void onResponse(Call<result> call, Response<result> response) {
                if (!response.isSuccessful()) {
                    title_et.setText("code: " + response.code());
                    return;
                }

                result postResponse = response.body();

                String content = "";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Content: " + postResponse.getContent() + "\n";

                title_et.setText(content);
            }

            @Override
            public void onFailure(Call<result> call, Throwable t) {
                content_et.setText(t.getMessage());
            }
        });
    }
}

