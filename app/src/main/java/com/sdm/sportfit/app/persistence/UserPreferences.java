package com.sdm.sportfit.app.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 9/05/14.
 */
public class UserPreferences {

    public final static String PREFERENCES_FILE = "com.sdm.sportfit.app_preferences";

    private final static String DISTANCE_UNITS = "general_units";
    private final static String USER_NAME = "general_name";
    private final static String USER_GENRE = "user_genre";
    private final static String USER_AGE = "user_age";
    private final static String USER_ACTIVITY = "user_activity";
    private final static String USER_HEIGHT = "user_height";
    private final static String USER_WEIGHT = "user_weight";
    private final static String USER_IMC = "user_imc";
    private final static String USER_MGC = "user_mgc";
    private final static String USER_NECK = "user_neck";
    private final static String USER_WAIST = "user_waist";
    private final static String USER_WATER = "user_water";
    private final static String USER_HIP = "user_hip";

    private SharedPreferences mPrefs;
    private Context mContext;

    public UserPreferences(Context context) {
        mContext = context;
        mPrefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public String getUserDistanceUnits() {
        int pos = Integer.parseInt(mPrefs.getString(DISTANCE_UNITS, "0"));
        return mContext.getResources().getStringArray(R.array.user_genre_titles) [pos];
    }

    public String getUserName() {
        return mPrefs.getString(USER_NAME, mContext.getString(R.string.general_name));
    }

    public String getUserGenre() {
        int pos = Integer.parseInt(mPrefs.getString(USER_GENRE, "0"));
        return mContext.getResources().getStringArray(R.array.user_genre_titles) [pos];
    }

    public int getUserAge() {
        return Integer.parseInt(mPrefs.getString(USER_AGE, "20"));
    }

    public String getUserActivity() {
        int pos = Integer.parseInt(mPrefs.getString(USER_ACTIVITY, "0"));
        return mContext.getResources().getStringArray(R.array.user_activity_titles) [pos];
    }

    public int getUserHeight() {
        return Integer.parseInt(mPrefs.getString(USER_HEIGHT, "170"));
    }

    public int getUserWeight() {
        return Integer.parseInt(mPrefs.getString(USER_WEIGHT, "75"));
    }

    public int getUserImc() {
        return Integer.parseInt(mPrefs.getString(USER_IMC, "20"));
    }

    public int getUserMgc() {
        return Integer.parseInt(mPrefs.getString(USER_MGC, "20"));
    }

    public int getUserNeck() {
        return Integer.parseInt(mPrefs.getString(USER_NECK, "25"));
    }

    public int getUserWaist() {
        return Integer.parseInt(mPrefs.getString(USER_WAIST, "60"));
    }

    public int getUserWater() {
        return Integer.parseInt(mPrefs.getString(USER_WATER, "70"));
    }

    public int getUserHip() {
        return Integer.parseInt(mPrefs.getString(USER_HIP, "10"));
    }

}
