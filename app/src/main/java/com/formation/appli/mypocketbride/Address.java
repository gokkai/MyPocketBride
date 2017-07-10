package com.formation.appli.mypocketbride;

/**
 * Created by student on 03-07-17.
 */

public class Address {
    private double longitude, latitude;
    private int adressContext; //Context will be attribute int 1 for home, 2 for work, etc...

        //region getter/setter
    public double getlatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getAdressContext() {
        return adressContext;
    }

    public void setAdressContext(int adressContext) {
        this.adressContext = adressContext;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //endregion


    public Address(double latitude, double longitude, int context) {
        this.longitude = longitude;
        this.latitude=latitude;
        this.adressContext=context;
    }
}
