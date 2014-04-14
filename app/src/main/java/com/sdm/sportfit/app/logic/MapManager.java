package com.sdm.sportfit.app.logic;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by juan on 14/04/14.
 */
public class MapManager {

    private GoogleMap mMap;

    public MapManager(GoogleMap map) {
        mMap = map;
    }

    public void printRoute() {
        if (mMap != null) {
            PolylineOptions ruta = new PolylineOptions()
                    .add(new LatLng(39.474943,-0.40279))
                    .add(new LatLng(39.474962,-0.402697))
                    .add(new LatLng(39.475071, -0.402123))
                    .add(new LatLng(39.475202,-0.401398))
                    .add(new LatLng(39.475407, -0.400368))
                    .add(new LatLng(39.475771,-0.398558))
                    .add(new LatLng(39.475479, -0.39844))
                    .add(new LatLng(39.475196,-0.398357))
                    .add(new LatLng(39.474722, -0.400706));

            Polyline polyline = mMap.addPolyline(ruta);


            ruta.add(new LatLng(39.474943,-0.40279));
            mMap.addPolyline(ruta);

            LatLng puntoEntrada= new LatLng(39.475407,-0.400368);
            CameraPosition camPos =new CameraPosition.Builder()
                    .target(puntoEntrada)
                    .zoom(15)
                    .bearing(10)
                    .build();
            CameraUpdate camPos2 = CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camPos2);
        }
    }
}
