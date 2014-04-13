package com.sdm.sportfit.app.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jess on 8/04/14.
 */
public class Diets {

    private String nameDiet;
    private int idFood;
    private String typeMeal;
    private String timeMeal;
    private String dateMeal;
    private List<Foods> listFoods;



    public Diets() {
        this.nameDiet = "";
        this.idFood = 0;
        this.typeMeal = "";
        this.timeMeal = null;
        this.dateMeal = null;
        this.listFoods = new ArrayList<Foods>();
    }

    public Diets(String nameDiet, int idFood, String typeMeal, String timeMeal, String dateMeal, ArrayList<Foods> listFoods) {
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

    public String getTimeMeal() {
       return timeMeal;
    }

    public void setTimeMeal(String timeMeal) {
            this.timeMeal= timeMeal;
    }

    public String getDateMeal() {
        return dateMeal;
    }

    public void setDateMeal(String dateMeal)  {
           this.dateMeal =dateMeal;
    }


    public List<Foods> getListFoods() {
        return listFoods;
    }

    public void setListFoods(List<Foods> listFoods) {
        this.listFoods = listFoods;
    }
}
