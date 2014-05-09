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
import android.widget.TextView;

import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.adapters.HistoryListAdapter;
import com.sdm.sportfit.app.logic.Trainings;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainHistoryFragment extends Fragment {

    private MainActivity mMainActivity;
    private ListView mHistoryList;
    DatabaseHandler dh;
    //Views
    TextView view_distancia;
    TextView view_speed;
    TextView view_calories;
    TextView view_routes;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_history, container, false);

        //inicio views
        iniciarViews(rootView);

        mHistoryList = (ListView) rootView.findViewById(R.id.train_history_list);

        mHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                Trainings trainings = (Trainings) mHistoryList.getAdapter().getItem(position);
                openReviewFragment(trainings);
            }
        });
        //preparo variables calculos totales
        Trainings session;
        Double distancia=0.0;
        Double velocidad=0.0;
        Double calorias=0.0;
        int rutas=0;
        //Obtiene el historia de actividades
        dh = dh.getInstance(getActivity());
        List<Trainings> history = dh.getAllTrainings();
        //Calcula datos totales
        Iterator iter = history.iterator();
        while (iter.hasNext()){
            session =(Trainings)iter.next();
            distancia= distancia+session.getDistance();
            velocidad=velocidad+session.getAverageSpeed();
            calorias=calorias+session.getCaloriesBurned();
            //System.out.println(iter.next());
        }
        rutas=history.size();
        NumberFormat decimalFormat = new DecimalFormat("###0.00");
        //Muestro datos por pantalla
        view_distancia.setText(decimalFormat.format(distancia/1000)+" Km");
        view_speed.setText(decimalFormat.format(velocidad/rutas)+" Km/h");
        view_calories.setText(decimalFormat.format(calorias)+" Kcal");
        view_routes.setText(String.valueOf(rutas));
        if (!history.isEmpty()){
            loadHistoryList(history);
        }

        return rootView;
    }


    private void openReviewFragment(Trainings session) {
        FragmentManager fragmentManager = getFragmentManager();
        TrainReviewFragment trainReviewFragment = new TrainReviewFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(TrainReviewFragment.ID_KEY, session.getIdTraining());
        trainReviewFragment.setArguments(bundle);
        mMainActivity.openFragment(trainReviewFragment);
    }

    private void loadHistoryList(List<Trainings> history) {
        HistoryListAdapter historyAdapter = new HistoryListAdapter(getActivity().getApplicationContext(), history);
        mHistoryList.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }
    //Iniciar Views
    private void iniciarViews(View rootView){
        view_distancia = (TextView)rootView.findViewById(R.id.train_history_distance);
        view_speed=(TextView) rootView.findViewById(R.id.train_history_speed);
        view_calories=(TextView) rootView.findViewById(R.id.train_history_calories);
        view_routes=(TextView) rootView.findViewById(R.id.train_history_routes);

    }
}
