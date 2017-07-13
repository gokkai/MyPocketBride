package com.formation.appli.mypocketbride.GPS;

/**
 * Created by student on 04-07-17.
 */

public class Position {
    private double latitude, longitude;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //region getter/setter
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double x) {
        this.latitude = x;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double y) {
        this.longitude = y;
    }

    //endregion
}
