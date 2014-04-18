package com.sdm.sportfit.app.logic;

import com.sdm.sportfit.app.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jess on 8/04/14.
 */
public class Trainings extends ArrayList<Points> implements Serializable {

    public enum Type {
        RUNNING("Run"), CYCLING("Cycling"), WALKING("Walk");
        String mType;

        Type(String type) {
            mType = type;
        }

        @Override
        public String toString() {
            return mType;
        }
    };

    private int idTraining;
    private Type typeTraining;
    private Double caloriesBurned;
    private long duration;
    private Double averageSpeed;
    private Double averageRate;
    private Double distance;
    private String date;
    private int idUser;

    public Trainings() {
        this.idTraining = 0;
        this.idUser = 0;
        this.typeTraining = Type.RUNNING;
        this.caloriesBurned = 0.0;
        this.duration = 0;
        this.averageSpeed = 0.0;
        this.averageRate = 0.0;
        this.distance = 0.0;
        this.date = "";
    }

    public Trainings(int idTraining, int idUser, String typeTraining, Double caloriesBurned, long duration, Double averageSpeed, Double averageRate, Double distance, String date) {
        this.idTraining = idTraining;
        this.idUser = idUser;
        this.typeTraining = getType(typeTraining);
        this.caloriesBurned = caloriesBurned;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.averageRate = averageRate;
        this.distance = distance;
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public String getTypeTraining() {
        return typeTraining.toString();
    }

    public void setTypeTraining(String typeTraining) {
        this.typeTraining = getType(typeTraining);
    }

    public Type getRouteType() {
        return typeTraining;
    }

    public void setRouteType(Type routeType) {
        this.typeTraining = routeType;
    }

    public Double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Type getType(String type) {
        if (type.equals(Type.RUNNING.toString()))
            return Type.RUNNING;
        if (type.equals(Type.CYCLING.toString()))
            return Type.CYCLING;
        if (type.equals(Type.WALKING.toString()))
            return Type.WALKING;

        return Type.RUNNING;
    }

    public int getSringId() {
        switch (typeTraining) {
            case RUNNING:
                return R.string.train_running;
            case CYCLING:
                return R.string.train_cycling;
            case WALKING:
                return R.string.train_walking;
        }
        return R.string.train_running;
    }

    public int getImageId() {
        switch (typeTraining) {
            case RUNNING:
                return R.drawable.ic_correr;
            case CYCLING:
                return R.drawable.ic_cycling;
            case WALKING:
                return R.drawable.ic_walking;
        }
        return R.drawable.ic_correr;
    }
}
