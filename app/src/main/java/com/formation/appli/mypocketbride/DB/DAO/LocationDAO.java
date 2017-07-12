package com.formation.appli.mypocketbride.DB.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.DB.DbHelper;
import com.formation.appli.mypocketbride.UserLocation;

/**
 * Created by student on 11-07-17.
 */

public class LocationDAO {
    public static final String TABLE_LOCATION = "location";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESSCONTEXT = "addressContext";
    public static final String COLUMN_ADDRESSID = "addressId";
    public static final String COLUMN_USERID ="userID";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ADDRESSCONTEXT + " TEXT NOT NULL, "
                    + COLUMN_ADDRESSID + " INTEGER NOT NULL, "
                    + COLUMN_USERID +" INTEGER NOT NULL "
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_LOCATION + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public LocationDAO(Context context) {
        this.context = context;
    }

    public LocationDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public LocationDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues locationToContentValues(UserLocation l) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ADDRESSCONTEXT, l.getAddressContext());
        cv.put(COLUMN_ADDRESSID, l.getAddressId());
        cv.put(COLUMN_USERID, l.getUserId());

        return cv;
    }

    public UserLocation insert(UserLocation l) {
        ContentValues cv = locationToContentValues(l);

        long id = db.insert(TABLE_LOCATION, null, cv);
        l.setId((int) id);

        UserLocation check = (id != -1) ? l : null;
        return check;
    }

    public void delete(UserLocation l) {
        String whereClause = COLUMN_ID + " = " + l.getId();
        db.delete(TABLE_LOCATION, whereClause, null);
    }
    public long update(UserLocation l) {
        ContentValues cv = locationToContentValues(l);

        String whereClause = COLUMN_ID + " = " + l.getId();
        long number = db.update(TABLE_LOCATION, cv, whereClause,null);
        return  number;
    }
    private UserLocation cursorToLocation(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        int addressContext = c.getInt(c.getColumnIndex(COLUMN_ADDRESSCONTEXT));
        int addressId= c.getInt(c.getColumnIndex(COLUMN_ADDRESSID));
        int userId= c.getInt(c.getColumnIndex(COLUMN_USERID));

        return new UserLocation(id,addressContext,userId,addressId);
    }
    public UserLocation[] getAllHomeFromUser(int userId){

        String whereClause = COLUMN_USERID + " = " +userId+" AND "+COLUMN_ADDRESSCONTEXT+" = "+1;
        Cursor c = db.query(TABLE_LOCATION,null, whereClause, null, null, null, null);

        int count = c.getCount();

        if(count > 0) {
            UserLocation[] locations = new UserLocation[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
                locations[i] = cursorToLocation(c);
            }
            return locations;
        }
        return null;
    }
    public UserLocation[] getAllWorkFromUser(int userId){

        String whereClause = COLUMN_USERID + " = " +userId+" AND "+COLUMN_ADDRESSCONTEXT+" = "+2;
        Cursor c = db.query(TABLE_LOCATION,null, whereClause, null, null, null, null);


        int count = c.getCount();

        if(count > 0) {
            UserLocation[] locations = new UserLocation[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
                locations[i] = cursorToLocation(c);
            }
            return locations;
        }
        return null;
    }
    public int getContext(int adresId){
        String query="Select addressContext from location where addressId= "+adresId;
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int context=c.getInt(0);

        if(context> 0) {

           return context;
            }

        return -1;

    }


}
