package com.example.phonedyguard.map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.TimerTask;

public class map_timetask extends TimerTask {


    GoogleMap map;
    double trackinglat = 0;
    double trackinglng = 0;

    public map_timetask(@NonNull GoogleMap _map){
        this.map = _map;
    }

    @Override
    public void run() {
        //주기적으로 실행할 작업 추가
        trackinglat += 1;
        trackinglng += 1;
        //Toast.makeText(Navigation.this, "permission denied", Toast.LENGTH_LONG).show();
        Log.d("@@@", " 2lat : " + Double.toString(trackinglat) + " log" + Double.toString(trackinglng));
        LatLng sydney = new LatLng(35.1092592, 28.9593478);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}

