package com.sdm.sportfit.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sdm.sportfit.app.logic.Diets;
import com.sdm.sportfit.app.logic.Foods;
import com.sdm.sportfit.app.persistence.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DietActivity extends ActionBarActivity {

    private DatabaseHandler dh;
    private TextView typeMeal, tnameFood, quantity, earnerdCalories;
    private SharedPreferences _prefs;
    private int idDiet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        dh = dh.getInstance(this);
        Intent intent = getIntent();
        idDiet = intent.getIntExtra("idDiet", 1);
        // Control TabHost
        TabHost host = (TabHost) findViewById(R.id.tabhost);
        host.setup();
        TabHost.TabSpec spec = null;
       // _prefs = getSharedPreferences("Mispreferencias", MODE_PRIVATE);
        //idDiet = _prefs.getInt("idDiet",0);


        //Day 1
        spec = host.newTabSpec("1");
        spec.setContent(R.id.dayone);
        spec.setIndicator(getResources().getString(R.string.day) + " 1");
        createRows(R.id.dayoneTable, 1);
        host.addTab(spec);
        //Day 2
        spec = host.newTabSpec("2");
        spec.setContent(R.id.daytwo);
        spec.setIndicator(getResources().getString(R.string.day) + " 2");
        createRows(R.id.daytwoTable, 2);
        host.addTab(spec);
        //Day 3
        spec = host.newTabSpec("3");
        spec.setContent(R.id.daythree);
        spec.setIndicator(getResources().getString(R.string.day) + " 3");
        createRows(R.id.daythreeTable, 3);
        host.addTab(spec);

        //Day4
        spec = host.newTabSpec("4");
        spec.setContent(R.id.dayfour);
        spec.setIndicator(getResources().getString(R.string.day) + " 4");
        createRows(R.id.dayfourTable, 4);
        host.addTab(spec);

        //Day5
        spec = host.newTabSpec("5");
        spec.setContent(R.id.dayfive);
        spec.setIndicator(getResources().getString(R.string.day) + " 5");
        createRows(R.id.dayfiveTable, 5);
        host.addTab(spec);

        //Day6
        spec = host.newTabSpec("6");
        spec.setContent(R.id.daysix);
        spec.setIndicator(getResources().getString(R.string.day) + " 6");
        createRows(R.id.daysixTable, 6);
        host.addTab(spec);

        //Day7
        spec = host.newTabSpec("7");
        spec.setContent(R.id.dayseven);
        spec.setIndicator(getResources().getString(R.string.day) + " 7");
        createRows(R.id.daysevenTable, 7);
        host.addTab(spec);

        //Day8
        spec = host.newTabSpec("8");
        spec.setContent(R.id.dayeight);
        spec.setIndicator(getResources().getString(R.string.day) + " 8");
        createRows(R.id.dayeightTable, 8);
        host.addTab(spec);

        //Day9
        spec = host.newTabSpec("9");
        spec.setContent(R.id.daynine);
        spec.setIndicator(getResources().getString(R.string.day) + " 9");
        createRows(R.id.daynineTable, 9);
        host.addTab(spec);

        //Day10
        spec = host.newTabSpec("10");
        spec.setContent(R.id.dayten);
        spec.setIndicator(getResources().getString(R.string.day) + " 10");
        createRows(R.id.daytenTable, 10);
        host.addTab(spec);

        //Day11
        spec = host.newTabSpec("11");
        spec.setContent(R.id.dayeleven);
        spec.setIndicator(getResources().getString(R.string.day) + " 11");
        createRows(R.id.dayelevenTable, 11);
        host.addTab(spec);

        //Day12
        spec = host.newTabSpec("12");
        spec.setContent(R.id.daytwelve);
        spec.setIndicator(getResources().getString(R.string.day) + " 12");
        createRows(R.id.daytwelveTable, 12);
        host.addTab(spec);

        //Day13
        spec = host.newTabSpec("13");
        spec.setContent(R.id.daythirteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 13");
        createRows(R.id.daythirteenTable, 13);
        host.addTab(spec);

        //Day14
        spec = host.newTabSpec("14");
        spec.setContent(R.id.dayfourteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 14");
        createRows(R.id.dayfourteenTable, 14);
        host.addTab(spec);

        //Day15
        spec = host.newTabSpec("15");
        spec.setContent(R.id.dayfifteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 15");
        createRows(R.id.dayfifteenTable, 15);
        host.addTab(spec);

        //Day16
        spec = host.newTabSpec("16");
        spec.setContent(R.id.daysixteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 16");
        createRows(R.id.daysixteenTable, 16);
        host.addTab(spec);

        //Day17
        spec = host.newTabSpec("17");
        spec.setContent(R.id.dayseventeen);
        spec.setIndicator(getResources().getString(R.string.day) + " 17");
        createRows(R.id.dayseventeenTable, 17);
        host.addTab(spec);

        //Day18
        spec = host.newTabSpec("18");
        spec.setContent(R.id.dayeighteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 18");
        createRows(R.id.dayeighteenTable, 18);
        host.addTab(spec);

        //Day19
        spec = host.newTabSpec("19");
        spec.setContent(R.id.daynineteen);
        spec.setIndicator(getResources().getString(R.string.day) + " 19");
        createRows(R.id.daynineTable, 19);
        host.addTab(spec);

        //Day20
        spec = host.newTabSpec(R.string.day+"20");
        spec.setContent(R.id.daytwenty);
        spec.setIndicator(getResources().getString(R.string.day) + " 20");
        createRows(R.id.daytwentyTable, 20);
        host.addTab(spec);

        //Day21
        spec = host.newTabSpec("21");
        spec.setContent(R.id.daytwentyone);
        spec.setIndicator(getResources().getString(R.string.day) + " 21");
        createRows(R.id.daytwentyoneTable, 21);
        host.addTab(spec);

        //Day22
        spec = host.newTabSpec("22");
        spec.setContent(R.id.daytwentytwo);
        spec.setIndicator(getResources().getString(R.string.day) + " 22");
        createRows(R.id.daytwentytwoTable, 22);
        host.addTab(spec);

        //Day23
        spec = host.newTabSpec("23");
        spec.setContent(R.id.daytwentythree);
        spec.setIndicator(getResources().getString(R.string.day) + " 23");
        createRows(R.id.daytwentythreeTable, 23);
        host.addTab(spec);

        //Day24
        spec = host.newTabSpec("24");
        spec.setContent(R.id.daytwentyfour);
        spec.setIndicator(getResources().getString(R.string.day) + " 24");
        createRows(R.id.daytwentyfourTable, 24);
        host.addTab(spec);

        //Day25
        spec = host.newTabSpec("25");
        spec.setContent(R.id.daytwentyfive);
        spec.setIndicator(getResources().getString(R.string.day) + " 25");
        createRows(R.id.daytwentyfiveTable, 25);
        host.addTab(spec);

        //Day26
        spec = host.newTabSpec("26");
        spec.setContent(R.id.daytwentysix);
        spec.setIndicator(getResources().getString(R.string.day) + " 26");
        createRows(R.id.daytwentysixTable, 26);
        host.addTab(spec);

        //Day27
        spec = host.newTabSpec("27");
        spec.setContent(R.id.daytwentyseven);
        spec.setIndicator(getResources().getString(R.string.day) + " 27");
        createRows(R.id.daytwentysevenTable, 27);
        host.addTab(spec);

        //Day28
        spec = host.newTabSpec("28");
        spec.setContent(R.id.daytwentyeight);
        spec.setIndicator(getResources().getString(R.string.day) + " 28");
        createRows(R.id.daytwentyeightTable, 28);
        host.addTab(spec);

        //Day29
        spec = host.newTabSpec("29");
        spec.setContent(R.id.daytwentynine);
        spec.setIndicator(getResources().getString(R.string.day) + " 29");
        createRows(R.id.daytwentynineTable, 29);
        host.addTab(spec);

        //Day30
        spec = host.newTabSpec("30");
        spec.setContent(R.id.daythirty);
        spec.setIndicator(getResources().getString(R.string.day) + " 30");
        createRows(R.id.daythirtyTable, 30);
        host.addTab(spec);

        //Day31
        spec = host.newTabSpec("31");
        spec.setContent(R.id.daythirtyone);
        spec.setIndicator(getResources().getString(R.string.day) + " 31");
        createRows(R.id.daythirtyoneTable, 31);
        host.addTab(spec);


        //Aqui ira el que este guardado en SharedPreferences
        host.setCurrentTabByTag(getResources().getString(R.string.day) + " 1");
    }

    public void createRows(int dayTable, int day) {
        List<Diets> dietList = new ArrayList<Diets>();
        dietList = dh.getDietByDateAndId(day, idDiet);
        TableLayout tableLayout = (TableLayout) findViewById(dayTable);
        boolean visibleBreak = false, visibleBrunch = false, visibleLunch = false, visibleAfter = false, visibleDinner = false;
        for (int i = 0; i < dietList.size(); i++) {
            Diets diet = dietList.get(i);
            Foods food = dh.getFood(diet.getIdFood());
            if ("Breakfast".equals(diet.getTypeMeal()) || "Desayuno".equals(diet.getTypeMeal())) {
                if (!visibleBreak){
                    TableRow row1 = new TableRow(this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this);
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this);
                    quantity = new TextView(this);
                    earnerdCalories = new TextView(this);
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleBreak = true;
                }
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this);
                quantity = new TextView(this);
                earnerdCalories = new TextView(this);
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                Log.v("VERBOSE", "Valor de cantidad " + diet.getQuantity() + "g");
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Brunch".equals(diet.getTypeMeal()) || "Almuerzo".equals(diet.getTypeMeal())) {
                if (!visibleBrunch){
                    TableRow row1 = new TableRow(this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this);
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this);
                    quantity = new TextView(this);
                    earnerdCalories = new TextView(this);
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleBrunch = true;
                }
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this);
                quantity = new TextView(this);
                earnerdCalories = new TextView(this);
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Lunch".equals(diet.getTypeMeal()) || "Comida".equals(diet.getTypeMeal())) {
                if (!visibleLunch){
                    TableRow row1 = new TableRow(this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this);
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this);
                    quantity = new TextView(this);
                    earnerdCalories = new TextView(this);
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleLunch = true;
                }
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this);
                quantity = new TextView(this);
                earnerdCalories = new TextView(this);
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }


            if ("Afternoon snack".equals(diet.getTypeMeal()) || "Merienda".equals(diet.getTypeMeal())) {
                if (!visibleAfter){
                    TableRow row1 = new TableRow(this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this);
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this);
                    quantity = new TextView(this);
                    earnerdCalories = new TextView(this);
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleAfter = true;
                }
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this);
                quantity = new TextView(this);
                earnerdCalories = new TextView(this);
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }

            if ("Dinner".equals(diet.getTypeMeal()) || "Cena".equals(diet.getTypeMeal())) {
                if (!visibleDinner){
                    TableRow row1 = new TableRow(this);
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row1.setLayoutParams(lp1);
                    TextView blank = new TextView(this);
                    blank.setText(" ");
                    row1.addView(blank);
                    tableLayout.addView(row1);
                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    typeMeal = new TextView(this);
                    quantity = new TextView(this);
                    earnerdCalories = new TextView(this);
                    typeMeal.setTextSize(30);
                    typeMeal.setText(diet.getTypeMeal());
                    quantity.setText(getResources().getString(R.string.quantity));
                    earnerdCalories.setText("kcal");
                    row.addView(typeMeal);
                    row.addView(quantity);
                    row.addView(earnerdCalories);
                    tableLayout.addView(row);
                    visibleDinner = true;
                }
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                tnameFood = new TextView(this);
                quantity = new TextView(this);
                earnerdCalories = new TextView(this);
                if ("es".equals(Locale.getDefault().getLanguage())) {
                    tnameFood.setText(food.getNameES());
                } else {
                    tnameFood.setText(food.getNameEN());
                }
                quantity.setText(String.valueOf(diet.getQuantity()) + " g");
                earnerdCalories.setText(String.valueOf(diet.getEarnedCalories()));
                row.addView(tnameFood);
                row.addView(quantity);
                row.addView(earnerdCalories);
                tableLayout.addView(row);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.diet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
