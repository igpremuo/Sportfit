package com.sdm.sportfit.app;

/**
 * Created by max on 12/04/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdm.sportfit.app.logic.Diet;
import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;
import com.sdm.sportfit.app.services.ConnectionDetector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity implements OnClickListener{

    private EditText mail, pass;
    private Button mSubmit, mRegister;
    private DatabaseHandler dh;
    private SharedPreferences _prefs;
    private SharedPreferences.Editor _prefsEditor;
    private boolean foodChecked;
    private ConnectionDetector cd;
    // Progress Dialog
   // private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://breakwebs.com/sportfit/restapi/index.php/login";
    private static final String FOODS_URL = "http://breakwebs.com/sportfit/restapi/index.php/foods";
    private static final String DIET_URL = "http://breakwebs.com/sportfit/restapi/index.php/diet";
    private static final String DIETS_URL = "http://breakwebs.com/sportfit/restapi/index.php/diets";



    //JSON element ids from repsonse of php script:
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        dh = dh.getInstance(this);
        foodChecked = false;

        mail = (EditText)findViewById(R.id.mail);
        pass = (EditText)findViewById(R.id.password);

        mSubmit = (Button)findViewById(R.id.login);
        mRegister = (Button)findViewById(R.id.register);

        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        Log.v("VERBOSE", "Creando los contenidos de la db");
        cd = new ConnectionDetector(getApplicationContext());
        if (!dh.existsDataInTable("Foods") || !dh.existsDataInTable("Diet") || !dh.existsDataInTable("Diets")){
            if (cd.isConnectingToInternet()){
                Log.v("VERBOSE", "ejecutando attempts");
                if(!dh.existsDataInTable("Foods")) new AttemptFoods().execute();
                if(!dh.existsDataInTable("Diet")) new AttemptDiet().execute();
                if(!dh.existsDataInTable("Diets")) new AttemptDiets().execute();
                foodChecked = true;
            } else {
                Toast.makeText(this, "You need internet connection", Toast.LENGTH_LONG).show();
            }
        } else {
            foodChecked = true;
        }

         /**** Quitar esto para realizar el login ****/
      //  Intent i = new Intent(LoginActivity.this, MainActivity.class);
        //finish();
        //startActivity(i);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (foodChecked) {
            switch (v.getId()) {
                case R.id.login:
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                    Boolean isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) new AttemptLogin().execute();
                    else {
                        try {
                            int logued = dh.checkLogin(mail.getText().toString().trim(), pass.getText().toString().trim());
                            Log.d("User logeado!", "" + logued);
                            _prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
                            _prefsEditor = _prefs.edit();
                            _prefsEditor.putInt("idUser", logued);
                            _prefsEditor.commit();
                            if (logued > 0) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                startActivity(i);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login incorrect. Are you already registered?", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Login incorrect. Are you already registered?", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case R.id.register:
                    Intent i = new Intent(this, RegisterActivity.class);
                    startActivity(i);
                    break;

                default:
                    break;
            }
        } else {
            if (cd.isConnectingToInternet()){
                Log.v("VERBOSE", "ejecutando cuando han conectado internet attempts");
               if(!dh.existsDataInTable("Foods")) new AttemptFoods().execute();
               if(!dh.existsDataInTable("Diet")) new AttemptDiet().execute();
               if(!dh.existsDataInTable("Diets")) new AttemptDiets().execute();
                foodChecked = true;
            } else {
                Toast.makeText(this, "Failed to connect server. Please try again", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this, "You need internet connection", Toast.LENGTH_LONG).show();
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {
        List<NameValuePair> params =  new ArrayList<NameValuePair>();
        ProgressDialog pDialog;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            String success;
            String username = mail.getText().toString();
            String password = pass.getText().toString();


           try {
                // Building Parameters

                params.add(new BasicNameValuePair("email", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getString("error");
                if (success == "false") {
                     Log.d("User logeado servidor!", json.toString());
                    Log.d("id User", json.getInt("id") + "");
                    int idUser = json.getInt("id");
                    _prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    _prefsEditor = _prefs.edit();
                    _prefsEditor.putInt("idUser", idUser);
                    _prefsEditor.commit();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

    class AttemptFoods extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Downloading data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            String success;
            JSONObject jsonFood = null;
            //Foods food = null;
            try {
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        FOODS_URL, "GET", null);

                // check your log for json response
                Log.d("Foods attempt", json.toString());

                // json success tag
                success = json.getString("error");
                if (success == "false") {
                    Log.v("VERBOSE", "Numero de alimentos: " + json.getJSONArray("foods").length());
                    for (int i = 0; i < json.getJSONArray("foods").length(); i++){
                        jsonFood = (JSONObject) json.getJSONArray("foods").get(i);
                        Foods food = new Foods();
                        Log.v("VERBOSE", "Numero de alimentos: " + jsonFood.getInt("ID"));
                        food.setId(jsonFood.getInt("ID"));
                        food.setNameES(jsonFood.getString("nameES"));
                        food.setNameEN(jsonFood.getString("nameEN"));
                        food.setCalories(jsonFood.getDouble("calories"));
                        food.setProteins(jsonFood.getDouble("proteins"));
                        food.setCarbohydrates(jsonFood.getDouble("carbohydrates"));
                        food.setFats(jsonFood.getDouble("fats"));
                        food.setWater(jsonFood.getDouble("water"));
                        food.setCategoryES(jsonFood.getString("categoryES"));
                        food.setCategoryEN(jsonFood.getString("categoryEN"));
                        dh.addFood(food);
                    }
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            // if (file_url != null){
            // Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            //}

        }

    }

    class AttemptDiet extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Downloading data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            String success;
            JSONObject jsonDiet = null;
            //Foods food = null;
            try {
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        DIET_URL, "GET", null);

                // check your log for json response
                Log.d("Foods attempt", json.toString());

                // json success tag
                success = json.getString("error");
                if (success == "false") {
                    for (int i = 0; i < json.getJSONArray("message").length(); i++) {
                        jsonDiet = (JSONObject) json.getJSONArray("message").get(i);
                        Diet diet = new Diet();
                        diet.setIdDiet(jsonDiet.getInt("idDiet"));
                        diet.setNameDiet(jsonDiet.getString("nameDiet"));
                        diet.setDescription(jsonDiet.getString("description"));
                        diet.setTotalCalories(jsonDiet.getDouble("totalCalories"));

                        dh.addDiet(diet);
                    }
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            // if (file_url != null){
            // Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            //}

        }
    }

    class AttemptDiets extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Downloading data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            String success;
            JSONObject jsonDiets = null;
            //Foods food = null;
            try {
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        DIETS_URL, "GET", null);

                // check your log for json response
                Log.d("Foods attempt", json.toString());

                // json success tag
                success = json.getString("error");
                if (success == "false") {
                    for (int i = 0; i < json.getJSONArray("message").length(); i++) {
                        jsonDiets = (JSONObject) json.getJSONArray("message").get(i);
                        Diets diets = new Diets();
                        diets.setIdDiet(jsonDiets.getInt("idDiet"));
                        diets.setIdFood(jsonDiets.getInt("idFood"));
                        diets.setTypeMeal(jsonDiets.getString("typeMeal"));
                        diets.setTimeMeal(jsonDiets.getString("timeMeal"));
                        diets.setDateMeal(jsonDiets.getInt("dateMeal"));
                        diets.setEarnedCalories(jsonDiets.getDouble("earnedCalories"));
                        diets.setQuantity(jsonDiets.getInt("quantity"));
                        dh.addDiets(diets);
                    }
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            // if (file_url != null){
            // Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            //}

        }
    }
}