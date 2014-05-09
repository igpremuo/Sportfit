package com.sdm.sportfit.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sdm.sportfit.app.DietActivity;
import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.adapters.DietsListAdapter;
import com.sdm.sportfit.app.logic.Diet;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nacho on 1/04/14.
 */
public class DietReviewFragment extends Fragment   {
    private MainActivity mMainActivity;
    private ListView mDietsList;
    private DatabaseHandler dh;
    private List<Diet> dietList;
    private SharedPreferences _prefs;
    private SharedPreferences.Editor _prefsEditor;
    private Button createDiet;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diet_review, container, false);

        mDietsList = (ListView) rootView.findViewById(R.id.llistDiets);

        _prefs = this.getActivity().getSharedPreferences("MisPreferencias", Activity.MODE_PRIVATE);
        dh = dh.getInstance(getActivity());
        dietList = new ArrayList<Diet>();
        dietList = dh.getAllDiets();
        loadDietsList(dietList);

        mDietsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                Log.v("VERBOSE", "Entro por la dieta ");
                final Diet dietSelect = (Diet) mDietsList.getAdapter().getItem(position);
                //Bundle extras = new Bundle();
                //extras.putInt("Diet", dietSelect.getIdDiet());
               // DietDialog dialog = new DietDialog();
               // dialog.setArguments(extras);
              //  dialog.show(getFragmentManager(), "tagAlerta");
                saveStateDiet(dietSelect);
                launchDiet(dietSelect.getIdDiet());

             /**   AlertDialog.Builder	builder	=
                        new	AlertDialog.Builder(getActivity());

                builder.setMessage(dietSelect.getDescription())
                        .setTitle(dietSelect.getNameDiet())
                        .setPositiveButton(R.string.buttonSelectionDiet, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor _prefsEditor = _prefs.edit();
                                _prefsEditor.putInt("idDiet", dietSelect.getIdDiet());
                                _prefsEditor.putInt("dateDiet", 1);
                                _prefsEditor.commit();
                                dialog.cancel();
                            }
                        }).setNegativeButton(R.string.buttonSeeDiet,	new	DialogInterface.OnClickListener()	{
                    public	void	onClick(DialogInterface	dialog,	int	id)	{

                        dialog.cancel();
                    }
                });


                builder.show();*/
            }

        });
        return rootView;
    }


    private void saveStateDiet(Diet diet){
        _prefs = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        if(_prefs==null) return;
        _prefsEditor = _prefs.edit();
        if(_prefsEditor==null) return;

        _prefsEditor.putInt("idDiet",diet.getIdDiet());
        _prefsEditor.commit();
    }

    public void launchDiet(int idDiet){
        Intent intent = new Intent(this.getActivity(), DietActivity.class);
        intent.putExtra("idDiet", idDiet);

        startActivity(intent);
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    private void loadDietsList(List<Diet> dietList) {
        DietsListAdapter dietsListAdapter = new DietsListAdapter(getActivity().getApplicationContext(), dietList);
        this.mDietsList.setAdapter(dietsListAdapter);
        dietsListAdapter.notifyDataSetChanged();
    }


}
