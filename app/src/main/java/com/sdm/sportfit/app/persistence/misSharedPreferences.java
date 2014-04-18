package com.sdm.sportfit.app.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sdm.sportfit.app.fragments.TrainTrainingFragment;


public class misSharedPreferences {

    static final String PREFS_NAME = "MisPreferenciasEntrenamiento";
    public static final String TIPODEPORTE = "tipodeporte";
    public static final String TIPOAVISO = "tipoaviso";

    public static void guardarConfiguracionDeporte(Context context,String deporte,String aviso){
        SharedPreferences _share = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor _shareEditor = _share.edit();
        if(_shareEditor==null) return;

        _shareEditor.putString(TIPODEPORTE,deporte);
        _shareEditor.putString(TIPOAVISO, aviso);

        _shareEditor.commit();

    }
    public static Bundle restaurarConfiguracionDeporte(Context context){
        SharedPreferences _share = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        Bundle _info = new Bundle();
        _info.putString(TIPODEPORTE, _share.getString(TIPODEPORTE, TrainTrainingFragment.CORRER));
        _info.putString(TIPOAVISO, _share.getString(TIPOAVISO, TrainTrainingFragment.DISTANCIA));

        return _info;
    }


    public static boolean partidaGuardada(Context context){
        SharedPreferences _share = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        Boolean _guardado = _share.getBoolean("Guardado", false);
        if(_guardado){
            return true;
        }
       else return false;
    }
    public static void salvarEstadoPlay(Context context, int numPregunta, boolean phone, boolean mitad, boolean users,boolean guardado){

        SharedPreferences _share = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor _shareEditor = _share.edit();
        if(_shareEditor==null) return;

        _shareEditor.putBoolean("Guardado", guardado);
        _shareEditor.putInt("Pregunta", numPregunta);
        _shareEditor.putBoolean("phone", phone);
        _shareEditor.putBoolean("mitad", mitad);
        _shareEditor.putBoolean("users", users);

        _shareEditor.commit();
    }
    public static Bundle restaurarEstadoPlay(Context context){
        SharedPreferences _share = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        Bundle _info = new Bundle();
        _info.putBoolean("Guardado",_share.getBoolean("Guardado",false));
        _info.putInt("Pregunta", _share.getInt("Pregunta", 0));
        _info.putBoolean("phone", _share.getBoolean("phone", false));
        _info.putBoolean("mitad", _share.getBoolean("mitad", false));
        _info.putBoolean("users", _share.getBoolean("users", false));

        return _info;
    }



}
