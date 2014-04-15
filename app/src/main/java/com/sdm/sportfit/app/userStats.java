package com.sdm.sportfit.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;

/**
 * Created by max on 12/04/2014.
 */

public class userStats extends Activity implements View.OnClickListener {

        private EditText height,weight, imc, water, mg;
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_stats_layout);
            dh = dh.getInstance(this);

            spinnerAge = (Spinner)findViewById(R.id.spinnerAge);
            height = (EditText)findViewById(R.id.height);
            weight = (EditText)findViewById(R.id.weight);
            genreMan = (RadioButton)findViewById(R.id.radioButton);
            genreWoman = (RadioButton)findViewById(R.id.radioButton2);
            imc = (EditText)findViewById(R.id.imc);
            water = (EditText)findViewById(R.id.water);
            mg = (EditText)findViewById(R.id.mg);

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

           // Statistics statistic = new Statistics( 1, 1, String.valueOf(System.currentTimeMillis()), weight.getText(), age.getText(), genre.getText(), height.getText(), imc.getText(), water.getText(), mg.getText());
           // dh.addStatistics(statistic);
            Toast.makeText(userStats.this, R.string.internetRequired, Toast.LENGTH_LONG).show();

        }

        class CreateUser extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            boolean failure = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(userStats.this);
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
                    Toast.makeText(userStats.this, file_url, Toast.LENGTH_LONG).show();
                }

            }

        }

    }

