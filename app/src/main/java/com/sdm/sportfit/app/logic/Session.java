package com.sdm.sportfit.app.logic;

import com.sdm.sportfit.app.R;

import java.util.ArrayList;

/**
 * Created by nacho on 8/04/14.
 */
public class Session extends ArrayList<Point> {

    public enum Type {RUNNING, CYCLING, WALKING};

    private int mId;
    private Type mSessionType;
    private String mDate;
    private long mDuration;
    private double mDistance;
    private double mAvgSpeed;
    private double mCalories;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Type getRouteType() {
        return mSessionType;
    }

    public void setRouteType(Type routeType) {
        this.mSessionType = routeType;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date){
        mDate = date;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        this.mDistance = distance;
    }

    public double getAvgSpeed() {
        return mAvgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.mAvgSpeed = avgSpeed;
    }

    public double getCalories() {
        return mCalories;
    }

    public void setCalories(double calories) {
        mCalories = calories;
    }

    public int getSringId() {
        switch (mSessionType) {
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
        switch (mSessionType) {
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
