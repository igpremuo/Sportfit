package com.sdm.sportfit.app.logic;

/**
 * Created by sdm on 9/04/14.
 */
public class Statistics {

    private int idStatistics;
    private int idUser;
    private String dateStatistics;
    private Double weight;
    private int age;
    private String gender;
    private Double height;
    private Double imc;
    private Double water;
    private Double mg;

    public Statistics() {
        this.idStatistics = 0;
        this.idUser = 0;
        this.dateStatistics = null;
        this.weight = 0.0;
        this.age = 0;
        this.gender = "";
        this.height = 0.0;
        this.imc = 0.0;
        this.water = 0.0;
        this.mg = 0.0;
    }


    public Statistics(int idStatistics, int idUser, String dateStatistics, Double weight, int age, String gender, Double height, Double imc, Double water, Double mg) {
        this.idStatistics = idStatistics;
        this.idUser = idUser;
        this.dateStatistics = dateStatistics;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.imc = imc;
        this.water = water;
        this.mg = mg;

    }

    public int getIdStatistics() {
        return idStatistics;
    }

    public void setIdStatistics(int idStatistics) {
        this.idStatistics = idStatistics;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDateStatistics() {
        return dateStatistics;
    }

    public void setDateStatistics(String dateStatistics) {
        this.dateStatistics = dateStatistics;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Double getWater() { return water;  }

    public void setWater(Double water) { this.water = water; }

    public Double getMg() { return mg;  }

    public void setMg(Double mg) { this.mg = mg; }

}
