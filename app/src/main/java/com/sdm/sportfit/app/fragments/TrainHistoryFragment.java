package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sdm.sportfit.app.R;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainHistoryFragment extends Fragment {

    ListView mHistoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_history, container, false);

        mHistoryList = (ListView) rootView.findViewById(R.id.train_history_list);

        return rootView;
    }


}
