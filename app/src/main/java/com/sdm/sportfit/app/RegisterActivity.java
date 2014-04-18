package com.sdm.sportfit.app;
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

import com.sdm.sportfit.app.logic.Users;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;
import com.sdm.sportfit.app.services.ConnectionDetector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 12/04/2014.
 */

public class RegisterActivity extends Activity implements OnClickListener{

    private EditText user, pass, mail;
    private Button  mRegister;
    private DatabaseHandler dh;
    // Progress Dialog
    private ProgressDialog pDialog;
    private SharedPreferences _prefs;
    private SharedPreferences.Editor _prefsEditor;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();


    private static final String LOGIN_URL = "http://breakwebs.com/sportfit/restapi/index.php/register";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        dh = dh.getInstance(this);

        user = (EditText)findViewById(R.id.username);
        mail = (EditText)findViewById(R.id.mail);
        pass = (EditText)findViewById(R.id.password);

        mRegister = (Button)findViewById(R.id.register);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        Boolean isInternetPresent = cd.isConnectingToInternet();
        if(isInternetPresent) new CreateUser().execute();
        else Toast.makeText(RegisterActivity.this, R.string.internetRequired, Toast.LENGTH_LONG).show();

    }

    class CreateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            String success;
            String username = user.getText().toString();
            String textMail = mail.getText().toString();
            String password = pass.getText().toString();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", username));
                params.add(new BasicNameValuePair("email", textMail));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getString("error");
                if (success == "false") {
                    Log.d("User Created!", json.toString());
                    int idUser = json.getInt("id");
                    _prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    _prefsEditor = _prefs.edit();
                    _prefsEditor.putInt("idUser", idUser);
                    _prefsEditor.commit();
                    Users user = new Users(idUser, username,textMail, password,"", String.valueOf(System.currentTimeMillis()),0.0, 0.0, "");
                     dh.addUser(user);
                    Intent i = new Intent(RegisterActivity.this, userStats.class);
                   // finish();
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
                Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}