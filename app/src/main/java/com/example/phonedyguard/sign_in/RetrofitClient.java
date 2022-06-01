package com.example.phonedyguard.sign_in;

import com.example.phonedyguard.User.UserInfo_interface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String baseUrl = "http://3.36.109.233/";
    private static RetrofitClient instance = null;
    private static Token_interface token_interface;
    private static UserInfo_interface userInfo_interface;

    private RetrofitClient() {
        // Log Intercepter
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        
        // retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        token_interface = retrofit.create(Token_interface.class);
        userInfo_interface = retrofit.create(UserInfo_interface.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static Token_interface getRetrofitInterface() {
        return token_interface;
    }
    public static UserInfo_interface getRetrofitInterface2() { return userInfo_interface; }
}
