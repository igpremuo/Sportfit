package com.sdm.sportfit.app.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.sdm.sportfit.app.fragments.MainTrainFragment;

/**
 * Created by juan on 8/04/14.
 */
public class GpsIntentService extends IntentService {

    //Variables de mensajes
    public static final String LISTA_PUNTOS = "com.sdm.sportfit.app.intent.action.LISTA_PUNTOS";
    public static final String TIEMPO = "com.sdm.sportfit.app.intent.action.TIEMPO";

    Chronometer mCronometro;
    boolean mCerrarIntentService =false;
    int mStoppedMilliseconds =0;
    gprIntentServiceReceiver mGpsRcv;
    boolean mEstadoSuscrito=false;

    public GpsIntentService() {
        super("GpsIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //filtro para las comunicaciones
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainTrainFragment.DAR_PUNTOS);
        filter.addAction(MainTrainFragment.PLAY_SERVICE_GPS);
        filter.addAction(MainTrainFragment.PAUSE_SERVICE_GPS);
        filter.addAction(MainTrainFragment.STOP_SERVICE_GPS);
        filter.addAction(MainTrainFragment.SUBSCRIBIR_SERVICE);
        filter.addAction(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE);
        filter.addAction(MainTrainFragment.FIN_SERVICE_GPS);
        mGpsRcv = new gprIntentServiceReceiver();
        registerReceiver(mGpsRcv, filter);


        mCronometro = new Chronometer(this);

        while(!mCerrarIntentService){

            if(mEstadoSuscrito == true){
                enviarValorCronometro();
            }
            dormir();
        }
    }
    //Play mCronometro
    private void playCronometro(){
        mCronometro.setBase(SystemClock.elapsedRealtime() - mStoppedMilliseconds);
        mCronometro.start();
    }
    //Pausar mCronometro
    private void pauseCronometro(){
        mCronometro.stop();
        guardarTiempoCronometro();
    }
    //Stop mCronometro
    private void stopCronometro(){
        mCronometro.stop();
        mCronometro.setBase(SystemClock.elapsedRealtime());
        unregisterReceiver(mGpsRcv);
        mCerrarIntentService =true;
    }
    //Guardar tiempo del mCronometro
    private void guardarTiempoCronometro(){
        mStoppedMilliseconds = 0;

        String chronoText = mCronometro.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            mStoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            mStoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }
    }
    //Envia el Valor de Cronometro
    private void enviarValorCronometro(){
        Intent bcIntent = new Intent();
        bcIntent.setAction(TIEMPO);
        bcIntent.putExtra("tiempo", mCronometro.getBase());
        sendBroadcast(bcIntent);
    }
    private void dormir(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

    //Control de los Mensajes
    public class gprIntentServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MainTrainFragment.DAR_PUNTOS)) {
                enviar_lista();
            }
            else if(intent.getAction().equals(MainTrainFragment.SUBSCRIBIR_SERVICE)){
                mEstadoSuscrito = true;
            }
            else if(intent.getAction().equals(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE)){
                mEstadoSuscrito = false;
            }
            else if(intent.getAction().equals(MainTrainFragment.PLAY_SERVICE_GPS)){
                playCronometro();
            }
            else if(intent.getAction().equals(MainTrainFragment.PAUSE_SERVICE_GPS)){
                pauseCronometro();
            }
            else if(intent.getAction().equals(MainTrainFragment.STOP_SERVICE_GPS)){
                stopCronometro();
            }
        }
    }
    private void enviar_lista(){
        Intent bcIntent = new Intent();
        bcIntent.setAction(LISTA_PUNTOS);
        bcIntent.putExtra("lista_puntos","lista_puntos");
        sendBroadcast(bcIntent);
    }


}
