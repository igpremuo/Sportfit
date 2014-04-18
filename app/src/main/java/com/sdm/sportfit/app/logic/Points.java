package com.sdm.sportfit.app.logic;

import android.location.Location;

/**
 * Created by Jess on 8/04/14.
 */
public class Points {
    private int id;
    private Location location;
    private Double speed;
    private long idTraining;

    public Points() {
        this.location = null;
        this.speed = 0.0;
        this.idTraining = 0;
    }

    public Points(Location location, Double speed, long idTraining) {
        this.location = location;
        this.speed = speed;
        this.idTraining = idTraining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public long getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }
}
