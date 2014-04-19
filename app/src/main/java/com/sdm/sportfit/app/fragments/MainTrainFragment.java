package com.sdm.sportfit.app.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.MapManager;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.persistence.PreferencesManager;
import com.sdm.sportfit.app.services.GpsIntentService;
import com.sdm.sportfit.app.services.GpsIntentService.State;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nacho on 1/04/14.
 */
public class MainTrainFragment extends Fragment {

    public static final String NOTIFICATION = "com.sdm.sportfit.app.main.receiver";

    //Variables para el paso de mensajes
    public static final String RUN_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.RUN_SERVICE_GPS";
    public static final String PAUSE_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.PAUSE_SERVICE_GPS";
    public static final String STOP_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.STOP_SERVICE_GPS";
    public static final String SUBSCRIBIR_SERVICE = "com.sdm.sportfit.app.intent.action.SUBSCRIBIR_CRONOMETRO";
    public static final String CANCELAR_SUSCRIBIR_SERVICE = "com.sdm.sportfit.app.intent.action.CANCELAR_SUSCRIBIR_CRONOMETRO";

    private MapView mMapView;
    private MapManager mMapManager;


    //Views
    private Chronometer mCronometro;
    private TextView mDistance;
    private TextView mSpeed;
    private TextView mSport;
    private TextView mAlert;
    private ImageButton mPlayPause;
    private ImageButton mStop;
    private ImageButton mSetting;
    private ImageView mImageSport;

    //Variables
    private MainTrainReceiver mReceiver;
    String mDeporte;
    String mAviso;
    private SupportMapFragment mMapFragment;
    MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_train, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.fragmentmain_mapview);
        mMapView.onCreate(savedInstanceState);

        mMapManager = new MapManager(mMapView.getMap(),getActivity().getApplicationContext());

        //Inicializa los Views
        iniciarViews(rootView);

        //Funcion del boton mPlayPause
        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bcIntent = new Intent(NOTIFICATION);
                switch(GpsIntentService.sState) {
                    case STOPPED:
                        //Creo el IntentService
                        Intent msgIntent = new Intent(getActivity(), GpsIntentService.class);
                        getActivity().startService(msgIntent);

                        bcIntent.setAction(RUN_SERVICE_GPS);

                        //Cambios necesarios en la interfaz
                        mPlayPause.setImageResource(R.drawable.ic_pause);

                        //Me subscribo
                        subscribirService();
                        break;
                    case PAUSED :
                        bcIntent.setAction(RUN_SERVICE_GPS);

                        //Cambios necesarios en la interfaz
                        mPlayPause.setImageResource(R.drawable.ic_pause);

                        //Me subscribo
                        subscribirService();
                        break;
                    case RUNNING :
                        bcIntent.setAction(PAUSE_SERVICE_GPS);

                        //Cambios necesarios en la interfaz
                        mPlayPause.setImageResource(R.drawable.ic_play);

                        //cancelo subscribir
                        cancelSubscribirService();
                        break;
                }
                getActivity().sendBroadcast(bcIntent);
            }
        });

        //Funcion del boton mStop
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GpsIntentService.sState != State.STOPPED) {
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(STOP_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    GpsIntentService.sState = State.STOPPED;
                    mPlayPause.setImageResource(R.drawable.ic_play);
                    //Pone el cronometro a 0
                    mCronometro.setBase(SystemClock.elapsedRealtime());
                    cancelSubscribirService();
                }
            }
        });

        // Boton de settings
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.openFragmentAtPos(2,1);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        establecerDatosTraining();

        if (GpsIntentService.sState == State.RUNNING) {
            mPlayPause.setImageResource(R.drawable.ic_pause);
            subscribirService();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelSubscribirService();
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

    //Se suscribe al Service para actualizar cronometro
    private void subscribirService(){
        // Avisar al servicio
        Intent bcIntent = new Intent();
        bcIntent.setAction(SUBSCRIBIR_SERVICE);
        getActivity().sendBroadcast(bcIntent);

        // Registrar el recibidor de mensajes
        IntentFilter filter = new IntentFilter(GpsIntentService.NOTIFICATION);
        mReceiver = new MainTrainReceiver();
        getActivity().registerReceiver(mReceiver, filter);
    }

    //Se cancela la suscripcion al Service para actualizar cronometro
    private void cancelSubscribirService(){
        Intent bcIntent = new Intent();
        bcIntent.setAction(CANCELAR_SUSCRIBIR_SERVICE);
        getActivity().sendBroadcast(bcIntent);

        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    //inicialia todos los View necesario de la interfaz
    private void iniciarViews(View rootView){
        mCronometro = (Chronometer) rootView.findViewById(R.id.main_train_chronometer);
        mDistance = (TextView) rootView.findViewById(R.id.main_train_distance);
        mSpeed = (TextView) rootView.findViewById(R.id.main_train_speed);
        mPlayPause = (ImageButton) rootView.findViewById(R.id.main_train_button_play);
        mStop = (ImageButton) rootView.findViewById(R.id.main_train_button_stop);
        mImageSport =(ImageView)rootView.findViewById(R.id.main_train_imageView);
        mSport=(TextView)rootView.findViewById(R.id.main_train_type);
        mAlert=(TextView)rootView.findViewById(R.id.main_train_alert);
        mSetting=(ImageButton)rootView.findViewById(R.id.main_train_button_config);

    }

    //Muestra en los view necesarios el tipo de deporte y aviso
    private void establecerDatosTraining(){
        Bundle datos = PreferencesManager.restaurarConfiguracionDeporte(getActivity());
        mDeporte = datos.getString(PreferencesManager.TIPODEPORTE);
        mAviso =datos.getString(PreferencesManager.TIPOAVISO);

        if(mDeporte.equals(TrainTrainingFragment.ANDAR)){
            mImageSport.setImageResource(R.drawable.ic_walking);
            mSport.setText(getResources().getString(R.string.train_walking));
        }

        else if(mDeporte.equals(TrainTrainingFragment.CORRER)){
            mImageSport.setImageResource(R.drawable.ic_correr);
            mSport.setText(getResources().getString(R.string.train_running));
        }
        else{
            mImageSport.setImageResource(R.drawable.ic_cycling);
            mSport.setText(getResources().getString(R.string.train_cycling));
        }

        if(mAviso.equals(TrainTrainingFragment.DISTANCIA)){
            mAlert.setText(getResources().getString(R.string.alert_distance));
        }
        else mAlert.setText(getResources().getString(R.string.alert_time));
    }

    /**
     * Clase para recibir los mensajes del servidor
     */
    public class MainTrainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mMapManager.clear();
                mCronometro.setBase(bundle.getLong(GpsIntentService.TIME, 0));

                NumberFormat decimalFormat = new DecimalFormat("##0.00");
                mDistance.setText(decimalFormat.format(bundle.getDouble(GpsIntentService.DISTANCE, 0.0)/1000f));
                mSpeed.setText(decimalFormat.format(bundle.getDouble(GpsIntentService.SPEED, 0.0)));

                //Trainings session = (Trainings) bundle.get(GpsIntentService.POINTS);
                Trainings session = GpsIntentService.mSession;
                if (session != null && session.size() > 0) {
                    mMapManager.printRoute(session);
                }

                Location location = (Location ) bundle.get(GpsIntentService.LOCATION);
                if (location != null) {
                    LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                    mMapManager.goToPosition(position, 20, true);
                }
            }
        }
    }
}
