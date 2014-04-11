package com.sdm.sportfit.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.adapters.HistoryListAdapter;
import com.sdm.sportfit.app.logic.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainHistoryFragment extends Fragment {

    private MainActivity mMainActivity;
    private ListView mHistoryList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_history, container, false);

        mHistoryList = (ListView) rootView.findViewById(R.id.train_history_list);

        mHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                Session session = (Session) mHistoryList.getAdapter().getItem(position);
                openReviewFragment(session);
            }
        });

        List<Session> history = new ArrayList<Session>();

        Session session;

        session = new Session();
        session.setRouteType(Session.Type.RUNNING);
        session.setDate("01/04/2014");
        session.setDistance(20.5);
        session.setAvgSpeed(5.5);
        session.setDuration(2165466);
        session.setCalories(700);
        history.add(session);

        session = new Session();
        session.setRouteType(Session.Type.CYCLING);
        session.setDate("02/04/2014");
        session.setDistance(110.5);
        session.setAvgSpeed(40.2);
        session.setDuration(212354566);
        session.setCalories(1200);
        history.add(session);

        session = new Session();
        session.setRouteType(Session.Type.RUNNING);
        session.setDate("03/04/2014");
        session.setDistance(20.5);
        session.setAvgSpeed(3.5);
        session.setDuration(21654566);
        session.setCalories(70.3);
        history.add(session);

        session = new Session();
        session.setRouteType(Session.Type.WALKING);
        session.setDate("04/04/2014");
        session.setDistance(10.5);
        session.setAvgSpeed(2.5);
        session.setDuration(216254566);
        session.setCalories(400);
        history.add(session);

        session = new Session();
        session.setRouteType(Session.Type.CYCLING);
        session.setDate("05/04/2014");
        session.setDistance(200.5);
        session.setAvgSpeed(50.5);
        session.setDuration(2654566);
        session.setCalories(3300);
        history.add(session);

        session = new Session();
        session.setRouteType(Session.Type.WALKING);
        session.setDate("06/04/2014");
        session.setDistance(201.5);
        session.setAvgSpeed(52.5);
        session.setDuration(224566);
        session.setCalories(800);
        history.add(session);

        loadHistoryList(history);

        return rootView;
    }


    private void openReviewFragment(Session session) {
        FragmentManager fragmentManager = getFragmentManager();
        TrainReviewFragment trainReviewFragment = new TrainReviewFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(TrainReviewFragment.ID_KEY, session.getId());
        trainReviewFragment.setArguments(bundle);
        mMainActivity.openFragment(trainReviewFragment);
    }

    private void loadHistoryList(List<Session> history) {
        HistoryListAdapter historyAdapter = new HistoryListAdapter(getActivity().getApplicationContext(), history);
        mHistoryList.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();


    }
}
