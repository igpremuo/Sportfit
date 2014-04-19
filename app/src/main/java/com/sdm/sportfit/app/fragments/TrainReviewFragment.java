package com.sdm.sportfit.app.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.MapManager;
import com.sdm.sportfit.app.logic.Points;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainReviewFragment extends Fragment {

    public final static String ID_KEY = "session_id";

    // Es la "ID" de la sesión y el treining realizado
    private int mIdSession;
    private Trainings mSession;

    //Views del layout
    private Chronometer mCronometro;
    private ImageView mImageDeporte;
    private TextView mDeporte;
    private TextView mCalorias;
    private TextView mDistancia;
    private TextView mVelMedia;
    private TextView mRitmoPromedio;

    private MapView mMapView;
    private MapManager mMapManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_review, container, false);
        //inicio mapa

        mMapView = (MapView) rootView.findViewById(R.id.fragmenttrain_mapview);
        mMapView.onCreate(savedInstanceState);
        mMapManager = new MapManager(mMapView.getMap(),getActivity().getApplicationContext());

        //inicio views
        iniciarViews(rootView);
        //obtengo id de sesion desde los argumentos pasados
        Bundle idBundle = getArguments();
        mIdSession = idBundle.getInt(TrainReviewFragment.ID_KEY);

        DatabaseHandler dbHandler = DatabaseHandler.getInstance(getActivity().getApplicationContext());
        mSession = dbHandler.getTraining(mIdSession);
        mSession.addAll(dbHandler.getPoints(mIdSession));
;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        ponerDatosViews(mSession);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
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

    //Iniciar Views
    private void iniciarViews(View rootView){
        mImageDeporte = (ImageView)rootView.findViewById(R.id.train_review_img_sport);
        mCronometro = (Chronometer)rootView.findViewById(R.id.train_review_chronometer);
        mDeporte = (TextView) rootView.findViewById(R.id.train_review_sport);
        mCalorias = (TextView) rootView.findViewById(R.id.train_review_calories);
        mDistancia = (TextView) rootView.findViewById(R.id.train_review_distance);
        mVelMedia = (TextView) rootView.findViewById(R.id.train_review_avg_speed);
        mRitmoPromedio = (TextView)rootView.findViewById(R.id.train_review_avg_pace);
    }

    //Carga los datos en los view
    private void ponerDatosViews(Trainings session){
        NumberFormat decimalFormat = new DecimalFormat("###0.00");
        mDeporte.setText(session.getSringId());
        mCalorias.setText(decimalFormat.format(session.getCaloriesBurned()));
        mCronometro.setBase(SystemClock.elapsedRealtime() - session.getDuration());
        mDistancia.setText(decimalFormat.format(session.getDistance()/1000));
        mVelMedia.setText(decimalFormat.format(session.getAverageSpeed()));
        mRitmoPromedio.setText(decimalFormat.format(session.getAverageRate()));
        mImageDeporte.setImageResource(session.getImageId());

        mMapManager.printFinishedRoute(mSession);
    }
}
