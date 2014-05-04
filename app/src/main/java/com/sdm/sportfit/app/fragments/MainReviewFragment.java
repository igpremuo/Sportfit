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


    private TextView name, textViewDayDiet, textViewHeight, textViewWeight,textViewImc, textViewPgc;
    private RadioGroup radioGroup;
    private RadioButton genreMan, genreWoman;
    private Spinner spinnerAge, spinnerPhysicalType;
    private Button save;
    private DatabaseHandler dh;
    private Statistics statistics;
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
        textViewHeight  = (TextView) rootView.findViewById(R.id.textViewHeight);
        textViewWeight  = (TextView) rootView.findViewById(R.id.textViewWeight);
        textViewImc  = (TextView) rootView.findViewById(R.id.textViewImc);
        textViewPgc  = (TextView) rootView.findViewById(R.id.textViewPgc);
        _prefs = this.getActivity().getSharedPreferences("myPreferences", this.getActivity().MODE_PRIVATE);
        _prefsEditor = _prefs.edit();
        dh = dh.getInstance(this.getActivity());
        //Users user = dh.getUser(_prefs.getInt("idUser", 2));
        statistics = dh.getLastStatistic(1);

       // name.setText(user.getName());
        textViewDayDiet.setText("Día " + _prefs.getInt("day", 1));
       // textViewHeight.setText(this.getString(R.string.height) + ": " + statistics.getHeight());
       // textViewWeight.setText(this.getString(R.string.weight) + ": " + statistics.getWeight());
       // textViewImc.setText(this.getString(R.string.imc) + ": " + statistics.getImc());
       // textViewPgc.setText(this.getString(R.string.pgc) + ": " + statistics.getPgc());
        textViewHeight.setText(this.getString(R.string.height) + ": 170 cm");
        textViewWeight.setText(this.getString(R.string.weight) + ": 61 kg");
        textViewImc.setText(this.getString(R.string.imc) + ": 21 %");
        textViewPgc.setText(this.getString(R.string.pgc) + " :15 %");
        return rootView;
    }


}
