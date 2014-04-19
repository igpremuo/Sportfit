package com.sdm.sportfit.app.logic;

import java.util.ArrayList;

/**
 * Created by Jess on 8/04/14.
 */
public class Diet {

    private int idDiet;
    private String nameDiet;
    private String description;
    private Double totalCalories;
    private ArrayList<Diets> listDiets;



    public Diet() {
        this.idDiet = 0;
        this.nameDiet = "";
        this.description = "";
        this.totalCalories = 0.0;
        this.listDiets = new ArrayList<Diets>();
    }

    public Diet(int idDiet, String nameDiet, String description, Double totalCalories, ArrayList<Diets> listDiets) {
        this.idDiet = idDiet;
        this.nameDiet = nameDiet;
        this.description = description;
        this.totalCalories = totalCalories;
        this.listDiets = listDiets;
    }

    public ArrayList<Diets> getListDiets() {
        return listDiets;
    }

    public void setListDiets(ArrayList<Diets> listDiets) {
        this.listDiets = listDiets;
    }

    public int getIdDiet() {
        return idDiet;
    }

    public void setIdDiet(int idDiet) {
        this.idDiet = idDiet;
    }

    public String getNameDiet() {
        return nameDiet;
    }

    public void setNameDiet(String nameDiet) {
        this.nameDiet = nameDiet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }
}
