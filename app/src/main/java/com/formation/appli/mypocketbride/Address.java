package com.formation.appli.mypocketbride;

/**
 * Created by student on 03-07-17.
 */

public class Address {
    private double longitude, latitude;
    private int id;

        //region getter/setter
    public double getlatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /*public int getAdressContext() {
        return adressContext;
    }

    public void setAdressContext(int adressContext) {
        this.adressContext = adressContext;
    }*/

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //endregion


    public Address(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude=latitude;
    }

    public Address(int id,double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude=latitude;
        this.id = id;
    }
}
