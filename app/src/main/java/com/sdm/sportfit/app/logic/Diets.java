package com.sdm.sportfit.app.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jess on 8/04/14.
 */
public class Diets {

    private String nameDiet;
    private int idFood;
    private String typeMeal;
    private Date timeMeal;
    private Date dateMeal;
    private List<Foods> listFoods;



    public Diets() {
        this.nameDiet = "";
        this.idFood = 0;
        this.typeMeal = "";
        this.timeMeal = null;
        this.dateMeal = null;
        this.listFoods = new ArrayList<Foods>();
    }

    public Diets(String nameDiet, int idFood, String typeMeal, Date timeMeal, Date dateMeal, ArrayList<Foods> listFoods) {
        this.nameDiet = nameDiet;
        this.idFood = idFood;
        this.typeMeal = typeMeal;
        this.timeMeal = timeMeal;
        this.dateMeal = dateMeal;
        this.listFoods = listFoods;
    }


    public String getNameDiet() {
        return nameDiet;
    }

    public void setNameDiet(String nameDiet) {
        this.nameDiet = nameDiet;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getTypeMeal() {
        return typeMeal;
    }

    public void setTypeMeal(String typeMeal) {
        this.typeMeal = typeMeal;
    }

    public Date getTimeMeal() {
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            timeMeal = sdf.parse(timeMeal.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeMeal;
    }

    public void setTimeMeal(String timeMeal) {
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            this.timeMeal = sdf.parse(timeMeal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDateMeal() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateMeal = sdf.parse(dateMeal.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateMeal;
    }

    public void setDateMeal(String dateMeal)  {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dateMeal = sdf.parse(dateMeal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public List<Foods> getListFoods() {
        return listFoods;
    }

    public void setListFoods(List<Foods> listFoods) {
        this.listFoods = listFoods;
    }
}
