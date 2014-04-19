package com.sdm.sportfit.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.persistence.JSONParser;
import com.sdm.sportfit.app.services.ConnectionDetector;

public class MainActivity extends ActionBarActivity

        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private DatabaseHandler dh;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();



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

    private SharedPreferences _prefs;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
        idUser = _prefs.getInt("idUser", 0);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setRetainInstance(true);

        //mTitle = getTitle();

        mTitle = getSectionTitle(mNavigationDrawerFragment.getCurrentSelectedPosition());

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();

        dh = dh.getInstance(this);
        Location location1 = new Location("Location1");
        location1.setLongitude(45.125225);
        location1.setLatitude(46.154578);
        Location location2 = new Location("Location2");
        location2.setLongitude(47.125225);
        location2.setLatitude(48.154578);

        //long idTraining1 = dh.addTraining(new Trainings(1, idUser, "Run", 152.25, 50000, 10.0, 5.5, 1000.0, "16/04/2014"));
        //long idTraining2 = dh.addTraining(new Trainings(2,idUser, "Run", 189.25, 30000, 12.0, 6.5, 2000.0, "16/04/2014"));
        //dh.addPoint(new Points(location1, 5.6, idTraining1),idTraining1 );
        //dh.addPoint(new Points(location2, 7.2, idTraining2), idTraining2);
        //List<Trainings> list = dh.getAllTrainings();

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        //if (!dh.existsDataInTable("Foods") && cd.isConnectingToInternet()){Log.v("VERBOSE", "ejecutando attempt foods"); new AttemptFoods().execute();}

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getSupportFragmentManager();
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
        }*/
        openFragmentAtPos(position, -1);
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
            if (pagerItem >= 0) {
                Bundle bundle = new Bundle();
                bundle.putInt(MainParentFragment.CURRENT_POSITION, pagerItem);
                fragment.setArguments(bundle);
            }
            replaceFragment(fragment, getSectionTitle(position));
        }
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

}
