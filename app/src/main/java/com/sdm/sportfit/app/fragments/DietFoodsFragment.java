package com.sdm.sportfit.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.adapters.FoodsListAdapter;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by nacho on 1/04/14.
 */
public class DietFoodsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MainActivity mMainActivity;
    private ListView mFoodsList;
    private Spinner spinnerCategory;
    private DatabaseHandler dh;
    Dialog customDialog = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_diet_foods, container, false);
        spinnerCategory = (Spinner) rootView.findViewById(R.id.spinnerCategory);
        mFoodsList = (ListView) rootView.findViewById(R.id.foods_list);

        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.food_category_array));

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.food_category_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);
        Log.v("VERBOSE", "Paso: " + spinnerCategory.getCount());

        dh = dh.getInstance(getActivity());

        spinnerCategory.setOnItemSelectedListener(this);
       // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText("Foods Fragment");

        mFoodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                Foods foodsSelect = (Foods) mFoodsList.getAdapter().getItem(position);
                Bundle extras = new Bundle();
                extras.putInt("Food", foodsSelect.getId());
                //FoodDialog dialog = new FoodDialog();
                String mensaje = "";

                mensaje += ("Calorias: " + String.format("%.2f",foodsSelect.getCalories()) +" kcal") + "\n";
                mensaje += ("Proteinas: " + String.format("%.2f",foodsSelect.getProteins()) + "g / 100g")+ "\n";
                mensaje += ("Carbohidratos: " + String.format("%.2f",  foodsSelect.getCarbohydrates()) + " g / 100g")+ "\n";
                mensaje += ("Grasas: " + String.format("%.2f", foodsSelect.getFats()) + " g / 100g")+ "\n";
                mensaje += ("Agua: " + String.format("%.2f", foodsSelect.getWater()) +" g / 100g");

                extras.putInt("Food", foodsSelect.getId());
                //FoodDialog dialog = new FoodDialog();
                //DialogFragment dialogFragment = new DialogFragment();
                AlertDialog alertDialog =  new AlertDialog.Builder(getActivity()).create();
                 alertDialog.setCancelable(true);
                if("es".equals(Locale.getDefault().getLanguage())) alertDialog.setTitle((foodsSelect.getNameES()));
                else alertDialog.setTitle(foodsSelect.getNameEN());
                alertDialog.setMessage(mensaje);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }});

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    private void loadFoodsList(List<Foods> foodList) {
        FoodsListAdapter foodsListAdapter = new FoodsListAdapter(getActivity().getApplicationContext(), foodList);
        this.mFoodsList.setAdapter(foodsListAdapter);
        foodsListAdapter.notifyDataSetChanged();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categorySelected = parent.getItemAtPosition(position).toString();

        List<Foods> foods = dh.getAllFoodsByCategory(categorySelected);
        loadFoodsList(foods);
        if (!foods.isEmpty()){
            loadFoodsList(foods);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            // borrar la categoria guardada y poner el list a blanco
    }
}
