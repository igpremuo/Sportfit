package com.sdm.sportfit.app.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.sdm.sportfit.app.fragments.MainTrainFragment;
import com.sdm.sportfit.app.logic.Points;
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
    private long mStoppedMilliseconds = 0;
    private boolean mEstadoSuscrito;

    private Trainings mSession;
    private double mSpeed;
    private long mStartTime;
    private long mTime;

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
        mSession = new Trainings();

        GpsListener gpsListener = new GpsListener();

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
        mStoppedMilliseconds = SystemClock.elapsedRealtime() - mCronometro.getBase();
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

    private class GpsListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (mSession.size() == 0) {
                mSession.add(new Points(location, 0.0, -1));
                mStartTime = System.currentTimeMillis();
                mTime = mStartTime;
            } else {
                Points lastPoint = mSession.get(mSession.size()-1);
                long time = (System.currentTimeMillis()-mTime)/1000;
                float distance = lastPoint.getLocation().distanceTo(location);
                double speed = (time/distance)*3.6;

                mSession.add(new Points(location, speed, -1 ));
                mTime = System.currentTimeMillis();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

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
