package com.example.phonedyguard;

/* 회원가입 처리 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText userid, userpassword, userpwck, username, userphonenumber, userbirth;
    private Button sign_up_btn, id_check_btn;
    private RadioButton radio_protector, radio_protege, radio_man, radio_woman;
    //private RadioGroup user_rg_role, user_rg_sex;
    private AlertDialog dialog;
    private boolean validate = false;

    // 보호자 유형 리턴 함수
    public String getRole(View view) {
        radio_protector = findViewById(R.id.protector_radio); // 보호자
        radio_protege = findViewById(R.id.protege_radio); // 피보호자
        String Role = null;

        if(radio_protector.isChecked()) {
            Role = radio_protector.getText().toString();
        } else if(radio_protege.isChecked()) {
            Role = radio_protege.getText().toString();
        }
        return Role;
    }

    // 성별 리턴 함수
    public String getSex(View view) {
        radio_man = findViewById(R.id.man_radio);
        radio_woman = findViewById(R.id.woman_radio);
        String Sex = null;

        if(radio_man.isChecked()) {
            Sex = radio_man.getText().toString();
        } else if(radio_woman.isChecked()) {
            Sex = radio_woman.getText().toString();
        }
        return Sex;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up); // 회원가입 화면

        // 아이디값 찾아주기 (editText)
        userid = findViewById(R.id.userid);
        userpassword = findViewById(R.id.userpassword);
        username = findViewById(R.id.username);
        userphonenumber = findViewById(R.id.userphonenumber);
        userbirth = findViewById(R.id.userbirth);
        userpwck = findViewById(R.id.pwck);

        // 아이디값 찾아주기 (radiogroup)
        //user_rg_role = findViewById(R.id.protection_group);
        //user_rg_sex = findViewById(R.id.sex_group);

        // 아이디 중복 체크
        id_check_btn = findViewById(R.id.id_check_btn); // 아이디 중복 체크 버튼
        id_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check_userid = userid.getText().toString();
                if(validate) {
                    return; // 검증 완료
                }

                if (check_userid.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                userid.setEnabled(false); // 아이디값 고정
                                validate = true; // 검증 성공
                                id_check_btn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(check_userid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });


        // 회원가입 버튼 클릭 시 수행
        sign_up_btn = findViewById(R.id.sign_up_btn); // 회원가입
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // editText
                final String check_userid = userid.getText().toString();
                final String check_userpassword = userpassword.getText().toString();
                final String check_userpwck = userpwck.getText().toString();
                final String check_name = username.getText().toString();
                final String check_phonenumber = userphonenumber.getText().toString();
                final String check_birth = userbirth.getText().toString();

                // Radiobtn : 보호자 유형
                final String check_role = getRole(view);

                // Radiobtn : 성별
                final String check_sex = getSex(view);

                // 아이디 중복 체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                // 한 칸이라도 입력 안한 경우
                if(check_userid.equals("") || check_userpassword.equals("") || check_name.equals("") || check_phonenumber.equals("") || check_birth.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;

                } // 라디오 박스: 보호자 유형을 선택을 안한 경우
                else if(check_role.length() == 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("보호자 유형을 선택해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;

                } // 라디오 박스: 성별 유형을 선택 안한 경우
                else if(check_sex.length() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("성별 유형을 선택해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 회원가입 성공시
                            if(check_userpassword.equals(check_userpwck)) {
                                if(success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", check_userid), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainDisplay.class);
                                    startActivity(intent);

                                    // 회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청
                // RegisterRequest.java 에서 정의한 것을 사용함.
                RegisterRequest registerRequest = new RegisterRequest(check_userid, check_userpassword, check_role, check_phonenumber, check_sex, check_name, check_birth, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
