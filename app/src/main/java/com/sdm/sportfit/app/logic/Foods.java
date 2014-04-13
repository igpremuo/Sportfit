package com.sdm.sportfit.app.logic;

/**
 * Created by Jess on 8/04/14.
 */
public class Foods {
    private int id;
    private String nameES;
    private String nameEN;
    private Double calories;
    private Double proteins;
    private Double carbohydrates;
    private Double fats;
    private Double water;
    private String categoryES;
    private String categoryEN;
    private Double earnedCalories;
    private Double quantity;

    public Foods() {
        this.id = 0;
        this.nameES = "";
        this.nameEN = "";
        this.calories = 0.0;
        this.proteins = 0.0;
        this.carbohydrates = 0.0;
        this.fats = 0.0;
        this.water = 0.0;
        this.categoryES = "";
        this.categoryEN = "";
        this.earnedCalories = 0.0;
        this.quantity = 0.0;
    }


    public Foods(int id, String nameES, String nameEN, Double calories, Double proteins, Double carbohydrates, Double fats, Double water, String categoryES, String categoryEN, Double earnedCalories, Double quantity) {
        this.id = id;
        this.nameES = nameES;
        this.nameEN = nameEN;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.water = water;
        this.categoryES = categoryES;
        this.categoryEN = categoryEN;
        this.earnedCalories = earnedCalories;
        this.quantity = quantity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getEarnedCalories() {
        return earnedCalories;
    }

    public void setEarnedCalories(Double earnedCalories) {
        this.earnedCalories = earnedCalories;
    }


    public String getCategoryES() {
        return categoryES;
    }

    public void setCategoryES(String categoryES) {
        this.categoryES = categoryES;
    }

    public String getCategoryEN() {
        return categoryEN;
    }

    public void setCategoryEN(String categoryEN) {
        this.categoryEN = categoryEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameES() {
        return nameES;
    }

    public void setNameES(String nameES) {
        this.nameES = nameES;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }


}
