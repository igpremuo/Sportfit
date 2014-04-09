package com.sdm.sportfit.app.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

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
    public static final String VALOR_CRONOMETRO = "com.sdm.sportfit.app.intent.action.VALOR_CRONOMETRO";
    public static final String FIN_SERVICE_GPS = "com.sdm.sportfit.app.intent.action.FIN_SERVICE_GPS";

    //Variables
    Chronometer cronometro;
    ImageButton play_pause;
    ImageButton stop;
    TextView texto;
    String estado="stop";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_train, container, false);

        //Inicializa los Views
        iniciarViews(rootView);

        //Filtro para el paso de mensajes
        IntentFilter filter = new IntentFilter();
        filter.addAction(GpsIntentService.LISTA_PUNTOS);
        filter.addAction(GpsIntentService.TIEMPO);
        MainTrainReceiver mainRcv = new MainTrainReceiver();
        getActivity().registerReceiver(mainRcv, filter);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(estado.equals("stop")){
                    //Creo el IntentService
                    Intent msgIntent = new Intent(getActivity(), GpsIntentService.class);
                    getActivity().startService(msgIntent);
                    //Le digo que empieze el Cronometro
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PLAY_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    estado="play";
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
                else if(estado.equals("pause")){
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PLAY_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    estado="play";
                    play_pause.setImageResource(R.drawable.ic_pause);

                }
                else if(estado.equals("play")){
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(PAUSE_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    estado="pause";
                    play_pause.setImageResource(R.drawable.ic_play);
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!estado.equals("stop")){
                    Intent bcIntent = new Intent();
                    bcIntent.setAction(STOP_SERVICE_GPS);
                    getActivity().sendBroadcast(bcIntent);
                    //Cambios necesarios en la interfaz
                    estado="stop";
                    play_pause.setImageResource(R.drawable.ic_play);
                }




            }
        });
        return rootView;

    }
    private void iniciarViews(View rootView){
        cronometro = (Chronometer) rootView.findViewById(R.id.chronometer);
        //cronometro.setFormat("H:MM:SS");
        play_pause = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_play);
        stop = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_stop);
        texto =(TextView)rootView.findViewById(R.id.main_train_tv4_distancia);

    }
    //Comprueba si gps esta activo
    private boolean gpsActivado(){
        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return false;
        }
        else return true;
    }

    //Clase para mensajes con Intentservice
    public class MainTrainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(GpsIntentService.LISTA_PUNTOS)) {
                //Aqui va el código que se necesita
            }

                if(intent.getAction().equals(GpsIntentService.TIEMPO)){
                    cronometro.setBase(intent.getLongExtra("tiempo", 0));
                }

        }
    }
}
