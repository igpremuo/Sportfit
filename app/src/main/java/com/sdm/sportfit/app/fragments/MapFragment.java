package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sdm.sportfit.app.logic.Point;

import java.util.List;

/**
 * Created by nacho on 13/04/14.
 */
public class MapFragment extends SupportMapFragment {

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap = getMap();
    }

    public void setMarker(MarkerOptions marker) {
        mMap.addMarker(marker);
    }

    public void showRoute(List<Point> pointList) {
        PolylineOptions polyline = new PolylineOptions();
        polyline.geodesic(true);

        for (Point point : pointList){
            polyline.add(new LatLng(point.getLatitude(),point.getLongitude()));
        }

        mMap.addPolyline(polyline);
    }
}
