package com.sdm.sportfit.app.logic;

/**
 * Created by Jess on 8/04/14.
 */
public class Trainings {
    private int idTraining;
    private String typeTraining;
    private Double caloriesBurned;
    private Double duration;
    private Double averageSpeed;
    private Double averageRate;
    private Double distance;

    public Trainings() {
        this.idTraining = 0;
        this.typeTraining = "";
        this.caloriesBurned = 0.0;
        this.duration = 0.0;
        this.averageSpeed = 0.0;
        this.averageRate = 0.0;
        this.distance = 0.0;
    }

    public Trainings(int idTraining, String typeTraining, Double caloriesBurned, Double duration, Double averageSpeed, Double averageRate, Double distance) {
        this.idTraining = idTraining;
        this.typeTraining = typeTraining;
        this.caloriesBurned = caloriesBurned;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.averageRate = averageRate;
        this.distance = distance;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public String getTypeTraining() {
        return typeTraining;
    }

    public void setTypeTraining(String typeTraining) {
        this.typeTraining = typeTraining;
    }

    public Double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
