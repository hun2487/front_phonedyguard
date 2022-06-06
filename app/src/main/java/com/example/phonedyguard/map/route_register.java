package com.example.phonedyguard.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phonedyguard.MainActivity;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_out.Logout_interface;
import com.example.phonedyguard.sign_out.Logout_rtf;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class route_register extends AppCompatActivity {

    List<routes> result = new ArrayList<>();
    private map_restful MapRestful;
    private final String BASEURL = "http://3.36.109.233/"; //url
    String token = ((MainDisplay)MainDisplay.context_main).call_token;
    String accessToken = ((MainDisplay) MainDisplay.context_main).origin_token;
    String refreshToken = ((MainDisplay) MainDisplay.context_main).call_refreshtoken;

    private Logout_interface Logout_interface;

    int test = 3;

    boolean flag = false;  //웹페이지 로드 완료 후 실행하기 위해
    private Timer timer;

    Toolbar mytoolbar;

    public void Start_Period() {
        timer = new Timer();
        timer.schedule(addTask , 1000,1000);  // 5초후 실행하고 종료
    }


    public void Stop_Period() {
        //Timer 작업 종료
        if(timer != null) timer.cancel();
    }

    private Handler handler;
    TimerTask addTask = new TimerTask() {
        @Override
        public void run() {
            //주기적으로 실행할 작업 추가
            //웹페이지 로드가 완료되면 실행하고 종료
            if (flag) {
                PostServer();
                flag = false;
                Stop_Period();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_register);
        // 지오코딩(GeoCoding) : 주소,지명 => 위도,경도 좌표로 변환
        //     위치정보를 얻기위한 권한을 획득, AndroidManifest.xml
        //    ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
        //    INTERNET : 구글서버에 접근하기위해서 필요함
        Button safeload_btn = (Button) findViewById(R.id.safeload_btn);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("안심경로설정"); //툴바 타이틀 이름

        final EditText end_edit = (EditText) findViewById(R.id.end_edittext);
        final EditText start_edit = (EditText) findViewById(R.id.start_edittext);
        final Geocoder geocoder = new Geocoder(this);
        WebView mWebView = (WebView) findViewById(R.id.webView);//xml 자바코드 연결

        mWebView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용


        //tmap 명의 JavascriptInterface 를 추가해 줍니다.
        mWebView.addJavascriptInterface(new WebViewJavascriptBridge(), "Android_tmap");
        mWebView.loadUrl("file:///android_asset/tmap.html");

        //------------------------run
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapRestful = retrofit.create(map_restful.class);
        Logout_interface = retrofit.create(Logout_interface.class);


        safeload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 주소입력후 지도2버튼 클릭시 해당 위도경도값의 지도화면으로 이동

                List<Address> start_latlng = null;
                List<Address> end_latlng = null;
                String start_text = start_edit.getText().toString();
                String end_text = end_edit.getText().toString();
                try {
                    start_latlng = geocoder.getFromLocationName(start_text, 10); // 읽을 개수
                    end_latlng = geocoder.getFromLocationName(end_text, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (start_latlng != null && end_latlng != null) {
                    if (start_latlng.size() == 0 & end_latlng.size() == 0) {
                        Log.d("test", "해당되는 주소 정보는 없습니다");
                    } else {                        // 해당되는 주소로 인텐트 날리기
                        Address start = start_latlng.get(0);
                        Address end = end_latlng.get(0);
                        double start_lat = start.getLatitude();
                        double start_lon = start.getLongitude();
                        double end_lat = end.getLatitude();
                        double end_lon = end.getLongitude();
                        Log.d("test", start_lat + ", " + start_lon);
                        Log.d("test", end_lat + ", " + end_lon);
                        //assets에 있는 route.html을 로딩합니다.
                        mWebView.loadUrl("javascript:initTmap(" + start_lat + ", " + start_lon  + ", " + end_lat + ", " + end_lon  + ")");

                        //웹페이지 로드 완료 후 실행하기 위해
                        Start_Period();

                    }
                }
            }
        });
    }

    private void PostServer() {

        Log.d("@@@: ", String.valueOf(test));
        Log.d("@@@: ", String.valueOf(result.size()));
        for(int i = 0; i < result.size(); i++)
        {
            Log.d("@@@: ", "latlng " + result.get(i).getLat() + ", "+ result.get(i).getLng());
        }

        Call<List<routes>> call = MapRestful.postSafeLatlng(token, result);



        call.enqueue(new Callback<List<routes>>() {
            @Override
            public void onResponse(Call<List<routes>> call, Response<List<routes>> response) {
                if (!response.isSuccessful()) {
                    Log.d("@@@: ", String.valueOf(response.code()));
                    return;
                }

            }

            @Override
            public void onFailure(Call<List<routes>> call, Throwable t) {

            }

        });
    }

    class WebViewJavascriptBridge {
        @JavascriptInterface
        public void setTest(final double testx, final double testy) {
            Log.d("test", "jstest" + testx + ", " + testy);
        }

        @JavascriptInterface
        public void getLatLng(final double[] lat, final double[] lng) {


            for(int i=0; i<lat.length; i++)
            {
                result.add(new routes(lat[i], lng[i]));

                //웹페이지 로드 완료 후 실행하기 위해
                flag = true;
            }
        }
    }

    public void deleteToken() {

        Logout_rtf post = new Logout_rtf(accessToken, refreshToken);
        Call<Logout_rtf> call = Logout_interface.deleteData(token, post);
        call.enqueue(new Callback<Logout_rtf>() {
            @Override
            public void onResponse(Call<Logout_rtf> call, Response<Logout_rtf> response) {
            }

            @Override
            public void onFailure(Call<Logout_rtf> call, Throwable t) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bt_logout:
                deleteToken();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
