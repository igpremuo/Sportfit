package com.sdm.sportfit.app.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.MapManager;
import com.sdm.sportfit.app.services.GpsIntentService;
import com.sdm.sportfit.app.services.GpsIntentService.State;

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
    private ImageButton mPlayPause;
    private ImageButton mStop;
    //Variables
    private MainTrainReceiver mReceiver;

    private SupportMapFragment mMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_train, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.fragmentmain_mapview);
        mMapView.onCreate(savedInstanceState);

        mMapManager = new MapManager(mMapView.getMap());

        //Inicializa los Views
        iniciarViews(rootView);

        //Funcion del boton mPlayPause
        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bcIntent = new Intent(NOTIFICATION);
                switch(GpsIntentService.sState) {
                    case STOPPED:
                        //Me subscribo
                        subscribirService();

                        //Creo el IntentService
                        Intent msgIntent = new Intent(getActivity(), GpsIntentService.class);
                        getActivity().startService(msgIntent);

                        bcIntent.setAction(RUN_SERVICE_GPS);

                        //Cambios necesarios en la interfaz
                        mPlayPause.setImageResource(R.drawable.ic_pause);
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

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

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
        mCronometro = (Chronometer) rootView.findViewById(R.id.chronometer);
        mPlayPause = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_play);
        mStop = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_stop);

    }

    //Comprueba si gps esta activo
    private boolean gpsActivado(){
        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return false;
        }
        else return true;
    }

    /**
     * Clase para recibir los mensajes del servidor
     */
    public class MainTrainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                /*if (intent.getAction().equals(GpsIntentService.LISTA_PUNTOS)) {
                    //Aqui va el código que se necesita
                }
                if (intent.getAction().equals(GpsIntentService.TIEMPO)) {
                    actualizarCronometro(intent.getLongExtra("tiempo", 0));
                }*/
                mCronometro.setBase((bundle.getLong(GpsIntentService.CHRONOMETER, 0)));
            }
        }
    }
}
