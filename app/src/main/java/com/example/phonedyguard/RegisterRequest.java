package com.example.phonedyguard;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/* 회원가입 값 요청을 요청 */

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 , "http://kkh1998.dothome.co.kr/Register.php"; <- 기능확인용 개인 테스트 주소(로컬)
    final static private String URL = "http://kkh1998.dothome.co.kr/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String ID, String PASSWORD, String ROLE, String PHONENUMBER, String SEX, String NAME, String BIRTH, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        // 아이디,비밀번호,보호자유형,폰번호,성별,이름,생일

        map = new HashMap<>();
        map.put("ID", ID);
        map.put("PASSWORD", PASSWORD);
        map.put("ROLE", ROLE);
        map.put("PHONENUMBER", PHONENUMBER);
        map.put("SEX", SEX);
        map.put("NAME", NAME);
        map.put("BIRTH", BIRTH);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
