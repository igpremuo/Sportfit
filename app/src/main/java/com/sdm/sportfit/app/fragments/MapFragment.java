package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
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
public class MapFragment extends Fragment {

    private MapView mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Opciones para el mapa
        GoogleMapOptions options = new GoogleMapOptions();
        //options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(false);

        mMap = new MapView(getActivity(), options);

        return mMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onDestroy() {
        mMap.onDestroy();
        super.onDestroy();
    }

    public void setMarker(MarkerOptions marker) {
        mMap.getMap().addMarker(marker);
    }

    public void showRoute(List<Point> pointList) {
        PolylineOptions polyline = new PolylineOptions();
        polyline.geodesic(true);

        for (Point point : pointList){
            polyline.add(new LatLng(point.getLatitude(),point.getLongitude()));
        }

        mMap.getMap().addPolyline(polyline);
    }
}
