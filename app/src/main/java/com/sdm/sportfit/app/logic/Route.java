package com.sdm.sportfit.app.logic;

import com.sdm.sportfit.app.R;

import java.util.ArrayList;

/**
 * Created by nacho on 8/04/14.
 */
public class Route extends ArrayList<RoutePoint> {

    public enum Type {RUNNING, CYCLING, WALKING};

    private Type mRouteType;
    private String mDate;
    private double mDuration;
    private double mDistance;
    private double mAvgSpeed;


    public Type getRouteType() {
        return mRouteType;
    }

    public void setRouteType(Type routeType) {
        this.mRouteType = routeType;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date){
        mDate = date;
    }

    public int getRouteSringId() {
        switch (mRouteType) {
            case RUNNING:
                return R.string.train_running;
            case CYCLING:
                return R.string.train_cycling;
            case WALKING:
                return R.string.train_walking;
        }
        return R.string.train_running;
    }

    public double getDuration() {
        return mDuration;
    }

    public void setDuration(double duration) {
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
}
