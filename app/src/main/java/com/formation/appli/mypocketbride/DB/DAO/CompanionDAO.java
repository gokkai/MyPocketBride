package com.formation.appli.mypocketbride.DB.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.Companion;
import com.formation.appli.mypocketbride.DB.DbHelper;


/**
 * Created by student on 11-07-17.
 */

public class CompanionDAO {
    public static final String TABLE_COMPANION = "companion";

    public static final String COLUMN_ID = "_id";
    //public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BEHAVIOUR = "behaviour";
    public static final String COLUMN_SEX="sex";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COMPANION+ " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    //+ COLUMN_NAME + " TEXT NOT NULL, "
                    + COLUMN_BEHAVIOUR + " INTEGER NOT NULL, "
                    + COLUMN_SEX+" INTEGER NOT NULL "
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_COMPANION + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public CompanionDAO(Context context) {
        this.context = context;
    }

    public CompanionDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public CompanionDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues companionToContentValues(Companion c) {
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_NAME, c.getName());
        cv.put(COLUMN_BEHAVIOUR, c.getBehaviour());
        cv.put(COLUMN_SEX, c.getSex());

        return cv;
    }

    public Companion insert(Companion c) {
        ContentValues cv = companionToContentValues(c);

        long id = db.insert(TABLE_COMPANION, null, cv);
        c.setId((int) id);

        Companion check = (id != -1) ? c : null;
        return check;
    }

    public void delete(Companion c) {
        String whereClause = COLUMN_ID + " = " + c.getId();
        db.delete(TABLE_COMPANION, whereClause, null);
    }
    public long update(Companion c) {
        ContentValues cv = companionToContentValues(c);

        String whereClause = COLUMN_ID + " = " + c.getId();
        long number = db.update(TABLE_COMPANION, cv, whereClause,null);
        return  number;
    }

    private Companion cursorToCompanion(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        int behaviour = c.getInt(c.getColumnIndex(COLUMN_BEHAVIOUR));
        int sex= c.getInt(c.getColumnIndex(COLUMN_SEX));


        return new Companion(id,sex,behaviour);
    }


}
