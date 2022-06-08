package com.example.phonedyguard.map;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.Board.getBoard;
import com.example.phonedyguard.Board.selectInterface;
import com.example.phonedyguard.MainDisplay;
import com.example.phonedyguard.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tracking extends AppCompatActivity
        implements OnMapReadyCallback
        {
            private final String BASEURL = "http://3.36.109.233/"; //url
            private map_restful mapRestful;
            private GoogleMap mMap;

            //선그리기 좌표
            double start_lat = 0;
            double start_lon = 0;
            double end_lat = 0;
            double end_lon = 0;
            private WebView mWebView;
            List<routes> result = new ArrayList<>();
            boolean webRoad = false;  //웹페이지 로드 완료 후 실행하기 위해

            double lat;
            double lng;
            String token = ((MainDisplay)MainDisplay.context_main).call_token;



            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.tracking);
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);


                Button button = findViewById(R.id.trakingbut);
                //html 파일 실행
                mWebView = (WebView) findViewById (R.id.webView); //xml 자바코드 연결
                mWebView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
                //tmap 명의 JavascriptInterface 를 추가해 줍니다.
                mWebView.addJavascriptInterface(new WebViewJavascriptBridge(), "Android_tmap");
                mWebView.loadUrl("file:///android_asset/getroutes.html");


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mapRestful = retrofit.create(map_restful.class);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Call<safe_routes> saferoute_call = mapRestful.get_saferoutes(token);

                        saferoute_call.enqueue(new Callback<safe_routes>() {
                            @Override
                            public void onResponse(Call<safe_routes> call, Response<safe_routes> response) {
                                if (!response.isSuccessful()) {
                                    Log.d("@@@: ", String.valueOf(response.code()));
                                    return;
                                }

                                safe_routes safeRoutes = response.body(); //post로 값 받아옴

                                start_lat = safeRoutes.getStart_lat();
                                start_lon = safeRoutes.getStart_lon();
                                end_lat = safeRoutes.getEnd_lat();
                                end_lon = safeRoutes.getEnd_lng();

                                Log.d("@@@", ".성공 startx " +safeRoutes.getStart_lat()+ "sy " + safeRoutes.getEnd_lng()
                                        + "e_x " + safeRoutes.getEnd_lat() + "e_y " + safeRoutes.getEnd_lng());
                                mWebView.loadUrl("javascript:initTmap(" + start_lat + ", " + start_lon + ", " + end_lat + ", " + end_lon + ")");
                                TmapThread tmap_thread = new TmapThread();
                                tmap_thread.start();
                            }

                            @Override
                            public void onFailure(Call<safe_routes> call, Throwable t) {
                                Log.d("msg", t.getMessage()); //서버 통신 실패시
                            }
                        });
                    }
                });

            }

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                this.mMap = googleMap;

                mapThread thread = new mapThread(googleMap);
                thread.start();
            }

            class WebViewJavascriptBridge {
                @JavascriptInterface
                public void setTest(final double testx, final double testy) {
                    Log.d("test", "jstest" + testx + ", " + testy);
                }

                @JavascriptInterface
                public void getLatLng(final double[] lat, final double[] lng) {
                    for (int i = 0; i < lat.length; i++) {
                        result.add(new routes(lat[i], lng[i]));
                        webRoad = true;
                    }
                }
            }

            void darwPath(LatLng startLatLng, LatLng endLatLng) {
                PolylineOptions options = new PolylineOptions().add(startLatLng).add(endLatLng).width(5).color(Color.RED).geodesic(true);
                mMap.addPolyline(options);
            }


            public class mapThread extends Thread{

                GoogleMap map = null;

                Marker marker = null;

                public mapThread(GoogleMap _gmap){
                    this.map = _gmap;
                }
                @Override
                public void run(){
                    while(true) {
                        (Tracking.this).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Call<latlng_result> call = mapRestful.latlng_get(token);
                                call.enqueue(new Callback<latlng_result>()  {
                                    @Override
                                    public void onResponse(Call<latlng_result> call, Response<latlng_result> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("통신 :", "통신코드" + String.valueOf(response.code()));
                                            return;
                                        }
                                        if(response.isSuccessful() && response.body() != null) {
                                            latlng_result result = response.body();

                                            //DB에서 현재 위치 받아오기
                                            lat = result.getLat();
                                            lng = result.getLng();
                                            //Log.d("통123신 :", "latlng" + result.getLat() + result.getLng());

                                            // 지도에 마커를 표시한다.
                                            // 지도에 표시되어있는 마커를 모두 제거한다.
                                            MarkerOptions options = new MarkerOptions();
                                            if (marker != null)
                                                marker.remove();
                                            // 위치설정

                                            LatLng pos = new LatLng(lat, lng);
                                            Log.d("@@@", " lat : " + Double.toString(lat) + ", " + Double.toString(lng));
                                            options.position(pos);

                                            marker = map.addMarker(options);
                                            map.moveCamera(CameraUpdateFactory.newLatLng(pos));
                                            map.moveCamera(CameraUpdateFactory.zoomTo(15));
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<latlng_result> call, Throwable t) {
                                        Log.d("통신 실패 : ", t.getMessage());
                                    }
                                });


                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }//Thread end

            public class TmapThread extends Thread {

                public TmapThread( ){ }
                @Override
                public void run() {

                    (Tracking.this).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                if (webRoad) {
                                    for (int i = 0; i < result.size() - 1; i++) {
                                        Log.d("그리기", "" + result.get(i).getLat() + ", " + result.get(i).getLng());
                                        darwPath(new LatLng(result.get(i).getLat(), result.get(i).getLng()),
                                                new LatLng(result.get(i + 1).getLat(), result.get(i + 1).getLng()));
                                    }
                                    webRoad = false;
                                    break;
                                }
                            }// while end

                        }
                    });


                }//run end

            }//Thread end
        }
