package com.example.phonedyguard.map;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class routes {

    @SerializedName("safe_latitude")
    private double lat;

    @SerializedName("safe_longitude")
    private double lng;

    public routes(double lat, double lng)
    {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


}


