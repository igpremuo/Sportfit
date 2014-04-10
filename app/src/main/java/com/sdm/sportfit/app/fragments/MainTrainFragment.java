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

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.services.GpsIntentService;

/**
 * Created by nacho on 1/04/14.
 */
public class MainTrainFragment extends Fragment {

    //Variables para el paso de mensajes
    public static final String DAR_PUNTOS = "com.sdm.sportfit.app.intent.action.DAR_PUNTOS";
    public static final String PLAY_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.PLAY_SERVICE_GPS";
    public static final String PAUSE_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.PAUSE_SERVICE_GPS";
    public static final String STOP_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.STOP_SERVICE_GPS";
    public static final String SUBSCRIBIR_SERVICE = "com.sdm.sportfit.app.intent.action.SUBSCRIBIR_CRONOMETRO";
    public static final String CANCELAR_SUSCRIBIR_SERVICE = "com.sdm.sportfit.app.intent.action.CANCELAR_SUSCRIBIR_CRONOMETRO";
    public static final String FIN_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.FIN_SERVICE_GPS";

    //Variables de estado
    public static final String PLAY="play";
    public static final String PAUSE="pause";
    public static final String STOP="stop";
    private static String sEstado;

    //Views
    Chronometer mCronometro;
    ImageButton mPlayPause;
    ImageButton mStop;
    //Variables
    MainTrainReceiver mMainRcv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_train, container, false);

        //Inicializa los Views
        iniciarViews(rootView);

        if(sEstado == null){
            sEstado = STOP;
        }

        //Funcion del boton mPlayPause
        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sEstado.equals(STOP)) {
                    //Creo el IntentService
                    Intent msgIntent = new Intent(getActivity(), GpsIntentService.class);
                    getActivity().startService(msgIntent);
                    //Le digo que empieze el Cronometro
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PLAY_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    sEstado = PLAY;
                    mPlayPause.setImageResource(R.drawable.ic_pause);
                    //Me subscribo
                    subscribirService();
                } else if (sEstado.equals(PAUSE)) {
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PLAY_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    sEstado = PLAY;
                    mPlayPause.setImageResource(R.drawable.ic_pause);
                    //Me subscribo
                    subscribirService();
                } else if (sEstado.equals(PLAY)) {
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PAUSE_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    sEstado = PAUSE;
                    mPlayPause.setImageResource(R.drawable.ic_play);
                    //cancelo subscribir
                    cancelSubscribirService();
                }
            }
        });

        //Funcion del boton mStop
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!sEstado.equals(STOP)) {
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(STOP_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    sEstado = STOP;
                    mPlayPause.setImageResource(R.drawable.ic_play);
                    //Pone el cronometro a 0
                    mCronometro.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

        //Filtro para el paso de mensajes
        IntentFilter filter = new IntentFilter();
        filter.addAction(GpsIntentService.LISTA_PUNTOS);
        filter.addAction(GpsIntentService.TIEMPO);
        mMainRcv = new MainTrainReceiver();
        getActivity().registerReceiver(mMainRcv, filter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sEstado.equals(PLAY)) {
            mPlayPause.setImageResource(R.drawable.ic_pause);
            subscribirService();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        cancelSubscribirService();
    }

    //Se suscribe al Service para actualizar cronometro
    private void subscribirService(){
        Intent bcIntent = new Intent();
        bcIntent.setAction(SUBSCRIBIR_SERVICE);
        getActivity().sendBroadcast(bcIntent);
    }

    //Se cancela la suscripcion al Service para actualizar cronometro
    private void cancelSubscribirService(){
        Intent bcIntent = new Intent();
        bcIntent.setAction(CANCELAR_SUSCRIBIR_SERVICE);
        getActivity().sendBroadcast(bcIntent);
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

    //Actualiza Cronometro
    private void actualizarCronometro(Long tiempo){
        mCronometro.setBase(tiempo);
    }



    //Clase para mensajes con Intentservice
    public class MainTrainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(GpsIntentService.LISTA_PUNTOS)) {
                //Aqui va el código que se necesita
            }
                if(intent.getAction().equals(GpsIntentService.TIEMPO)){
                    actualizarCronometro(intent.getLongExtra("tiempo", 0));
                }

        }
    }
}
