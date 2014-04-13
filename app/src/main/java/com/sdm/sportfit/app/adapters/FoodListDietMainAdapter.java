package com.sdm.sportfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Foods;

import java.util.List;

/**
 * Created by Jess on 13/04/2014.
 */
public class FoodListDietMainAdapter  extends BaseAdapter  {

        private Context context;
        private List<Foods> foods;

        public FoodListDietMainAdapter(Context context, List<Foods> foods) {
            this.context = context;
            this.foods = foods;
        }

        @Override
        public int getCount() {
            return this.foods.size();
        }

        @Override
        public Object getItem(int position) {
            return this.foods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.items_list_view_meals, null);
            }

            // Set data into the view.
            TextView nameFood = (TextView) rowView.findViewById(R.id.nameFood);
            TextView calories = (TextView) rowView.findViewById(R.id.calories);

            Foods food = this.foods.get(position);
            if ("ES".equals(R.string.language)){
                nameFood.setText(food.getNameES());
            } else {
                nameFood.setText(food.getNameEN());
            }
            calories.setText(String.valueOf(food.getCalories()));
            return rowView;
        }

}

