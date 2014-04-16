package com.sdm.sportfit.app.services;

/**
 * Created by Jess on 14/04/2014.
 */
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    private Context mContext;

    public ConnectionDetector(Context context){
        this.mContext = context;
    }

    //Comprueba si hay conexión a internet
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    //Comprueba si gps esta activo
    public boolean isConnectingToGps(){
            LocationManager locManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                return false;
            }
            else return true;

    }
}
