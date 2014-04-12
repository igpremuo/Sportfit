package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainReviewFragment extends Fragment {

    public final static String ID_KEY = "session_id";

    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_review, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Resumen Fragment");*/
        MapsInitializer.initialize(getActivity());

        //mMapView = (MapView) rootView.findViewById(R.id.map);
        //mMapView.onCreate(mBundle);
        //setUpMapIfNeeded(rootView);
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap == null) {
            mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onResume() {
        super.onResume();
       // mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       // mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        //mMapView.onDestroy();
        super.onDestroy();
    }
}
