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
 * Created by nacho on 9/04/14.
 */
public class FoodsListAdapter extends BaseAdapter {

    List<Foods> foodsList;
    Context mContext;

    public FoodsListAdapter(Context context, List<Foods> foodsList) {
        this.foodsList = foodsList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Foods getItem(int position) {
        return foodsList.get(position);
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
            rowView = inflater.inflate(R.layout.listitem_food, null);
        }

        rowView.setEnabled(true);

        Foods food =  getItem(position);

        if (food != null) {

            TextView listitem_food_calories = (TextView) rowView.findViewById(R.id.listitem_food_calories);
            TextView listitem_food_name = (TextView) rowView.findViewById(R.id.listitem_food_name);

            listitem_food_name.setText(food.getNameES());
            listitem_food_calories.setText(String.format("%.2f",food.getCalories()) +" kcal");

        }

        return rowView;
    }
}
