package com.sdm.sportfit.app.logic;

/**
 * Created by nacho on 8/04/14.
 */
public class Point {

    private int mId;
    private float mLatitude;
    private float mLongitude;
    private double mSpeed;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float latitude) {
        this.mLatitude = latitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float longitude) {
        this.mLongitude = longitude;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public void setSpeed(double speed) {
        this.mSpeed = speed;
    }
}
