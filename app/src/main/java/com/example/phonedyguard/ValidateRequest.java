package com.example.phonedyguard;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/* 아이디 중복 확인 */
public class ValidateRequest extends StringRequest {

    // 서버 url 설정 , "http://kkh1998.dothome.co.kr/UserValidate.php" <- 기능 확인용 개인 테스트 주소 (로컬)
    final static  private String URL="http://kkh1998.dothome.co.kr/UserValidate.php";
    private Map<String, String> map;

    public ValidateRequest(String ID, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("ID", ID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
