package com.sdm.sportfit.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nacho on 1/04/14.
 */
public class MainDietFragment extends Fragment{
    private DatabaseHandler dh;
    private TextView typeMeal, tnameFood, quantity, earnerdCalories;
    private SharedPreferences _prefs;
    private int idDiet, dateDiet;
    private View rootView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_diet, container, false);
        dh = dh.getInstance(this.getActivity());
        _prefs = this.getActivity().getSharedPreferences("Mispreferencias", Context.MODE_PRIVATE);
        idDiet = _prefs.getInt("idDiet",1);
        Log.v("VERBOSE", "VAlor de iddiet " + idDiet);
        dateDiet = _prefs.getInt("dateDate",1);
        Log.v("VERBOSE", "VAlor de datediet  " + dateDiet);
        createRows();
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Crear dieta", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this.getActivity(), DietActivity.class);
            // startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createRows() {
        List<Diets> dietList = new ArrayList<Diets>();
        dietList = dh.getDietByDateAndId(dateDiet, idDiet);
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.dayTable);
        boolean visibleBreak = false, visibleBrunch = false, visibleLunch = false, visibleAfter = false, visibleDinner = false;
        for (int i = 0; i < dietList.size(); i++) {
            Diets diet = dietList.get(i);
            Foods food = dh.getFood(diet.getIdFood());
            if ("Breakfast".equals(diet.getTypeMeal()) || "Desayuno".equals(diet.getTypeMeal())) {
                if (!visibleBreak){
                    TableRow row1 = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this.getActivity());
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this.getActivity());
                    quantity = new TextView(this.getActivity());
                    earnerdCalories = new TextView(this.getActivity());
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTimeMeal() + " " + diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleBreak = true;
                }
                TableRow row = new TableRow(this.getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this.getActivity());
                quantity = new TextView(this.getActivity());
                earnerdCalories = new TextView(this.getActivity());
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                Log.v("VERBOSE", "Valor de cantidad " + diet.getQuantity() + "g");
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Brunch".equals(diet.getTypeMeal()) || "Almuerzo".equals(diet.getTypeMeal())) {
                if (!visibleBrunch){
                    TableRow row1 = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this.getActivity());
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this.getActivity());
                    quantity = new TextView(this.getActivity());
                    earnerdCalories = new TextView(this.getActivity());
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTimeMeal() + " " + diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleBrunch = true;
                }
                TableRow row = new TableRow(this.getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this.getActivity());
                quantity = new TextView(this.getActivity());
                earnerdCalories = new TextView(this.getActivity());
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Lunch".equals(diet.getTypeMeal()) || "Comida".equals(diet.getTypeMeal())) {
                if (!visibleLunch){
                    TableRow row1 = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this.getActivity());
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this.getActivity());
                    quantity = new TextView(this.getActivity());
                    earnerdCalories = new TextView(this.getActivity());
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTimeMeal() + " " + diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleLunch = true;
                }
                TableRow row = new TableRow(this.getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this.getActivity());
                quantity = new TextView(this.getActivity());
                earnerdCalories = new TextView(this.getActivity());
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }


            if ("Afternoon snack".equals(diet.getTypeMeal()) || "Merienda".equals(diet.getTypeMeal())) {
                if (!visibleAfter){
                    TableRow row1 = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this.getActivity());
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this.getActivity());
                    quantity = new TextView(this.getActivity());
                    earnerdCalories = new TextView(this.getActivity());
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTimeMeal() + " " + diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleAfter = true;
                }
                TableRow row = new TableRow(this.getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this.getActivity());
                quantity = new TextView(this.getActivity());
                earnerdCalories = new TextView(this.getActivity());
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Dinner".equals(diet.getTypeMeal()) || "Cena".equals(diet.getTypeMeal())) {
                if (!visibleDinner){
                    TableRow row1 = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this.getActivity());
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this.getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this.getActivity());
                    quantity = new TextView(this.getActivity());
                    earnerdCalories = new TextView(this.getActivity());
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTimeMeal() + " " + diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleDinner = true;
                }
                TableRow row = new TableRow(this.getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this.getActivity());
                quantity = new TextView(this.getActivity());
                earnerdCalories = new TextView(this.getActivity());
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }
        }
    }


}




