package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.MapManager;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainReviewFragment extends Fragment {

    public final static String ID_KEY = "session_id";

    private SupportMapFragment mMapFragment;
    MapView mMapView;
    MapManager mMapManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_train_review, container, false);
        /*GoogleMapOptions options = new GoogleMapOptions();
        //options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(false);*/

        // Añadir el fragment a su espacio
        /*mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmenttrain_map_review, mMapFragment);
        fragmentTransaction.commit();*/

        /*mapFragment = new SupportMapFragment().getFragmentManager().beginTransaction()
                .add(R.id.fragmenttrain_map_review,SupportMapFragment).commit();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        getFragmentManager().beginTransaction().add(R.id.fragmenttrain_map,).commit();*/
        //mMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragmenttrain_map);

        mMapView = (MapView) rootView.findViewById(R.id.fragmenttrain_mapview);
        mMapView.onCreate(savedInstanceState);

        mMapManager = new MapManager(mMapView.getMap());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        //mMapFragment.getMap().addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("title"));
        //mapa = mMapFragment.getMap();


        //setUpMapIfNeeded();
       mMapManager.printRoute();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        //getFragmentManager().beginTransaction().remove((SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragmenttrain_map_review)).commit();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

}
