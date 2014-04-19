package com.sdm.sportfit.app.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sdm.sportfit.app.fragments.TrainTrainingFragment;


public class PreferencesManager {

    private static final String PREFS_NAME = "MisPreferenciasEntrenamiento";

    public static final String TIPODEPORTE = "tipodeporte";
    public static final String TIPOAVISO = "tipoaviso";


    public static void guardarConfiguracionDeporte(Context context, String deporte, String aviso){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(editor==null) return;

        editor.putString(TIPODEPORTE, deporte);
        editor.putString(TIPOAVISO, aviso);

        editor.commit();
    }

    public static Bundle restaurarConfiguracionDeporte(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        Bundle bundle = new Bundle();
        bundle.putString(TIPODEPORTE, sharedPreferences.getString(TIPODEPORTE, TrainTrainingFragment.CORRER));
        bundle.putString(TIPOAVISO, sharedPreferences.getString(TIPOAVISO, TrainTrainingFragment.DISTANCIA));

        return bundle;
    }
}
