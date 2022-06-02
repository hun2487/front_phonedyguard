package com.example.phonedyguard.tokenassistance;

import static okhttp3.internal.Internal.instance;

import android.util.Log;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.sign_in.RetrofitClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAssistance {

    private static String baseUrl = "http://3.36.109.233/";
    private static TokenAssistance_interface tokenAssistance_interface;
    private static TokenAssistance instance = null;
    String getDB_token = ((MainDisplay)MainDisplay.context_main).call_token; // 헤더 토큰
    String getDB_refreshToken = ((MainDisplay)MainDisplay.context_main).call_refreshtoken;
    // Integer getDB_refreshToken_time = ((MainDisplay)MainDisplay.context_main).call_refreshtokne_time;
    String newToken;
    String oldToken;

    public TokenAssistance() {
        // Log Intercepter
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        tokenAssistance_interface = retrofit.create(TokenAssistance_interface.class);
        //startAssistance(new TokenAssistance_rtf(getDB_token,getDB_refreshToken));
    }

    public void startAssistance(TokenAssistance_rtf data) {
        tokenAssistance_interface.getRefresh(getDB_token,data).enqueue(new Callback<TokenAssistance_Response>() {

            @Override
            public void onResponse(Call<TokenAssistance_Response> call, Response<TokenAssistance_Response> response) {
                Log.d("통신","서버 연결 성공");
                TokenAssistance_Response result = response.body();
                oldToken = getDB_token; // 조건 확인전 기존 사용한 토큰을 저장

                int state = result.getState();

                if(state == 400) { // access 토큰 만료
                    /*
                    if(!getDB_refreshToken.equals(result.getRefreshToken())) { // access만료 refreshToken 만료된 경우
                        // 사용자 액세스 만료
                        Log.d("통신","사용자 엑세스 만료 앱 종료");
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                    else { // refreshToken은 유효하고 access 토큰은 만료
                        newToken = result.getAccessToken();
                        getDB_token = newToken;
                        ((MainDisplay)MainDisplay.context_main).call_token = getDB_token;
                        Log.d("통신","기존 Token: " + oldToken + "\n" + "새로운 Token: " + newToken);
                    } */
                    Log.d("통신","테스트333333333333332");
                }
                else if(state == 200) { // access 토큰 유효
                    getDB_token = oldToken; // 기존의 토큰 그대로
                    ((MainDisplay)MainDisplay.context_main).call_token = getDB_token;
                    Log.d("통신","기존 Token :" + oldToken + "\n" + "유효토큰: " + getDB_token);
                }
            }

            @Override
            public void onFailure(Call<TokenAssistance_Response> call, Throwable t) {
                Log.d("통신 에러","서버 연결 실패");
            }
        });
    }

    public static TokenAssistance getInstance() {
        if (instance == null) {
            instance = new TokenAssistance();
        }
        return instance;
    }

    public static TokenAssistance_interface getRetrofitInterface_assistance() { return tokenAssistance_interface; }
}
