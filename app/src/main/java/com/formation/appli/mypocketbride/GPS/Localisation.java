package com.formation.appli.mypocketbride.GPS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.formation.appli.mypocketbride.LivingActivity;
import com.formation.appli.mypocketbride.MainActivity;

/**
 * Created by student on 04-07-17.
 */

public class Localisation implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //region Callback
    public interface ILocalisation {
        void getLocation(Position position);
    }

    private Context contextLiv;
    private ILocalisation callback;
    //private Activity      myActivity ;

    public void setCallback(ILocalisation callback) {
        this.callback = callback;
    }
    //endregion

    public void askLocation(Context context) {
        this.contextLiv=context;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (callback != null) {
                requestPermissions(context);
            }
            return;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (callback != null) {
            callback.getLocation(new Position(
                            location.getLatitude(),
                            location.getLongitude()
                    )
            );

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void requestPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
        }
    }





}