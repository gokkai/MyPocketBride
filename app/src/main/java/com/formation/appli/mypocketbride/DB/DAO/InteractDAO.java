package com.formation.appli.mypocketbride.DB.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.DB.DbHelper;
import com.formation.appli.mypocketbride.Interact;

/**
 * Created by student on 11-07-17.
 */

public class InteractDAO {
    public static final String TABLE_INTERACT = "interact";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPANIONID = "companionId";
    public static final String COLUMN_USERID ="userID";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_INTERACT + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL, "
                    + COLUMN_COMPANIONID + " INTEGER NOT NULL, "
                    + COLUMN_USERID +" INTEGER NOT NULL "
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_INTERACT + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public InteractDAO(Context context) {
        this.context = context;
    }

    public InteractDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public InteractDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues interactToContentValues(Interact i) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, i.getName());
        cv.put(COLUMN_COMPANIONID, i.getCompanionId());
        cv.put(COLUMN_USERID, i.getUserId());

        return cv;
    }

    public Interact insert(Interact i) {
        ContentValues cv = interactToContentValues(i);

        long id = db.insert(TABLE_INTERACT, null, cv);
        i.setId((int) id);

        Interact check = (id != -1) ? i : null;
        return check;
    }

    public void delete(Interact i) {
        String whereClause = COLUMN_ID + " = " + i.getId();
        db.delete(TABLE_INTERACT, whereClause, null);
    }
    public long update(Interact i) {
        ContentValues cv = interactToContentValues(i);

        String whereClause = COLUMN_ID + " = " + i.getId();
        long number = db.update(TABLE_INTERACT, cv, whereClause,null);
        return  number;
    }
    private Interact cursorToInteract(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        String name= c.getString(c.getColumnIndex(COLUMN_NAME));
        int companionId = c.getInt(c.getColumnIndex(COLUMN_COMPANIONID));
        int userId= c.getInt(c.getColumnIndex(COLUMN_USERID));


        return new Interact(id,name,companionId,userId);
    }
    public Interact[] getAllCompanionFromUser(int userId){

        String whereClause = COLUMN_USERID + " = " +userId;
        Cursor c = db.query(TABLE_INTERACT,null, whereClause, null, null, null, null);

        int count = c.getCount();

        if(count > 0) {
            Interact[] interact = new Interact[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
                interact[i] = cursorToInteract(c);
            }
            return interact;
        }
        return null;
    }
    public String getName(int userId){
        String whereClause = COLUMN_USERID + " = " +userId;
        String[] Fields = {COLUMN_NAME};
        Cursor c = db.query(TABLE_INTERACT,Fields,whereClause, null, null, null, null);
        c.moveToFirst();
        String name=c.getString(0);
        if(name!="") {

            return name;
        }

        return null;
    }

}
