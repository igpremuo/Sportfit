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
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.logic.Users;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.UserPreferences;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nacho on 1/04/14.
 */
public class MainReviewFragment extends Fragment {


    private TextView name, textViewDayDiet,textViewAge, textViewHeight, textViewWeight,textViewImc, textViewPgc, textViewWater;
    private TextView textViewCaloriesDiet, textViewCaloriesBurned, textViewDistance;
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
        textViewAge = (TextView) rootView.findViewById(R.id.textViewAge);
        textViewDayDiet  = (TextView) rootView.findViewById(R.id.textViewDayDiet);
        textViewHeight  = (TextView) rootView.findViewById(R.id.textViewHeight);
        textViewWeight  = (TextView) rootView.findViewById(R.id.textViewWeight);
        textViewImc  = (TextView) rootView.findViewById(R.id.textViewImc);
        textViewPgc  = (TextView) rootView.findViewById(R.id.textViewPgc);
        textViewWater = (TextView) rootView.findViewById(R.id.textViewWater);
        textViewCaloriesDiet = (TextView) rootView.findViewById(R.id.textViewCaloriesDiet);
        textViewCaloriesBurned = (TextView) rootView.findViewById(R.id.textViewCaloriesBurned);
        textViewDistance = (TextView) rootView.findViewById(R.id.textViewDistance);

        _prefs = this.getActivity().getSharedPreferences("myPreferences", this.getActivity().MODE_PRIVATE);
        _prefsEditor = _prefs.edit();

        dh = dh.getInstance(this.getActivity());
        Users user = dh.getUser(_prefs.getInt("idUser", 2));
        statistics = dh.getLastStatistic(_prefs.getInt("idUser", 2));

        UserPreferences userPref = new UserPreferences(getActivity());

        name.setText(userPref.getUserName());
        textViewDayDiet.setText("Día " + _prefs.getInt("day", 1));
        textViewAge.setText(this.getString(R.string.user_age) +": " + statistics.getAge());
        textViewHeight.setText(this.getString(R.string.user_height) + ": " + statistics.getHeight());
        textViewWeight.setText(this.getString(R.string.user_weight) + ": " + String.format("%.2f",statistics.getWeight()));
        textViewWater.setText(this.getString(R.string.user_water) + ": " + String.format("%.2f",statistics.getWater()));
        textViewImc.setText(this.getString(R.string.user_imc) + ": " + String.format("%.2f",Double.parseDouble(String.valueOf(statistics.getImc()))));
        textViewPgc.setText(this.getString(R.string.pgc) + ": " + String.format("%.2f",statistics.getPgc()));

        NumberFormat decimalFormat = new DecimalFormat("###0.00");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        Calendar calendar = Calendar.getInstance();

        textViewCaloriesDiet.setText(decimalFormat.format(2000) + " Kcal");

        int idTraining = dh.getIdTrainingByDate(dateFormat.format(calendar.getTime()));
        Trainings training = dh.getTraining(idTraining);

        if (training != null) {
            textViewCaloriesBurned.setText(decimalFormat.format(training.getCaloriesBurned()) + " Kcal");
            textViewDistance.setText(decimalFormat.format(training.getDistance()) + " Km");
        }

        return rootView;
    }


}
