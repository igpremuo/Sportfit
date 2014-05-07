package com.sdm.sportfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Diet;

import java.util.List;

/**
 * Created by nacho on 9/04/14.
 */
public class DietsListAdapter extends BaseAdapter {

    List<Diet> dietsList;
    Context mContext;

    public DietsListAdapter(Context context, List<Diet> dietsList) {
        this.dietsList = dietsList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return dietsList.size();
    }

    @Override
    public Diet getItem(int position) {
        return dietsList.get(position);
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
            rowView = inflater.inflate(R.layout.listitem_diet, null);
        }

        rowView.setEnabled(true);

        Diet diet =  getItem(position);

        if (diet != null) {

            TextView list_item_diet_calories = (TextView) rowView.findViewById(R.id.list_item_diet_totalcalories);
            TextView list_item_diet_name = (TextView) rowView.findViewById(R.id.list_item_diet_name);
            TextView list_item_diet_description = (TextView) rowView.findViewById(R.id.list_item_diet_description);

            list_item_diet_name.setText(diet.getNameDiet());
            list_item_diet_calories.setText(String.format("%.2f",diet.getTotalCalories()) +" kcal");
            list_item_diet_description.setText(diet.getDescription());
        }

        return rowView;
    }
}
