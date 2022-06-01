package com.example.phonedyguard.map;



import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.phonedyguard.Board.BoardActivity;
import com.example.phonedyguard.Board.PostBoard;
import com.example.phonedyguard.Board.listInterface;
import com.example.phonedyguard.Board.registInterface;
import com.example.phonedyguard.R;
import com.example.phonedyguard.sign_up.RegisterActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Navigation extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback
{
    private GpsTracker gpsTracker;
    private GoogleMap mMap;
    private final String BASEURL = "http://3.36.109.233/"; //url
    private map_restful MapRestful;

    Timer timer;
    //시작 좌표
    double start_latitude;
    double start_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gpsTracker = new GpsTracker(Navigation.this);

        start_latitude = gpsTracker.getLatitude();
        start_longitude = gpsTracker.getLongitude();

        setContentView(R.layout.navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //------------------------run
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapRestful = retrofit.create(map_restful.class);

        //----여기서부터 임시
        Button tracking = (Button) findViewById(R.id.tracking);

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop_Period();
                Intent intent = new Intent(getApplicationContext(), Tracking.class);
                startActivity(intent);
            }
        });

    }


    private void createPost() {


        latlng_result latlngResult = new latlng_result(gpsTracker.getLatitude(), gpsTracker.getLongitude());


        Call<latlng_result> call = MapRestful.createPost(latlngResult);

        call.enqueue(new Callback<latlng_result>() {
            @Override
            public void onResponse(Call<latlng_result> call, Response<latlng_result> response) {
                if (!response.isSuccessful()) {
                    Log.d("@@@: ", String.valueOf(response.code()));
                    Log.d("@@@", "실패 lat : " + Double.toString(latlngResult.getLat()) +  " lng" + Double.toString(latlngResult.getLng()));
                    return;
                }

                latlng_result postResponse = response.body();


                latlng_result latlngResponse = response.body(); //post로 값 받아옴

                latlngResponse.getLat();
                latlngResponse.getLng();

                Log.d("@@@", " 성공 lattsese : " + Double.toString(latlngResult.getLat()) +  " lng" + Double.toString(latlngResult.getLng()));
            }

            @Override
            public void onFailure(Call<latlng_result> call, Throwable t) {
                Log.d("msg", t.getMessage()); //서버 통신 실패시
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        LatLng latLng = new LatLng(start_latitude, start_longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermissionWithRationale();
        }
        Start_Period();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    public void Start_Period() {
        timer = new Timer();
        //timer.schedule(adTast , 5000);  // 5초후 실행하고 종료
        //timer.schedule(adTast, 0, 300000); // 0초후 첫실행, 3초마다 계속실행
        timer.schedule(addTask, 0, 5000); //// 0초후 첫실행, Interval분마다 계속실행
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

            createPost();


        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Navigation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        gpsTracker = new GpsTracker(Navigation.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        Toast.makeText(Navigation.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG);
    }
}