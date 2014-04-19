package com.sdm.sportfit.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;

/**
 * Created by max on 12/04/2014.
 */

public class UserStats extends Activity implements View.OnClickListener {

    private EditText height,weight, imc, water, mg;
    private RadioGroup radioGroup;
    private RadioButton genreMan, genreWoman;
    private Spinner spinnerAge;
    private Button save;
    private DatabaseHandler dh;
    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();


    private static final String LOGIN_URL = "http://breakwebs.com/sportfit/restapi/index.php/register";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    protected Statistics statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_stats_layout);
        dh = dh.getInstance(this);

        spinnerAge = (Spinner)findViewById(R.id.spinnerAge);
        height = (EditText)findViewById(R.id.height);
        weight = (EditText)findViewById(R.id.weight);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        genreMan = (RadioButton)findViewById(R.id.radioButton);
        genreWoman = (RadioButton)findViewById(R.id.radioButton2);
        imc = (EditText)findViewById(R.id.imc);
        water = (EditText)findViewById(R.id.water);
        mg = (EditText)findViewById(R.id.mg);
        statistic = new Statistics();
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ageArray,
                R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAge.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        boolean requiredHeight = false, requiredWeight = false;
        try{
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String genre = "";
            if (selectedId == 1) genre = "Man";
            else genre = "Woman";
            statistic.setGender(genre);
            //Select age
            statistic.setAge(Integer.parseInt((String) spinnerAge.getSelectedItem()));
            //Select height obligatorio
            Log.v("VERBOSE", "height " + height.getText().toString());
            if (!"".equals(height.getText().toString())) {
                statistic.setHeight(Double.parseDouble(height.getText().toString()));
                requiredHeight = true;
            } else {
                Toast.makeText(UserStats.this, R.string.fieldRequired, Toast.LENGTH_LONG).show();
                requiredHeight = false;
            }
            Log.v("VERBOSE", "height " + statistic.getHeight());
            //obligatorio
            Log.v("VERBOSE", "weight " + weight.getText().toString());
            if (!"".equals(weight.getText().toString())) {
                statistic.setWeight(Double.parseDouble(weight.getText().toString()));
                requiredWeight = true;
            } else {
                Toast.makeText(UserStats.this, R.string.fieldRequired, Toast.LENGTH_LONG).show();
                requiredWeight = false;
            }
            Log.v("VERBOSE", "weight " + statistic.getWeight());
            //Si no estan aplicar la formula y rellenar el cuadro visual
            Log.v("VERBOSE", "imc " + imc.getText().toString());
            if (!"".equals(imc.getText().toString()))
                statistic.setImc(Double.parseDouble(imc.getText().toString()));
            Log.v("VERBOSE", "imc " + statistic.getImc());
            Log.v("VERBOSE", "water " + water.getText().toString());
            if (!"".equals(water.getText().toString()))
                statistic.setWater(Double.parseDouble(water.getText().toString()));
            Log.v("VERBOSE", "water " + statistic.getWater());
            Log.v("VERBOSE", "mg " + mg.getText().toString());
            if (!"".equals(mg.getText().toString()))
                statistic.setMg(Double.parseDouble(mg.getText().toString()));
            Log.v("VERBOSE", "mg " + statistic.getMg());
            dh.addStatistics(statistic);
            if (requiredWeight && requiredHeight) {
                Intent i = new Intent(UserStats.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        }catch (Exception e){
            Toast.makeText(UserStats.this, R.string.notSaved, Toast.LENGTH_LONG).show();
        }
    }

    class CreateStatsUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserStats.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(UserStats.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}

