package com.formation.appli.mypocketbride.DB.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.Address;
import com.formation.appli.mypocketbride.DB.DbHelper;

/**
 * Created by student on 11-07-17.
 */

public class AddressDAO {
    public static final String TABLE_ADDRESS = "address";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_LONGITUDE + " FLOAT NOT NULL, "
                    + COLUMN_LATITUDE + " FLOAT NOT NULL "
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_ADDRESS + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public AddressDAO(Context context) {
        this.context = context;
    }

    public AddressDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public AddressDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues addressToContentValues(Address a) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LONGITUDE, a.getLongitude());
        cv.put(COLUMN_LATITUDE, a.getlatitude());

        return cv;
    }

    public Address insert(Address a) {
        ContentValues cv = addressToContentValues(a);

        long id = db.insert(TABLE_ADDRESS, null, cv);
        a.setId((int) id);

        Address check = (id != -1) ? a : null;
        return check;
    }

    public void delete(Address a) {
        String whereClause = COLUMN_ID + " = " + a.getId();
        db.delete(TABLE_ADDRESS, whereClause, null);
    }
    public long update(Address a) {
        ContentValues cv = addressToContentValues(a);

        String whereClause = COLUMN_ID + " = " + a.getId();
        long number = db.update(TABLE_ADDRESS, cv, whereClause,null);
        return  number;
    }
    private Address cursorToAddress(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        double lat = c.getFloat(c.getColumnIndex(COLUMN_LATITUDE));
        double longitude= c.getFloat(c.getColumnIndex(COLUMN_LONGITUDE));


        return new Address(id,lat,longitude);
    }

    public Address[] getAddress(int userId){

        //String whereClause = COLUMN_ID + " = " +addressId;
        //Cursor c = db.query(TABLE_ADDRESS,null, whereClause, null, null, null, null);
        String query="SELECT a._id,longitude,latitude FROM address AS a INNER JOIN location AS l ON "
                +"a._id=l.addressUser where l.userId="+userId;
        Cursor c=db.rawQuery(query,null);
        int count = c.getCount();

        if(count > 0) {
            Address[] addresses = new Address[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
                addresses[i] = cursorToAddress(c);
            }
            return addresses;
        }
        return null;
    }

}
