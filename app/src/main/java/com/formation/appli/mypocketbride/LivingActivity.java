package com.formation.appli.mypocketbride;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.formation.appli.mypocketbride.DB.DAO.AddressDAO;
import com.formation.appli.mypocketbride.DB.DAO.InteractDAO;
import com.formation.appli.mypocketbride.DB.DAO.LocationDAO;
import com.formation.appli.mypocketbride.DB.DAO.MessageDAO;
import com.formation.appli.mypocketbride.GPS.Localisation;
import com.formation.appli.mypocketbride.GPS.Position;

import java.util.Random;

public class LivingActivity extends AppCompatActivity implements Localisation.ILocalisation{
    public int userId;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    //private static final  Address home=new Address(50.806784,4.343913);
    //private static final  Address work=new Address(50.837803,4353648);
    //private final static Companion hatsune=new Companion("Hatsune",1,0);
    //private static final  Messages messageHome=new Messages("Bienvenue à la maison", 1,0);
    //private static final  Messages messageWork=new Messages("Bon courage pour le travail", 2,0);


    private TextView longi, lati;

    private double latitude,longitude, latitudeAddress,longitudeAdress;
    private Localisation localisation;
    String textNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            userId = (int) bd.get("idUser");

        }
        longi=(TextView)findViewById(R.id.tv_la_longitude);
        lati=(TextView)findViewById(R.id.tv_la_latitude);
        localiser();

    }

    private void localiser(){
        Localisation gps=new Localisation();
        gps.setCallback(this);
        gps.askLocation(this);
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

    public void getLocation(Position position) {

        longitude=position.getX();
        latitude=position.getY();
        lati.setText(String.valueOf(latitude));
        longi.setText(String.valueOf(longitude));

        Address[] addresses=loadAddress(userId);
        for(int i=0;i<addresses.length;i++){
            double adressLati=addresses[i].getlatitude();
            double adressLongi= addresses[i].getLongitude();
            if(latitude==adressLati&&longitude==adressLongi){
                int adressId=addresses[i].getId();
                int context=getContext(adressId);
                Messages[] messages=getMessage(context);
                Random r = new Random();
                int random = r.nextInt(messages.length);
                textNotif=messages[random].getText();
                sendNotification(textNotif);
                //TODO get out of for?
            }
        }

    }
    private void sendNotification(String texto){

        String name=getName(userId);

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.hm_logo);
        mBuilder.setContentTitle(name);
        mBuilder.setContentText(texto);

        sendNotif(mBuilder);
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

 /*   private UserLocation[] loadHomeLocation(int userId) {
        LocationDAO dao = new LocationDAO(this);
        dao.openReadable();
        UserLocation[] location = dao.getAllHomeFromUser(userId);
        dao.close();
        return location;
    }
    private UserLocation[] loadWorkLocation(int userId) {
        LocationDAO dao = new LocationDAO(this);
        dao.openReadable();
        UserLocation[] location = dao.getAllWorkFromUser(userId);
        dao.close();
        return location;
    }*/
    //region get from DB
    private Address[] loadAddress(int userId) {
        AddressDAO dao = new AddressDAO(this);
        dao.openReadable();
        Address[] address = dao.getAddress(userId);
        dao.close();
        return address;
    }
    private int getContext(int adresId){
        LocationDAO dao=new LocationDAO(this);
        dao.openReadable();
        int context=dao.getContext(adresId);
        dao.close();
        return context;
    }
    private Messages[] getMessage(int context){
        MessageDAO dao=new MessageDAO(this);
        dao.openReadable();
        Messages[] messages=dao.getAllInContext(context);
        dao.close();
        return messages;
    }
    private String getName(int idUser){
        InteractDAO dao=new InteractDAO(this);
        dao.openReadable();
        String name=dao.getName(idUser);
        dao.close();
        return name;
    }

    //endregion
}