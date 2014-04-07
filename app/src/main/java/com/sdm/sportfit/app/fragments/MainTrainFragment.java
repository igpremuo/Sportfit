package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 1/04/14.
 */
public class MainTrainFragment extends Fragment {

    //Variables
    Chronometer cronometro;
    ImageButton play_pause;
    ImageButton stop;
    String _estado ="parado";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_train, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText("train Fragment");
        iniciarViews(rootView);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cronometro.isActivated()) {
                    cronometro.stop();

                }
                else{
                    cronometro.start();
                }
            }
        });
        return rootView;

    }
    private void iniciarViews(View rootView){
        cronometro = (Chronometer) rootView.findViewById(R.id.chronometer);
        play_pause = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_play);
        stop = (ImageButton) rootView.findViewById(R.id.main_train_Ibutton_stop);

    }
}
