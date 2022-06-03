package com.example.phonedyguard.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class route {

    @SerializedName("routes")
    private ArrayList<LatLng> latlng;


    public route(ArrayList<LatLng> _latlng)
    {
        this.latlng = _latlng;
    }

    public  ArrayList<LatLng>  getLatLng() {
        return latlng;
    }

}
