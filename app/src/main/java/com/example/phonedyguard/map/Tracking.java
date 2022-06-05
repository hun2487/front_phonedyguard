package com.example.phonedyguard.map;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

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

import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tracking extends AppCompatActivity
        implements OnMapReadyCallback
        {
            double lat;
            double lng;
            private final String BASEURL = "http://3.36.109.233/"; //url
            private map_restful mapRestful;
            String token = ((MainDisplay)MainDisplay.context_main).call_token;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.tracking);
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mapRestful = retrofit.create(map_restful.class);

            }

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mapThread thread = new mapThread(googleMap);
                thread.start();
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
                                            // 말풍선이 표시될 값 설정
                                            //options.title(name);
                                            //options.snippet(vicinity);
                                            // 아이콘 설정
                                            //BitmapDescriptor icon= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                                            //options.icon(icon);
                                            // 마커를 지도에 표시한다.
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


        }
