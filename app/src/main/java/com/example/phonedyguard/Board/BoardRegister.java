package com.example.phonedyguard.Board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BoardRegister extends AppCompatActivity {

    private final String BASEURL = "http://3.36.109.233/"; //url
    private EditText title_et, content_et;


    String token = ((MainDisplay)MainDisplay.context_main).call_token;

    private registInterface registInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);

        Button boardwt = (Button) findViewById(R.id.reg_button); //등록 버튼

        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       /* OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Header : ", token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();
*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(okHttpClient)
                .build();

        registInterface = retrofit.create(registInterface.class);

        boardwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });
    }

    private void createPost() {

        PostBoard post = new PostBoard(title_et.getText().toString(), content_et.getText().toString()); //String으로 변환하여 값을 보냄.

        Call<PostBoard> call = registInterface.createPost(token,post);

        call.enqueue(new Callback<PostBoard>() {
            @Override
            public void onResponse(Call<PostBoard> call, Response<PostBoard> response) {
                if (!response.isSuccessful()) {
                    Log.d("code: ", String.valueOf(response.code()));
                    return;
                }

                PostBoard postResponse = response.body(); //post로 값 받아옴

                String content = "";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Content: " + postResponse.getContent() + "\n";

                //title_et.setText(token);
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PostBoard> call, Throwable t) {
                Log.d("msg", t.getMessage()); //서버 통신 실패시
            }
        });
    }
}

