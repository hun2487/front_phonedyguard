package com.example.phonedyguard.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class latlng_result {

    @SerializedName("latitude")
    private double lat;
    @SerializedName("longitude")
    private double lng;


    public latlng_result(double LAT, double LAN)
    {
        this.lat = LAT;
        this.lng = LAN;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
