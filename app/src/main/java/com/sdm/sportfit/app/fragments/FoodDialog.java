package com.sdm.sportfit.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.sdm.sportfit.app.adapters.FoodAdapter;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

/**
 * Created by Jess on 19/04/2014.
 */
public class FoodDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private DatabaseHandler dh;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)	{

            AlertDialog.Builder	builder	=
                    new	AlertDialog.Builder(getActivity());

            Bundle extras = getArguments();
            dh = dh.getInstance(getActivity());
            Foods food = dh.getFood(extras.getInt("Food"));

            builder.setAdapter(new FoodAdapter(getActivity().getApplicationContext(), food), this);

            return	builder.create();
        }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
