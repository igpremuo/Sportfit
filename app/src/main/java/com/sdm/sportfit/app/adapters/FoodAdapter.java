package com.sdm.sportfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Foods;

import java.util.Locale;

/**
 * Created by Jess on 19/04/2014.
 */
public class FoodAdapter  extends BaseAdapter{
        Foods food;
        Context mContext;

        public FoodAdapter(Context context, Foods food) {
            this.food = food;
            mContext = context;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Foods getItem(int position) {
            return food;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.food_dialog, null);
            }

            rowView.setEnabled(true);

            Foods food =  getItem(position);

            if (food != null) {

                TextView listitem_food_name = (TextView) rowView.findViewById(R.id.name);
                TextView listitem_food_calories = (TextView) rowView.findViewById(R.id.calories);
                TextView listitem_food_proteins = (TextView) rowView.findViewById(R.id.proteins);
                TextView listitem_food_carbohydrates = (TextView) rowView.findViewById(R.id.carbohydrates);
                TextView listitem_food_fats = (TextView) rowView.findViewById(R.id.fats);
                TextView listitem_food_water = (TextView) rowView.findViewById(R.id.water);


                if("es".equals(Locale.getDefault().getLanguage()))
                    listitem_food_name.setText(food.getNameES());
                else
                    listitem_food_name.setText(food.getNameEN());
                listitem_food_calories.setText("Calorias: " + String.format("%.2f",food.getCalories()) +" kcal");
                listitem_food_proteins.setText("Proteinas: " + String.format("%.2f",food.getProteins()) + "g / 100g");
                listitem_food_carbohydrates.setText("Carbohidratos: " + String.format("%.2f",  food.getCarbohydrates()) + " g / 100g");
                listitem_food_fats.setText("Grasas: " + String.format("%.2f", food.getFats()) + " g / 100g");
                listitem_food_water.setText("Agua: " + String.format("%.2f", food.getWater()) +" g / 100g");
            }

            return rowView;
        }
    }

