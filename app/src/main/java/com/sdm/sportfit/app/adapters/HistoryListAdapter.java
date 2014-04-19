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
public class HistoryListAdapter extends BaseAdapter {

    List<Trainings> mHistoryList;
    Context mContext;

    public HistoryListAdapter(Context context, List<Trainings> historyList) {
        mHistoryList = historyList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mHistoryList.size();
    }

    @Override
    public Trainings getItem(int position) {
        return mHistoryList.get(position);
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
            rowView = inflater.inflate(R.layout.listitem_history, null);
        }

        rowView.setEnabled(true);

        Trainings session =  getItem(position);

        if (session != null) {
            NumberFormat decimalFormat = new DecimalFormat("###0.00");

            ImageView image = (ImageView) rowView.findViewById(R.id.listitem_history_icon);
            TextView sessionType = (TextView) rowView.findViewById(R.id.listitem_history_type);
            Chronometer time = (Chronometer) rowView.findViewById(R.id.listitem_history_time);
            TextView distance = (TextView) rowView.findViewById(R.id.listitem_history_distance);
            TextView date = (TextView) rowView.findViewById(R.id.listitem_history_date);

            image.setImageDrawable(mContext.getResources().getDrawable(session.getImageId()));
            sessionType.setText(session.getSringId());
            distance.setText(decimalFormat.format(session.getDistance()/1000f) + " km");
            time.setBase(SystemClock.elapsedRealtime() - session.getDuration());
            date.setText(session.getDate());
        }

        return rowView;
    }
}
