package com.sdm.sportfit.app;

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
    private String category;

    public Foods() {
        this.id = 0;
        this.nameES = "";
        this.nameEN = "";
        this.calories = 0.0;
        this.proteins = 0.0;
        this.carbohydrates = 0.0;
        this.fats = 0.0;
        this.water = 0.0;
        this.category = "";
    }

    public Foods(int id, String nameES, String nameEN, Double calories, Double proteins, Double carbohydrates, Double fats, Double water, String category) {
        this.id = id;
        this.nameES = nameES;
        this.nameEN = nameEN;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.water = water;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
