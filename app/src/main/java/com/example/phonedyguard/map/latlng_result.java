package com.example.phonedyguard.map;

import com.google.gson.annotations.SerializedName;

public class latlng_result {

    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public latlng_result(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLat() {
        return latitude;
    }

    public double getLng() {
        return longitude;
    }
}
