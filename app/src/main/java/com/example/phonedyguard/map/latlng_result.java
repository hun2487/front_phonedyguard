package com.example.phonedyguard.map;

import com.google.gson.annotations.SerializedName;

public class latlng_result {

    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
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
