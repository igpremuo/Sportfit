package com.sdm.sportfit.app.adapters;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.logic.Route;

import java.util.List;

/**
 * Created by nacho on 9/04/14.
 */
public class HistoryListAdapter extends ArrayAdapter<Route> {

    List<Route> mHistoryList;
    Context mContext;

    public HistoryListAdapter(Context context, int resource, List<Route> historyList) {
        super(context, resource, historyList);
        mHistoryList = historyList;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listitem_history, null);
        }

        Route route =  mHistoryList.get(position);

        if (route != null) {
            TextView routeType = (TextView) rowView.findViewById(R.id.listitem_history_type);
            TextView time = (TextView) rowView.findViewById(R.id.listitem_history_time);
            TextView speed = (TextView) rowView.findViewById(R.id.listitem_history_speed);
            TextView calories = (TextView) rowView.findViewById(R.id.listitem_history_calories);

            routeType.setText(mContext.getResources().getString(route.getRouteSringId()));
            time.setText(route.getDuration()+"");
            speed.setText(route.getRouteSringId());
            calories.setText(route.getRouteSringId());

        }

        return rowView;
    }
}
