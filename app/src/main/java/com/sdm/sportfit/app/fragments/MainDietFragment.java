package com.sdm.sportfit.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nacho on 1/04/14.
 */
public class MainDietFragment extends Fragment{
    private Diets diet;
    private DatabaseHandler dh;
    private TextView nameDiet, timeMeal, typeMeal;
    private ListView listViewMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_diet, container, false);
        Log.v("VERBOSE", "Entrando en MainDietFragment ");
        dh = dh.getInstance(this.getActivity());
        nameDiet = (TextView) rootView.findViewById(R.id.nameDietMain);
        timeMeal = (TextView) rootView.findViewById(R.id.timeMealMain);
        typeMeal = (TextView) rootView.findViewById(R.id.typeMealMain);
        listViewMeal = (ListView) rootView.findViewById(R.id.meal);
        //Log.v("VERBOSE", "Entrando al metodo showtMeal");
       // showtMeal();
        return rootView;
    }

    public void showtMeal() {
        Diets meal = new Diets();
        List<Foods> listFoods = new ArrayList<Foods>();
        nameDiet.setText("Dieta genérica 1");
        String typeMealdb = dh.getDietByDate(obtainDateCurrency(),1);
        Log.v("VERBOSE", "typeMealdb : "+typeMealdb);
        List itemse	= new ArrayList();
        meal = dh.getDietByMeal(obtainDateCurrency(), 1,typeMealdb );
        Log.v("VERBOSE","valor de meal.getTimeMeal()"+meal.getTimeMeal());
        if(meal.getTimeMeal() != null) timeMeal.setText(meal.getTimeMeal());
        typeMeal.setText(typeMealdb);
 /*       listFoods = meal.getListFoods();
        for (int i = 0; i < listFoods.size(); i++){
            itemse.add(listFoods.get(i));
        }
        listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), itemse));
 */  /*     if("08:00".equals(obtainTimeCurrency())) {
            List items	= new ArrayList();
            meal = dh.getDietByMeal(obtainDateCurrency(), "Dieta genérica 1", getResources().getString(R.string.breakfast));
            Log.v("VERBOSE", " valor horario: " + meal.getTimeMeal().toString());
            timeMeal.setText(meal.getTimeMeal().toString());
            Log.v("VERBOSE", " valor horario: " + meal.getTypeMeal());
            typeMeal.setText(meal.getTypeMeal());
            listFoods = meal.getListFoods();
            for (int i = 0; i < listFoods.size(); i++){
                items.add(listFoods.get(i));
            }
            listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), items));
        } else if ("10:00".equals(obtainTimeCurrency())) {
            List items	= new ArrayList();
            meal = dh.getDietByMeal(obtainDateCurrency(), "Dieta genérica 1", getResources().getString(R.string.brunch));
            timeMeal.setText(meal.getTimeMeal().toString());
            typeMeal.setText(meal.getTypeMeal());
            listFoods = meal.getListFoods();
            for (int i = 0; i < listFoods.size(); i++){
                items.add(listFoods.get(i));
            }
            listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), items));
        } else if ("12:00".equals(obtainTimeCurrency())) {
            List items	= new ArrayList();
            meal = dh.getDietByMeal(obtainDateCurrency(), "Dieta genérica 1", getResources().getString(R.string.lunch));
            timeMeal.setText(meal.getTimeMeal().toString());
            typeMeal.setText(meal.getTypeMeal());
            listFoods = meal.getListFoods();
            for (int i = 0; i < listFoods.size(); i++){
                items.add(listFoods.get(i));
            }
            listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), items));
        } else if ("17:00".equals(obtainTimeCurrency())) {
            List items	= new ArrayList();
            meal = dh.getDietByMeal(obtainDateCurrency(), "Dieta genérica 1", getResources().getString(R.string.afternoonSnack));
            timeMeal.setText(meal.getTimeMeal().toString());
            typeMeal.setText(meal.getTypeMeal());
            listFoods = meal.getListFoods();
            for (int i = 0; i < listFoods.size(); i++){
                items.add(listFoods.get(i));
            }
            listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), items));
        } else if ("20:00".equals(obtainTimeCurrency())) {
            List items	= new ArrayList();
            meal = dh.getDietByMeal(obtainDateCurrency(), "Dieta genérica 1", getResources().getString(R.string.brunch));
            timeMeal.setText(meal.getTimeMeal().toString());
            typeMeal.setText(meal.getTypeMeal());
            listFoods = meal.getListFoods();
            for (int i = 0; i < listFoods.size(); i++){
                items.add(listFoods.get(i));
            }
           listViewMeal.setAdapter(new FoodListDietMainAdapter(this.getActivity(), items));
        }
    */

    }

    public String obtainTimeCurrency(){
        Calendar c = Calendar.getInstance();
        DateFormat df1 = new SimpleDateFormat("HH:mm");
        String formattedTimeCurrency = df1.format(c.getTime());
        return formattedTimeCurrency;
    }

    public String obtainDateCurrency(){
        Log.v("VERBOSE", "dentro de  obtainDateCurrency");
        Calendar c = Calendar.getInstance();
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateCurrency = df2.format(c.getTime());
        Log.v("VERBOSE", "La fecha es: " + formattedDateCurrency);
       /** Date dateCurrency = null;
        try {
            dateCurrency = df2.parse(formattedDateCurrency);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.v("VERBOSE", "La date a enviar es: " + dateCurrency.toString());*/
        return formattedDateCurrency;
    }


}
