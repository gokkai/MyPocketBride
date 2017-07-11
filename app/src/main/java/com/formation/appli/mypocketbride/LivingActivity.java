package com.formation.appli.mypocketbride;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import com.formation.appli.mypocketbride.GPS.Localisation;
import com.formation.appli.mypocketbride.GPS.Position;

public class LivingActivity extends AppCompatActivity implements Localisation.ILocalisation{
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final static Address home=new Address(50.806784,4.343913);
    private final static Address work=new Address(50.837803,4353648);
    //private final static Companion hatsune=new Companion("Hatsune",1,0);
    private final static Messages messageHome=new Messages("Bienvenue à la maison", 1,0);
    private final static Messages messageWork=new Messages("Bon courage pour le travail", 2,0);


    private TextView longi, lati;

    private double latitude,longitude, latitudeAddress,longitudeAdress;
    private Localisation localisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);
        longi=(TextView)findViewById(R.id.tv_la_longitude);
        lati=(TextView)findViewById(R.id.tv_la_latitude);
        localiser();

    }
    private void sendNotif(NotificationCompat.Builder builder){
        Intent intent =new Intent(this, LivingActivity.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LivingActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());



    }
    private void localiser(){
        Localisation gps=new Localisation();
        gps.setCallback(this);
        gps.askLocation(this);
    }

    public void getLocation(Position position) {
        longitude=position.getX();
        latitude=position.getY();
        lati.setText(String.valueOf(latitude));
        longi.setText(String.valueOf(longitude));
       /* if(latitude==work.getlatitude()&&longitude==work.getLongitude()){
            NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.hm_logo);
            mBuilder.setContentTitle(hatsune.getName());
            mBuilder.setContentText(messageWork.getText());
            sendNotif(mBuilder);
            //developper en envoyant simplement le adressContext après la mise en place de la DB
            //A voir en fonction de la manière dont on recccupere le msg


        }else if (latitude==home.getlatitude()&&longitude==home.getLongitude()){
            NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.hm_logo);
            mBuilder.setContentTitle(hatsune.getName());
            mBuilder.setContentText(messageHome.getText());

        }
*/
        //Log.w("LOCALISATION",position.toString());
    }
    public void  onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    localiser();

                } else {

                    // TODO
                }

            }


        }
    }
}