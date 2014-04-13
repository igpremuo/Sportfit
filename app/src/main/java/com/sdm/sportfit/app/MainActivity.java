package com.sdm.sportfit.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdm.sportfit.app.fragments.DietParentFragment;
import com.sdm.sportfit.app.fragments.HiitParentFragment;
import com.sdm.sportfit.app.fragments.MainParentFragment;
import com.sdm.sportfit.app.fragments.PreferencesActivity;
import com.sdm.sportfit.app.fragments.TrainParentFragment;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity

        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private DatabaseHandler dh;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private static final String FOODS_URL = "http://breakwebs.com/sportfit/restapi/index.php/foods";

    //JSON element ids from repsonse of php script:
    private static final String TAG_MESSAGE = "error";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private MainParentFragment mMapFragment = null;
    private DietParentFragment mDietFragment = null;
    private TrainParentFragment mTrainFragment = null;
    private HiitParentFragment mHiitFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setRetainInstance(true);

        //mTitle = getTitle();

        mTitle = getSectionTitle(mNavigationDrawerFragment.getCurrentSelectedPosition());

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        dh = dh.getInstance(this);
        if (!dh.existsDataInTable("Foods")) new AttemptFoods().execute();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                //if (mMapFragment == null) mMapFragment = new MainParentFragment();
                fragment = new MainParentFragment();
                break;
            case 1:
                //if (mDietFragment == null) mDietFragment = new DietParentFragment();
                fragment = new DietParentFragment();
                break;
            case 2:
                //if (mTrainFragment == null) mTrainFragment = new TrainParentFragment();
                fragment = new TrainParentFragment();
                break;
            case 3:
                if (mHiitFragment == null) mHiitFragment = new HiitParentFragment();
                fragment = mHiitFragment;
                break;
        }

        if (fragment != null) {
            replaceFragment(fragment, getSectionTitle(position));
        }
    }

    private void replaceFragment(Fragment newFragment, CharSequence title) {
        this.mTitle = title;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
    }

    public void openFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).addToBackStack(null).commit();
    }

    public void openFragmentAtPos(int position, int pagerItem) {
        Fragment fragment = new MainParentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MainParentFragment.CURRENT_POSITION, pagerItem);
        fragment.setArguments(bundle);

        replaceFragment(fragment, getSectionTitle(position));
    }

    private String getSectionTitle(int pos) {
        String[] sectionTitles = getResources().getStringArray(R.array.drawer_titles);
        if (pos < sectionTitles.length) return sectionTitles[pos];
        return "";
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            startService(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            /*((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));*/
        }
    }

    class AttemptFoods extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting Foods...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
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


}
