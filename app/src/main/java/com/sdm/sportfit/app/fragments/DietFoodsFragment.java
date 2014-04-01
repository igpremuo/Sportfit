package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 1/04/14.
 */
public class DietFoodsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diet_foods, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Foods Fragment");
        return rootView;
    }
}
