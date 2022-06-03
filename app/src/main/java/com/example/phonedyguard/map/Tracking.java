package com.example.phonedyguard.map;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonedyguard.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;


public class Tracking extends AppCompatActivity
        implements OnMapReadyCallback
        {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.tracking);
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);


            }

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mapThread thread = new mapThread(googleMap);
                thread.start();
                //Start_Period();
                //mark(googleMap);
            }


            public class mapThread extends Thread{

                GoogleMap map = null;
                double lat = 35.1092767;
                double lng = 128.9592871;

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
                                // 지도에 마커를 표시한다.
                                // 지도에 표시되어있는 마커를 모두 제거한다.
                                MarkerOptions options = new MarkerOptions();
                                if (marker != null)
                                    marker.remove();
                                // 위치설정

                                LatLng pos = new LatLng(lat, lng);
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
                                Log.d("@@@", " 2lat : " + Double.toString(lat) + " log" + Double.toString(lng));
                                lat += 0.0001;
                                lat += 0.0001;
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }


        }
