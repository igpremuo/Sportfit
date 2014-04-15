package com.sdm.sportfit.app.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.sdm.sportfit.app.fragments.MainTrainFragment;
import com.sdm.sportfit.app.logic.Trainings;

/**
 * Created by juan on 8/04/14.
 */
public class GpsIntentService extends IntentService {

    public static enum State {RUNNING, PAUSED, STOPPED};

    public static final String NOTIFICATION = "com.sdm.sportfit.app.services.receiver";

    //Variables de mensajes
    public static final String CHRONOMETER = "cronometer";
    public static final String POINTS = "points";

    private IntentServiceReceiver mReceiver;

    private Chronometer mCronometro;
    private boolean mCerrarIntentService = false;
    private int mStoppedMilliseconds = 0;
    private boolean mEstadoSuscrito;

    private Trainings session;

    public GpsIntentService() {
        super("GpsIntentService");
    }

    public static State sState = State.STOPPED;

    @Override
    protected void onHandleIntent(Intent intent) {
        sState = State.RUNNING;
        mEstadoSuscrito = true;

        //filtro para las comunicaciones
        IntentFilter filter = new IntentFilter(MainTrainFragment.NOTIFICATION);
        filter.addAction(MainTrainFragment.RUN_SERVICE_GPS);
        filter.addAction(MainTrainFragment.PAUSE_SERVICE_GPS);
        filter.addAction(MainTrainFragment.STOP_SERVICE_GPS);
        filter.addAction(MainTrainFragment.SUBSCRIBIR_SERVICE);
        filter.addAction(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE);
        mReceiver = new IntentServiceReceiver();
        registerReceiver(mReceiver, filter);

        mCronometro = new Chronometer(this);
        session = new Trainings();

        while(!mCerrarIntentService){

            if(mEstadoSuscrito && sState == State.RUNNING){
                sendDataToFragment();
            }
            sleep();
        }
        sState = State.STOPPED;
    }
    //Play mCronometro
    private void run(){
        mCronometro.start();
        mCronometro.setBase(SystemClock.elapsedRealtime() - mStoppedMilliseconds);

        sState = State.RUNNING;
    }
    //Pausar mCronometro
    private void pause(){
        mCronometro.stop();
        guardarTiempoCronometro();

        sState = State.PAUSED;
    }
    //Stop mCronometro
    private void stop(){
        mCronometro.stop();
        mCronometro.setBase(SystemClock.elapsedRealtime());
        unregisterReceiver(mReceiver);
        mCerrarIntentService = true;

        sState = State.STOPPED;
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

    private void sendDataToFragment(){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(CHRONOMETER, mCronometro.getBase());
        sendBroadcast(intent);
    }

    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            Log.e(getClass().getName(), "Error while sleeping");
        }
    }

    //Control de los Mensajes
    public class IntentServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(MainTrainFragment.SUBSCRIBIR_SERVICE)) {
                    mEstadoSuscrito = true;
                } else if (intent.getAction().equals(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE)) {
                    mEstadoSuscrito = false;
                } else if (intent.getAction().equals(MainTrainFragment.RUN_SERVICE_GPS)) {
                    run();
                } else if (intent.getAction().equals(MainTrainFragment.PAUSE_SERVICE_GPS)) {
                    pause();
                } else if (intent.getAction().equals(MainTrainFragment.STOP_SERVICE_GPS)) {
                    stop();
                }
            }
        }
    }
}
