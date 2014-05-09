package com.sdm.sportfit.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sdm.sportfit.app.fragments.DietParentFragment;
import com.sdm.sportfit.app.fragments.HiitParentFragment;
import com.sdm.sportfit.app.fragments.MainParentFragment;
import com.sdm.sportfit.app.fragments.NavigationDrawerFragment;
import com.sdm.sportfit.app.fragments.StaticsParentFragment;
import com.sdm.sportfit.app.fragments.TrainParentFragment;
import com.sdm.sportfit.app.interfaces.CallBacks;
import com.sdm.sportfit.app.persistence.DatabaseHandler;
import com.sdm.sportfit.app.services.GpsIntentService;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, CallBacks {

    private final static String KEY_CURRENT_FRAGMENT = "current_fragment";
    private final static String KEY_CHILD_FRAGMENT = "child_fragment";

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
    private StaticsParentFragment mStaticsFragment = null;

    private SharedPreferences _prefs;
    private int idUser;

    private Menu mMenu;

    private int mCurrentFragment;
    private int mCurrentChild;

    private DatabaseHandler dh;

    private int hoy;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("VERBOSE", "Entro por la main Activity");
        calendar = Calendar.getInstance();
        hoy = calendar.getTime().getDate();
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

        if (savedInstanceState != null) {
            mCurrentFragment = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT);
            mCurrentChild = savedInstanceState.getInt(KEY_CHILD_FRAGMENT);
        }

        /*
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
        */


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mCurrentFragment = position;
        // update the main content by replacing fragments
        openFragmentAtPos(position, -1);
    }

    @Override
    public void onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_listen_train:
                Intent bcIntent = new Intent();
                bcIntent.setAction(GpsIntentService.REPRODUCE_INFO);
                sendBroadcast(bcIntent);
                break;
            case R.id.action_example:
                Toast.makeText(getApplicationContext(), "Example action", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        updateMenu();
        return true;
    }

    @Override
    public void setFragmentChild(int position) {
        mCurrentChild = position;
        updateMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            //startService(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_FRAGMENT, mCurrentFragment);
        outState.putInt(KEY_CHILD_FRAGMENT, mCurrentChild);
    }

    private void replaceFragment(Fragment newFragment, CharSequence title) {
        this.mTitle = title;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Clear fragment backstack
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
        }

        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
    }

    public void openFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
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
            case 5:
                if (mHiitFragment == null) mHiitFragment = new HiitParentFragment();
                fragment = mHiitFragment;
                break;
            case 3:
                if (mStaticsFragment == null) mStaticsFragment = new StaticsParentFragment();
                fragment = mStaticsFragment;
                break;
            case 4:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
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

    public void updateMenu() {
        //getMenuInflater().inflate(R.menu.main, mMenu);

        if (mMenu != null) {

            MenuItem listenTrain = mMenu.findItem(R.id.action_listen_train);
            MenuItem example = mMenu.findItem(R.id.action_example);

            listenTrain.setVisible(false);
            example.setVisible(false);

            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if (!mNavigationDrawerFragment.isDrawerOpen()) {

                switch (mCurrentFragment) {
                    case 0:
                        switch (mCurrentChild) {
                            case 0:
                                listenTrain.setVisible(true);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                        break;
                    case 1:
                        switch (mCurrentChild) {
                            case 0:
                                example.setVisible(true);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        }
        restoreActionBar();
    }

}
