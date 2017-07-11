package com.formation.appli.mypocketbride.Geocodage;

import android.content.Context;
import android.os.AsyncTask;

import com.formation.appli.mypocketbride.GPS.Localisation;
import com.formation.appli.mypocketbride.GPS.Position;

/**
 * Created by student on 10-07-17.
 */

public class RequestGeocodage extends AsyncTask<String, Void, Position> {
    private static final String URL_BASE="https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String URL_KEY="AIzaSyC1JbavXi8xhmPnzPYBAfW0gZLaj903nAw";
    private  String URL_address;


    //region Callback
    public interface IRequestGeocodage {
        void getGeocodage(Position position);
    }

    //private Context contextLiv;
    private Localisation.ILocalisation callback;
    //private Activity      myActivity ;

    public void setCallback(Localisation.ILocalisation callback) {
        this.callback = callback;
    }
    //endregion


    @Override
    protected Position doInBackground(String[] objects) {
        //String URL_address=address you get from SignIn Activity .replace(" ","+");
        String url=URL_BASE+URL_address+"&key="+URL_KEY;
        return null;
    }

    @Override
    protected void onPostExecute(Position position) {
        super.onPostExecute(position);
    }
}
