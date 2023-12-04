package com.example.coffeapplication.Fragments;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewModel extends ViewModel {

    public void getMap(SupportMapFragment mapFragment) {

        MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions markerOptions1 = new MarkerOptions();
        LatLng CoffeeOne = new LatLng(16.056244, 108.221917);
        LatLng CoffeeTwo = new LatLng(16.056244, 108.221917);
        LatLng Moscow = new LatLng(16.056244, 108.221917);
        markerOptions.position(CoffeeOne).title("Đỉa điểm của chúng tôi").snippet("Giờ mở cửa: 6:00 - 22:00");
        markerOptions1.position(CoffeeTwo).title("Địa điểm của chúng tôi").snippet("Giờ mở cửa: 7:30 - 23:30");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(markerOptions);
                googleMap.addMarker(markerOptions1);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Moscow,15));
            }
        });
    }
}
