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
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Chronometer;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.fragments.MainTrainFragment;
import com.sdm.sportfit.app.fragments.TrainTrainingFragment;
import com.sdm.sportfit.app.logic.Points;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.PreferencesManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by juan on 8/04/14.
 */
public class GpsIntentService extends IntentService implements LocationListener{

    public static enum State {RUNNING, PAUSED, STOPPED};

    public static final String NOTIFICATION = "com.sdm.sportfit.app.services.receiver";

    public static final String REPRODUCE_INFO = "com.sdm.sportfit.app.intent.action.REPRODUCE_INFO";

    // Variables de mensajes
    public static final String TIME = "cronometer";
    public static final String DISTANCE = "distance";
    public static final String SPEED = "speed";
    public static final String LOCATION = "location";
    public static final String POINTS = "points";

    // GPS variables
    private final static int MIN_TIME_UPDATE = 3000;
    private final static int MIN_DISTANCE_UPDATE = 0;
    private final static int GPS_ACCURACY = 50;

    // Variables de avisos


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
    private long mAuxTime;

    // Gps variables
    private LocationManager mLocationManager;
    //private GpsListener mLocationListener;
    private TextToSpeech mTextToSpeech;
    private int mCounter;


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
        //mLocationListener = new GpsListener();
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Inicializar variables
        mCronometro = new Chronometer(this);
        mSession = new Trainings();
        mSavedChronoTime = 0;
        mTotalDistance = 0;
        mCurrentSpeed = 0;
        mCounter = 0;

        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTextToSpeech.setLanguage(Locale.getDefault());
                    if (result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        mTextToSpeech.speak(getResources().getString(R.string.service_start_train), TextToSpeech.QUEUE_ADD, null);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });

        run();

        while (!mFinishService) {

            if (mEstadoSuscrito && sState == State.RUNNING) {
                sendDataToFragment();
            }

            sleep(1000);
        }
        sState = State.STOPPED;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        if (location.getAccuracy() != 0 && location.getAccuracy() < GPS_ACCURACY) {
            if (mSession.size() == 0) {
                mSession.add(new Points(location, 0.0, -1));
                mAuxTime = 0;
            } else {
                Points lastPoint = mSession.get(mSession.size() - 1);
                long time = ((SystemClock.elapsedRealtime()-mCronometro.getBase())-mAuxTime)/1000;
                float distance = lastPoint.getLocation().distanceTo(location);
                double speed = (distance/time)*3.6;

                mSession.add(new Points(location, speed, -1));
                mTotalDistance += distance;
                mCurrentSpeed = speed;
                mAuxTime = (SystemClock.elapsedRealtime() - mCronometro.getBase());

                Bundle bundle = PreferencesManager.restaurarConfiguracionDeporte(getApplicationContext());
                String sport = bundle.getString(PreferencesManager.TIPODEPORTE);

                // Reproducir avisos de distancia o tiempo
                if (sport != null && sport.equals(TrainTrainingFragment.DISTANCIA)) {
                    if (mCounter < 1000) {
                        mCounter += distance;
                    } else {
                        mCounter = 0;
                        mTextToSpeech.speak(getAdviceText(), TextToSpeech.QUEUE_ADD, null);
                    }
                } else {
                    if (mCounter < 300) {
                        mCounter += time;
                    } else {
                        mCounter = 0;
                        mTextToSpeech.speak(getAdviceText(), TextToSpeech.QUEUE_ADD, null);
                    }
                }
            }
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

    //Play mCronometro
    private synchronized void run(){
        notify();

        mCronometro.start();
        mCronometro.setBase(SystemClock.elapsedRealtime() - mSavedChronoTime);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);
        mLocationManager.removeUpdates(this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);

        sState = State.RUNNING;
    }

    //Pausar mCronometro
    private void pause(){
        mCronometro.stop();
        mSavedChronoTime = SystemClock.elapsedRealtime() - mCronometro.getBase();

        mLocationManager.removeUpdates(this);

        sState = State.PAUSED;
    }

    //Stop mCronometro
    private synchronized void stop() {
        // Despertar el servicio si está durmiendo
        notify();
        //mCronometro.stop();
        //mCronometro.setBase(SystemClock.elapsedRealtime());
        unregisterReceiver(mReceiver);

        mLocationManager.removeUpdates(this);

        if (mSession.size() > 0) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
            Calendar calendar = Calendar.getInstance();
            mSession.setDate(dateFormat.format(calendar.getTime()));
            mSession.setDistance(mTotalDistance);

            // Calculo de la velocidad media y el ritmo promedio
            double speed = 0;
            for (Points point : mSession) {
                speed += point.getSpeed();
            }
            mSession.setAverageSpeed(speed/mSession.size());

            // Tiempo de sesion
            long time = SystemClock.elapsedRealtime() - mCronometro.getBase();
            if (sState == State.PAUSED) {
                time = mSavedChronoTime;
            }
            mSession.setDuration(time);

            double pace = ((time/1000)/60)/(mTotalDistance/1000);
            mSession.setAverageRate(pace);

            // Guardar sesion
            DatabaseHandler dbHandler = DatabaseHandler.getInstance(getApplicationContext());
            long id = dbHandler.addTraining(mSession);
            for (Points point : mSession) {
                dbHandler.addPoint(point, id);
            }
        }

        sState = State.STOPPED;

        mFinishService = true;

        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void sendDataToFragment(){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(TIME, mCronometro.getBase());
        intent.putExtra(DISTANCE, mTotalDistance);
        intent.putExtra(SPEED, mCurrentSpeed);
        intent.putExtra(LOCATION, mLocation);
        //ArrayList<Points> points = mSession;
        //intent.putExtra(POINTS, points);

        sendBroadcast(intent);
    }

    private synchronized void sleep(int time){
        try {
            wait(time);
        } catch(InterruptedException e) {
            Log.e(getClass().getName(), "Error while sleeping service");
        }
    }

    private String getAdviceText() {
        String text = getResources().getString(R.string.service_advice);
        //Distancia: %.2f kilometros. Tiempo: %d horas, %d minutos, %d segundos. Velocidad: %.2f kilometros por hora.
        long time = SystemClock.elapsedRealtime() - mCronometro.getBase();
        int seconds = (int) (time / 1000) % 60 ;
        int minutes = (int) ((time / (1000*60)) % 60);
        int hours   = (int) ((time / (1000*60*60)));
        text = String.format(text, mTotalDistance/1000, hours, minutes, seconds, mCurrentSpeed);
        return text;
    }

    //Control de los Mensajes
    public class IntentServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(MainTrainFragment.SUBSCRIBIR_SERVICE)) {
                    mEstadoSuscrito = true;
                    sendDataToFragment();
                } else if (intent.getAction().equals(MainTrainFragment.CANCELAR_SUSCRIBIR_SERVICE)) {
                    mEstadoSuscrito = false;
                } else if (intent.getAction().equals(MainTrainFragment.RUN_SERVICE_GPS)) {
                    run();
                } else if (intent.getAction().equals(MainTrainFragment.PAUSE_SERVICE_GPS)) {
                    pause();
                } else if (intent.getAction().equals(MainTrainFragment.STOP_SERVICE_GPS)) {
                    stop();
                } else if (intent.getAction().equals(REPRODUCE_INFO)) {
                    if (mTextToSpeech != null) {
                        mTextToSpeech.speak(getAdviceText(), TextToSpeech.QUEUE_ADD, null);
                    }
                }
            }
        }
    }
}
