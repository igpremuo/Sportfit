package com.sdm.sportfit.app.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

/**
 * Created by nacho on 1/04/14.
 */
public class MainReviewFragment extends Fragment {


    private TextView name, textViewDayDiet;
    private RadioGroup radioGroup;
    private RadioButton genreMan, genreWoman;
    private Spinner spinnerAge, spinnerPhysicalType;
    private Button save;
    private DatabaseHandler dh;
    private SharedPreferences _prefs;
    private SharedPreferences.Editor _prefsEditor;
    // Progress Dialog
    private ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_review, container, false);
        name = (TextView) rootView.findViewById(R.id.name);
        textViewDayDiet  = (TextView) rootView.findViewById(R.id.textViewDayDiet);
        _prefs = this.getActivity().getSharedPreferences("myPreferences", this.getActivity().MODE_PRIVATE);
        _prefsEditor = _prefs.edit();
        dh = dh.getInstance(this.getActivity());
        //Users user = dh.getUser(_prefs.getInt("idUser", 2));
        Statistics statistic = dh.getLastStatistic(1);

       // name.setText(user.getName());
        textViewDayDiet.setText("Día " + _prefs.getInt("day", 1));
        return rootView;
    }


}
