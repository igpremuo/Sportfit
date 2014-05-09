package com.sdm.sportfit.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.sportfit.app.logic.Statistics;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;

import java.util.StringTokenizer;

/**
 * Created by max on 12/04/2014.
 */

public class UserStats extends Activity implements View.OnClickListener {

    private EditText height,weight, imc, water, pgc, sizeNeck, sizeWaist,sizeHip;
    private RadioGroup radioGroup;
    private RadioButton genreMan, genreWoman;
    private Spinner spinnerAge, spinnerPhysicalType;
    private Button save;
    private DatabaseHandler dh;
    private int checkHeight;
    private String checkWeight;
    // Progress Dialog
    private ProgressDialog pDialog;
    private TextView textViewHip;
    private SharedPreferences _prefs;
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
        spinnerPhysicalType = (Spinner)findViewById(R.id.spinnerPhysicalType);
        height = (EditText)findViewById(R.id.height);
        weight = (EditText)findViewById(R.id.weight);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        genreMan = (RadioButton)findViewById(R.id.radioButton);
        genreWoman = (RadioButton)findViewById(R.id.radioButton2);
        imc = (EditText)findViewById(R.id.imc);
        water = (EditText)findViewById(R.id.water);
        pgc = (EditText)findViewById(R.id.pgc);
        sizeNeck = (EditText)findViewById(R.id.sizeNeck);
        sizeWaist = (EditText)findViewById(R.id.sizeWaist);
        sizeHip = (EditText)findViewById(R.id.sizeHip);
        statistic = new Statistics();
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        textViewHip = (TextView) findViewById(R.id.TextViewHip);
        statistic = new Statistics();
        _prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
        statistic.setIdUser(_prefs.getInt("idUser",1));
        Log.v("VERBOSE", "id de usuario a meter en stats "+ _prefs.getInt("idUser", -1));
        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this, R.array.ageArray,
                android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);


        ArrayAdapter<CharSequence> adapterPhysicalType = ArrayAdapter.createFromResource(this, R.array.physicalType,
                android.R.layout.simple_spinner_item);
        adapterPhysicalType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhysicalType.setAdapter(adapterPhysicalType);

        genreMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHip.setVisibility(View.INVISIBLE);
                sizeHip.setVisibility(View.INVISIBLE);
            }
        });

        genreWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHip.setVisibility(View.VISIBLE);
                sizeHip.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        try{
            boolean requiredHeight, requiredWeight;
            boolean selectedMan = false;
            boolean selectedWoman = false;

            int selectedId = radioGroup.getCheckedRadioButtonId();
            String genre = "";
            if (selectedId == 1) {
                genre = "Man";
                selectedMan = true;
            } else {
                genre = "Woman";
                selectedWoman = true;
            }

            statistic.setGender(genre);
            //Select age
            statistic.setAge(Integer.parseInt((String) spinnerAge.getSelectedItem()));
            //Select height obligatorio
            Log.v("VERBOSE", "height " + height.getText().toString());
            if (!"".equals(height.getText().toString())) {
                checkHeight = Integer.parseInt(height.getText().toString());
                if (checkHeight <= 250 && checkHeight >= 50)
                    statistic.setHeight(checkHeight);
                else {
                    Toast.makeText(UserStats.this, R.string.chechkedHeight, Toast.LENGTH_LONG).show();
                    requiredHeight = false;
                }
                requiredHeight = true;
            } else {
                Toast.makeText(UserStats.this, R.string.fieldRequired, Toast.LENGTH_LONG).show();
                requiredHeight = false;
            }
            Log.v("VERBOSE", "height " + statistic.getHeight());
            //obligatorio
            Log.v("VERBOSE", "weight " + weight.getText().toString());
            if (!"".equals(weight.getText().toString())) {
                checkWeight =  weight.getText().toString();
                StringTokenizer token = new StringTokenizer(checkWeight, ".");
                int elmentsCheckWeight = Integer.parseInt(token.nextToken());
                if (elmentsCheckWeight <= 300 && elmentsCheckWeight >= 30){
                    statistic.setWeight(Double.parseDouble(checkWeight));
                } else {
                    Toast.makeText(UserStats.this, R.string.chechkedWeight, Toast.LENGTH_LONG).show();
                    requiredWeight = false;
                }
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
            else
                statistic.setImc(calImc(statistic.getWeight(), statistic.getHeight()));
            Log.v("VERBOSE", "imc " + statistic.getImc());
            Log.v("VERBOSE", "sizeNeck " + sizeNeck.getText().toString());
            if (!"".equals(sizeNeck.getText().toString()))
                statistic.setSizeNeck(Integer.parseInt(sizeNeck.getText().toString()));
            Log.v("VERBOSE", "sizeNeck " + statistic.getSizeNeck());
            Log.v("VERBOSE", "sizeWaist " + sizeWaist.getText().toString());
            if (!"".equals(sizeWaist.getText().toString()))
                statistic.setSizeWaist(Integer.parseInt(sizeWaist.getText().toString()));
            Log.v("VERBOSE", "sizeWaist " + statistic.getSizeWaist());

            if(selectedWoman) {
                Log.v("VERBOSE", "sizeHip " + sizeHip.getText().toString());
                if (!"".equals(sizeHip.getText().toString()))
                    statistic.setSizeHip(Integer.parseInt(sizeHip.getText().toString()));
                Log.v("VERBOSE", "sizeHip " + statistic.getSizeHip());
            }

            Log.v("VERBOSE", "water " + water.getText().toString());
            if (!"".equals(water.getText().toString()))
                statistic.setWater(Double.parseDouble(water.getText().toString()));
            else if (selectedWoman)
                statistic.setWater(calcWaterWoman(statistic.getHeight(), statistic.getWeight()));
            else
                statistic.setWater(calcWaterMan(statistic.getHeight(), statistic.getWeight()));
            Log.v("VERBOSE", "water " + statistic.getWater());

            Log.v("VERBOSE", "pgc valor del texto visual " + pgc.getText().toString());
            if (!"".equals(pgc.getText().toString())){
                statistic.setPgc(Double.parseDouble(pgc.getText().toString()));
                Log.v("VERBOSE", "pgc valor insertado por el usuario " +pgc.getText());
            } else if (selectedWoman && !"".equals(sizeHip.getText().toString()) && !"".equals(sizeWaist.getText().toString())) {
                statistic.setPgc(calcPgcWoman(statistic.getSizeHip(), statistic.getSizeWaist(), statistic.getHeight(), statistic.getSizeNeck()));
                Log.v("VERBOSE", "pgc valor  ");
            } else if (selectedMan && !"".equals(sizeWaist.getText().toString())){
                statistic.setPgc(calcPgcMan(statistic.getSizeWaist(), statistic.getHeight(), statistic.getSizeNeck()));
            Log.v("VERBOSE", "valor pgc hombre   " + statistic.getPgc());
            } else {
                int gender = 0;
                if (selectedMan)
                    gender = 1;
                statistic.setPgc(calcPgc(statistic.getImc(), statistic.getAge(), gender));
                Log.v("VERBOSE", "valor pgc generico   " + statistic.getPgc());

            }
            Log.v("VERBOSE", "pgc " + statistic.getPgc());
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

    private double calcPgc(Double imc, int age, int gender){
        return 1.2 * imc + 0.23 * age - 10.8 * gender - 5.4;
    }

    private double calcWaterWoman(int height, Double weight) {
        return (((0.34454 * height) + (0.183809 * weight) - 35.270121)/weight)*100;
    }

    private double calcWaterMan(int height, Double weight) {
        return (((0.197486 * height) + (0.296785 * weight) - 14.012934)/weight)*100;
    }
    //%Grasa=495/(1.0324-0.19077(log(cintura-cuello))+0.15456(log(altura)))-450
    private Double calcPgcMan(int sizeWaist, int height, int sizeNeck) {
        return 495/((1.0324 - (0.19077 * (Math.log((sizeWaist-sizeNeck)))) + (0.15456 * (Math.log(height)))) - 450);
    }

    //%Grasa=495/(1.29579-0.35004(log(cintura+cadera-cuello))+0.22100(log(altura)))-450
    private double calcPgcWoman(int sizeHip, int sizeWaist, int height, int sizeNeck) {
            /**double r =  (weight*0.86)+24;
            double r1 =  sizeWaist * 0.14;
            double r2 = sizeHip * 0.2;
            double leanWeight = r - r1 - r2;
            double pcgWoman = ((weight - leanWeight)*100)/weight;
            return pcgWoman;*/
        return 495/((1.29579 - (0.35004 * (Math.log((sizeWaist+sizeHip-sizeNeck)))) + (0.22100 * (Math.log(height)))) - 450);
     }

    private Double calImc(Double weight, int height) {
        Double imc = 0.0;
        if (height != 0)
            imc = weight/Math.pow(height, 2);
        return imc * 10000;
    }


   /* class CreateStatsUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
    /*    boolean failure = false;

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
   /*     protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(UserStats.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    } */

}

