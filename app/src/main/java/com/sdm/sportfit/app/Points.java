package com.sdm.sportfit.app;

import android.location.Location;

/**
 * Created by Jess on 8/04/14.
 */
public class Points {
    private int id;
    private Location location;
    private Double speed;
    private int idTraining;

    public Points() {
        this.id = 0;
        this.location = null;
        this.speed = 0.0;
        this.idTraining = 0;
    }

    public Points(int id, Location location, Double speed, int idTraining) {
        this.id = id;
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

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }
}
