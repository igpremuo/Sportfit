package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainReviewFragment extends Fragment {

    public final static String ID_KEY = "session_id";

    private SupportMapFragment mMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_review, container, false);

        GoogleMapOptions options = new GoogleMapOptions();
        //options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(false);

        // Añadir el fragment a su espacio
        mMapFragment = SupportMapFragment.newInstance(options);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmenttrain_map, mMapFragment);
        fragmentTransaction.commit();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mMapFragment.getMap().addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("title"));
    }
}
