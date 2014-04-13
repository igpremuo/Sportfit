package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 2/04/14.
 */
public class PreferencesActivity extends PreferenceActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
