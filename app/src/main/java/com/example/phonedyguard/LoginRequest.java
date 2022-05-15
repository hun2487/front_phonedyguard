package com.example.phonedyguard;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/* 로그인 값 요청 */

public class LoginRequest extends StringRequest {

    // 서버 URL 설정 , "http://kkh1998.dothome.co.kr/Login.php" <- 기능확인용 개인 테스트 주소(로컬)
    final static private String URL = "http://kkh1998.dothome.co.kr/Login.php";
    private Map<String,String>map;

    public LoginRequest(String ID, String PASSWORD, Response.Listener<String>listener) {
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("ID",ID);
        map.put("PASSWORD",PASSWORD);
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return map;
    }
}
