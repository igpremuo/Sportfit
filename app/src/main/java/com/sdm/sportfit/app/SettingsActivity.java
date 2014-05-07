package com.sdm.sportfit.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        checkPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
       checkPreferences();
    }

    private void checkPreferences() {
        EditTextPreference userName = (EditTextPreference) findPreference("general_name");
        ListPreference units = (ListPreference) findPreference("general_units");
        ListPreference userGenre = (ListPreference) findPreference("user_genre");
        EditTextPreference userAge = (EditTextPreference) findPreference("user_age");
        ListPreference userActivity = (ListPreference) findPreference("user_activity");
        EditTextPreference hip = (EditTextPreference) findPreference("user_hip");

        userName.setSummary(userName.getText());
        units.setSummary(getResources().getStringArray(R.array.general_units_titles)[Integer.parseInt(units.getValue())]);
        userGenre.setSummary(getResources().getStringArray(R.array.user_genre_titles)[Integer.parseInt(userGenre.getValue())]);
        userAge.setSummary(userAge.getText() +" "+ getResources().getString(R.string.user_age_unit));
        userActivity.setSummary(getResources().getStringArray(R.array.user_activity_titles)[Integer.parseInt(userActivity.getValue())]);
        hip.setEnabled(!userGenre.getValue().equals("0"));
    }
}
