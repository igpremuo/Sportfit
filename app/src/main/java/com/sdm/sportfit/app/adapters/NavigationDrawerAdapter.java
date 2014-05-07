package com.sdm.sportfit.app.adapters;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Trainings;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by nacho on 9/04/14.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    List<String> mMenuTitles;
    Context mContext;

    public NavigationDrawerAdapter(Context context, List<String> titleList) {
        mMenuTitles = titleList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMenuTitles.size();
    }

    @Override
    public String getItem(int position) {
        return mMenuTitles.get(position);
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
            rowView = inflater.inflate(R.layout.listitem_navigation_drawer, null);
        }

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView text = (TextView) rowView.findViewById(R.id.title);

        text.setText(getItem(position));

        switch (position) {
            case 0:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_correr));
                break;
            case 1:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_correr));
                break;
            case 2:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_nd_training));
                break;
            case 3:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_correr));
                break;
            case 4:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_correr));
                break;
            case 5:
                image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_correr));
                break;
        }

        return rowView;
    }
}
