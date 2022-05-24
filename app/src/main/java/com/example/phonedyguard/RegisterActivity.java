package com.example.phonedyguard;

/* 회원가입 처리 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText userid, userpassword, userpwck, username, userphonenumber, userbirth, certification_txt;
    private Button sign_up_btn, id_check_btn, sign_up_back_btn, certification_btn,certification_ck_btn;
    private RadioButton radio_protector, radio_protege, radio_man, radio_woman;
    private AlertDialog dialog;
    private boolean validate = false;
    private boolean cft_validate = false;

    private Certification_interface certification_interface;
    private String certification_num = "";

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

    // Retrofit 인터페이스 구현
    private void sendPost() {
        // 보낼 데이터 저장
        Certification_rtf certification_post = new Certification_rtf(certification_num ,userphonenumber.getText().toString());

        Call<Certification_rtf> call = certification_interface.sendPost(certification_post);

        call.enqueue(new Callback<Certification_rtf>() {
            @Override
            public void onResponse(Call<Certification_rtf> call, retrofit2.Response<Certification_rtf> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Certification_rtf postResponse = response.body();

                    String senddata = "";
                    certification_num = postResponse.getCertificationn_rtf();
                    senddata += postResponse.getPhone_num_rtf(); // 보낼때 2개 받을때 1개 가능 여부 check 필요
                    userphonenumber.setText(senddata);
                }
                else {
                    Log.e("통신 에러","코드번호:"+response.code()+",인터넷 연결 이상 발견");
                }
            }

            @Override
            public void onFailure(Call<Certification_rtf> call, Throwable t) {
                Log.e("통신 에러","인터넷 연결 이상 발견");
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up); // 회원가입 화면

        // 아이디 지정 (editText)
        userid = findViewById(R.id.userid);
        userpassword = findViewById(R.id.userpassword);
        username = findViewById(R.id.username);
        userphonenumber = findViewById(R.id.userphonenumber);
        userbirth = findViewById(R.id.userbirth);
        userpwck = findViewById(R.id.pwck);

        certification_txt = findViewById(R.id.certification_txt);

        // 본인인증 Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.36.109.233:8089/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        certification_interface = retrofit.create(Certification_interface.class);

        // 인증번호 요청 버튼 클릭 시
        certification_btn = findViewById(R.id.certification_btn);
        certification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });

        // 인증번호 확인 버튼 클릭 시
        final String cft_ck_text = certification_txt.getText().toString();
        certification_ck_btn = findViewById(R.id.certification_ck_btn);
        certification_ck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cft_ck_text.equals(certification_num)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("인증 완료").setPositiveButton("확인", null).create();
                    dialog.show();
                    certification_txt.setText(null);
                    cft_validate = true; // 인증 성공
                    certification_ck_btn.setBackgroundColor(getResources().getColor(R.color.colorGray));

                }
            }
        });

        // 뒤로가기 버튼 클릭 시
        sign_up_back_btn = findViewById(R.id.sign_up_back_btn);
        sign_up_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 아이디 중복 체크 클릭 시
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
                    @Override // 서버 통신
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) { // 서버 통신 성공 시
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

                // editText에 작성된 값 가져오기
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

                } // 인증 체크 안했을 경우
                else if(!cft_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("본인 인증을 해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override // 서버 통신
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");


                            if(check_userpassword.equals(check_userpwck)) {
                                if(success) { // 서버 통신 성공 시 (= 회원가입 성공)
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", check_userid), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainDisplay.class);
                                    startActivity(intent);


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
