package com.sdm.sportfit.app.logic;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sdm.sportfit.app.R;

/**
 * Created by juan on 14/04/14.
 */
public class MapManager{

    private GoogleMap mMap;
    private Context mContext;

    public MapManager(GoogleMap map, Context context) {
        try {
            MapsInitializer.initialize(context);
        } catch (Exception impossible) {
            Log.e(getClass().getName(),"Can not initialize google map");
        }
        configureMap(map);
        mMap = map;
        mContext = context;
    }

    private void configureMap(GoogleMap map) {
        UiSettings settings = map.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(false);
    }

    public void printRoute(Trainings training) {
        if (mMap != null) {
            mMap.clear();

            PolylineOptions ruta = new PolylineOptions();
            for(Points point : training) {
                ruta.add(new LatLng(point.getLocation().getLatitude(), point.getLocation().getLongitude()));
            }

            ruta.color(Color.BLUE);
            mMap.addPolyline(ruta);
        }
    }

    public void printFinishedRoute(Trainings training) {
        if (mMap != null) {

            printRoute(training);

            LatLng puntoEntrada= new LatLng(
                    training.get(0).getLocation().getLatitude(),
                    training.get(0).getLocation().getLongitude());

            LatLng puntoFin = new LatLng(
                    training.get(training.size()-1).getLocation().getLatitude(),
                    training.get(training.size()-1).getLocation().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(puntoEntrada)
                    .title(mContext.getResources().getString(R.string.mapmangaer_start)));

            mMap.addMarker(new MarkerOptions()
                    .position(puntoFin)
                    .title(mContext.getResources().getString(R.string.mapmangaer_end)));

            goToPosition(puntoEntrada,16, false);
        }
    }

    public void goToPosition(LatLng location, int zoomlevel, boolean showMarker) {
        CameraPosition camPos =new CameraPosition.Builder()
                .target(location)
                .zoom(zoomlevel)
                //.bearing(10)
                .build();

        if (showMarker) {
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                    .position(location)
                    .flat(true)
                    //.rotation(245)
            );

        }

        CameraUpdate camPos2 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camPos2);
    }

    public void clear() {
        mMap.clear();
    }
}
