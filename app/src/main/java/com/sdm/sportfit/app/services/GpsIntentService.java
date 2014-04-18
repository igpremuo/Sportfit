package com.sdm.sportfit.app.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

    // Variables de mensajes
    public static final String TIME = "cronometer";
    public static final String DISTANCE = "distance";
    public static final String SPEED = "speed";
    public static final String LOCATION = "location";
    public static final String POINTS = "points";

    // GPS variables
    private final static int MIN_TIME_UPDATE = 3000;
    private final static int MIN_DISTANCE_UPDATE = 0;

    private IntentServiceReceiver mReceiver;

    private boolean mFinishService = false;
    private boolean mEstadoSuscrito;

    // Training variables
    public static Trainings mSession;
    private Chronometer mCronometro;
    private Location mLocation;
    private long mSavedChronoTime;
    private double mTotalDistance;
    private double mCurrentSpeed;
    private long mStartTime;
    private long mTime;

    // Gps variables
    private LocationManager mLocationManager;
    private GpsListener mLocationListener;

    public GpsIntentService() {
        super("GpsIntentService");
    }

    public static State sState = State.STOPPED;

    @Override
    protected void onHandleIntent(Intent intent) {
        sState = State.RUNNING;
        mEstadoSuscrito = true;

        // Filtro para las comunicaciones
        IntentFilter filter = new IntentFilter(MainTrainFragment.NOTIFICATION);
        filter.addAction(MainTrainFragment.RUN_SERVICE_GPS);
        filter.addAction(MainTrainFragment.PAUSE_SERVICE_GPS);
        filter.addAction(MainTrainFragment.STOP_SERVICE_GPS);
        filter.addAction(MainTrainFragment.SUBSCRIBIR_SERVICE);
        filter.addAction(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE);
        mReceiver = new IntentServiceReceiver();
        registerReceiver(mReceiver, filter);

        // Gps options
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new GpsListener();
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Inicializar variables
        mCronometro = new Chronometer(this);
        mSession = new Trainings();
        mSavedChronoTime = 0;
        mTotalDistance = 0;
        mCurrentSpeed = 0;

        run();
        while (!mFinishService) {

            if (mEstadoSuscrito && sState == State.RUNNING) {
                sendDataToFragment();
            }

            sleep();
        }
        sState = State.STOPPED;
    }

    //Play mCronometro
    private void run(){
        mCronometro.start();
        mCronometro.setBase(SystemClock.elapsedRealtime() - mSavedChronoTime);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, mLocationListener);

        sState = State.RUNNING;
    }

    //Pausar mCronometro
    private void pause(){
        mCronometro.stop();
        saveChronoTime();

        mLocationManager.removeUpdates(mLocationListener);

        sState = State.PAUSED;
    }

    //Stop mCronometro
    private void stop(){
        mCronometro.stop();
        mCronometro.setBase(SystemClock.elapsedRealtime());
        unregisterReceiver(mReceiver);
        mFinishService = true;

        mLocationManager.removeUpdates(mLocationListener);

        sState = State.STOPPED;

        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //Guardar tiempo del mCronometro
    private void saveChronoTime(){
        mSavedChronoTime = SystemClock.elapsedRealtime() - mCronometro.getBase();
    }

    private void sendDataToFragment(){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(TIME, mCronometro.getBase());
        intent.putExtra(DISTANCE, mTotalDistance);
        intent.putExtra(SPEED, mCurrentSpeed);
        intent.putExtra(LOCATION, mLocation);
        //intent.putExtra(POINTS, mSession);

        sendBroadcast(intent);
    }

    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            Log.e(getClass().getName(), "Error while sleeping service");
        }
    }

    private class GpsListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
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
                mTotalDistance += distance;
                mCurrentSpeed = speed;
                mTime = System.currentTimeMillis();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

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
