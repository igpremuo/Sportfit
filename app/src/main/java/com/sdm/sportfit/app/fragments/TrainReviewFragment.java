package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
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
import com.sdm.sportfit.app.logic.Trainings;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainReviewFragment extends Fragment {


    public final static String ID_KEY = "session_id";

    // Es la "ID" de la sesión y el treining realizado
    private int mIdSession;
    private Trainings mSession;

    //Views del layout
    Chronometer mCronometro;
    ImageView mImageDeporte;
    TextView mDeporte;
    TextView mCalorias;
    TextView mDuracion;
    TextView mDistancia;
    TextView mVelMedia;
    TextView mRitmoPromedio;


    MapView mMapView;
    MapManager mMapManager;


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
        mMapManager = new MapManager(mMapView.getMap());
        //inicio views
        iniciarViews(rootView);
        //obtengo id de sesion desde los argumentos pasados
        Bundle idBundle = getArguments();
        mIdSession =idBundle.getInt(TrainReviewFragment.ID_KEY);


        //pruebas
        obtenerTraining();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        //imprimo mapa
        mMapManager.printRoute();

        //pruebas
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
        mImageDeporte=(ImageView)rootView.findViewById(R.id.iv1);
        mCronometro=(Chronometer)rootView.findViewById(R.id.train_review_chronometer);
        mDeporte=(TextView) rootView.findViewById(R.id.train_review_tv1_deporte);
        mCalorias=(TextView) rootView.findViewById(R.id.train_review_tv4_calorias);
        //mDuracion=(TextView) rootView.findViewById(R.id.train_review_tv2_duracion);
        mDistancia= (TextView) rootView.findViewById(R.id.train_review_tv3_distancia);
        mVelMedia = (TextView) rootView.findViewById(R.id.train_review_tv5_vel_media);
        mRitmoPromedio=(TextView)rootView.findViewById(R.id.train_review_tv6_ritmo_promedio);
    }
    //Carga los datos en los view
    private void ponerDatosViews(Trainings session){
        mDeporte.setText(session.getTypeTraining());
        mCalorias.setText(String.valueOf(session.getCaloriesBurned())+"Kcal");
        //mDuracion.setText(String.valueOf(session.getDuration()));
        mCronometro.setText(String.valueOf(session.getDuration()));
        mDistancia.setText(String.valueOf(session.getDistance())+"Km");
        mVelMedia.setText(String.valueOf(session.getAverageSpeed())+"Km/h");
        mRitmoPromedio.setText(String.valueOf(session.getAverageRate())+"min/Km");
        mImageDeporte.setImageResource(session.getImageId());

    }
    //obtener la session para acceder a sus datos
    private void obtenerTraining(){
        mSession=new Trainings();
        mSession.setTypeTraining("walk");
        mSession.setCaloriesBurned(351.0);
        mSession.setDistance(8.3);
        mSession.setDuration(125);
        mSession.setAverageSpeed(9.8);
        mSession.setAverageRate(5.32);
    }

}
