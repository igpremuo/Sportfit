package com.sdm.sportfit.app.logic;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Jess on 8/04/14.
 */
public class Diets {

    private String nameDiet;
    private int idFood;
    private String typeMeal;
    private Time timeMeal;
    private Date dateMeal;
    private Double earnedCalories;

    public Diets() {
        this.nameDiet = "";
        this.idFood = 0;
        this.typeMeal = "";
        this.timeMeal = null;
        this.dateMeal = null;
        this.earnedCalories = 0.0;
    }

    public Diets(String nameDiet, int idFood, String typeMeal, Time timeMeal, Date dateMeal, Double earnedCalories) {
        this.nameDiet = nameDiet;
        this.idFood = idFood;
        this.typeMeal = typeMeal;
        this.timeMeal = timeMeal;
        this.dateMeal = dateMeal;
        this.earnedCalories = earnedCalories;
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

    public Time getTimeMeal() {
        return timeMeal;
    }

    public void setTimeMeal(Time timeMeal) {
        this.timeMeal = timeMeal;
    }

    public Date getDateMeal() {
        return dateMeal;
    }

    public void setDateMeal(Date dateMeal) {
        this.dateMeal = dateMeal;
    }

    public Double getEarnedCalories() {
        return earnedCalories;
    }

    public void setEarnedCalories(Double earnedCalories) {
        this.earnedCalories = earnedCalories;
    }
}
