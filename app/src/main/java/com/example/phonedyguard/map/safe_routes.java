package com.example.phonedyguard.map;

import com.google.gson.annotations.SerializedName;

public class safe_routes {

    @SerializedName("start_lat")
    private double start_lat;

    @SerializedName("start_lon")
    private double start_lon;

    @SerializedName("end_lat")
    private  double end_lat;

    @SerializedName("end_lon")
    private  double end_lng;


    public safe_routes(double start_lat, double start_lon, double end_lat, double end_lng)
    {
        this.start_lat = start_lat;
        this.start_lon = start_lon;
        this.end_lat = end_lat;
        this.end_lng = end_lng;
    }

    public double getStart_lat() {
        return start_lat;
    }

    public double getStart_lon() {
        return start_lon;
    }

    public double getEnd_lat() {
        return end_lat;
    }

    public double getEnd_lng() {
        return end_lng;
    }
}
