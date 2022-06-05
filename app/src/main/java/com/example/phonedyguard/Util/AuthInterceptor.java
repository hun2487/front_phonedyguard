package com.example.phonedyguard.Util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        AuthToken_interface authToken_interface = RetrofitClient.RetrofitClient().create(AuthToken_interface.class);

        Utils.init(MyApp.getContext());
        String at = Utils.getAccessToken("");
        String rt = Utils.getRefreshToken("");

        Request original = chain.request().newBuilder().header("Authorization", "Bearer " + at).build();
        Response response = chain.proceed(original);

        if(response.code() == 400) { // 응답코드
            Log.d("통신_interceptor","Intercept 진행 : 토큰값 만료. 로그인 API 호출 진행");

            AuthToken_rtf data = new AuthToken_rtf(at,rt);
            retrofit2.Response<AuthToken_Response> token = authToken_interface.getReIssue(data).execute();

            if(token.isSuccessful()) {
                Log.d("통신_interceptor","응답 성공");

                String getToken = token.body().getData().getAccessToken();
                String getRefreshToken = token.body().getData().getRefreshToken();

                Utils.setAccessToken(getToken);
                Utils.setRefreshToken(getRefreshToken);

                Log.d("통신_intercepter","갱신 Token :" + Utils.getAccessToken("") + "\n" + "갱신 refreshtoken :" + Utils.getRefreshToken(""));
                return chain.proceed(original.newBuilder().header("Authorization", "Bearer " + token.body().getData().getAccessToken()).build());
            }
            return chain.proceed(original.newBuilder().header("Authorization", "Bearer " + Utils.getAccessToken("")).build());
        }

        Log.d("통신_intercepter","현재 토큰 : " + Utils.getAccessToken(""));
        Log.e("통신_intercepter", String.valueOf(response.code()));
        return response;
    }
}
