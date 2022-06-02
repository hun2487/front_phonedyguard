package com.example.phonedyguard.sign_in;

import com.example.phonedyguard.User.UserInfo_interface;
import com.example.phonedyguard.sign_up.Register_interface;
import com.example.phonedyguard.tokenassistance.TokenAssistance_interface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String baseUrl = "http://3.36.109.233/";
    private static RetrofitClient instance = null;
    private static Token_interface token_interface;
    private static UserInfo_interface userInfo_interface;
    private static Register_interface register_interface;

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
        register_interface = retrofit.create(Register_interface.class);

    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static Token_interface getRetrofitInterface_token() {
        return token_interface;
    }
    public static UserInfo_interface getRetrofitInterface_userinfo() { return userInfo_interface; }
    public static Register_interface getRetrofitInterface_register() { return register_interface; }

}
