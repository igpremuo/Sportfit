package com.sdm.sportfit.app.logic;

import java.util.Locale;

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
    private int height;
    private Double imc;
    private Double water;
    private Double pgc;
    private int sizeNeck;
    private int sizeWaist;
    private int sizeHip;
    private PhysicalType physicalType;

    public enum PhysicalType {
        SEDENTARY("Sedentary"), MODERATE("Moderate"), ACTIVE("Active"), SEDENTARIO("Sedentario"), MODERADO("Moderado"), ACTIVO("Activo");

        String mPhysicalType;

        PhysicalType(String physicalType) {
            mPhysicalType = physicalType;
        }

        @Override
        public String toString() {
            return String.valueOf(mPhysicalType);
        }
    };

    public Statistics() {
        this.idStatistics = 0;
        this.idUser = 0;
        this.dateStatistics = null;
        this.weight = 0.0;
        this.age = 0;
        this.gender = "";
        this.height = 0;
        this.imc = 0.0;
        this.water = 0.0;
        this.pgc = 0.0;
        this.sizeNeck = 0;
        this.sizeWaist = 0;
        this.physicalType = null;
        this.sizeHip = 0;
    }


    public Statistics(int idStatistics, int idUser, String dateStatistics, Double weight, int age, String gender, int height, Double imc, Double water, Double pgc, int sizeNeck, int sizeWaist, int sizeHip, String physicalType) {
        this.idStatistics = idStatistics;
        this.idUser = idUser;
        this.dateStatistics = dateStatistics;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.imc = imc;
        this.water = water;
        this.pgc = pgc;
        this.sizeNeck = sizeNeck;
        this.sizeWaist = sizeWaist;
        this.sizeHip = sizeHip;
        this.physicalType = getPhysicalType(physicalType);

    }

    public int getSizeHip() {
        return sizeHip;
    }

    public void setSizeHip(int sizeHip) {
        this.sizeHip = sizeHip;
    }

    public int getSizeNeck() {
        return sizeNeck;
    }

    public void setSizeNeck(int sizeNeck) {
        this.sizeNeck = sizeNeck;
    }

    public int getSizeWaist() {
        return sizeWaist;
    }

    public void setSizeWaist(int sizeWaist) {
        this.sizeWaist = sizeWaist;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
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

    public Double getPgc() { return pgc;  }

    public void setPgc(Double pgc) { this.pgc = pgc; }

    public String getPhysicalType() {
        return physicalType.toString();
    }

    public void setPhysicalType(String physicalType) {
        this.physicalType = getPhysicalType(physicalType);
    }


    public PhysicalType getPhysicalType(String physicalType) {
        if (physicalType.equals(PhysicalType.SEDENTARY.toString()))
            return PhysicalType.SEDENTARY;
        if (physicalType.equals(PhysicalType.MODERATE.toString()))
            return PhysicalType.MODERATE;
        if (physicalType.equals(PhysicalType.ACTIVE.toString()))
            return PhysicalType.ACTIVE;
        if (physicalType.equals(PhysicalType.SEDENTARIO.toString()))
            return PhysicalType.SEDENTARIO;
        if (physicalType.equals(PhysicalType.MODERADO.toString()))
            return PhysicalType.MODERADO;
        if (physicalType.equals(PhysicalType.ACTIVO.toString()))
            return PhysicalType.ACTIVO;

        if ("es".equals(Locale.getDefault().getLanguage())){
            return PhysicalType.SEDENTARIO;
        } else {
            return PhysicalType.SEDENTARY;
        }
    }

}