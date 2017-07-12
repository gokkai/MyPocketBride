package com.formation.appli.mypocketbride.DB.DAO;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.DB.DbHelper;
import com.formation.appli.mypocketbride.Messages;

/**
 * Created by student on 11-07-17.
 */

public class MessageDAO {
    public static final String TABLE_MESSAGE = "message";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_ADDRESSCONTEXT = "adressContext";
    public static final String COLUMN_BEHAVIOUR ="behaviour";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TEXT + " TEXT NOT NULL, "
                    + COLUMN_ADDRESSCONTEXT + " INTEGER NOT NULL, "
                    + COLUMN_BEHAVIOUR +" INTEGER NOT NULL "
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_MESSAGE + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public MessageDAO(Context context) {
        this.context = context;
    }

    public MessageDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public MessageDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues messageToContentValues(Messages m) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, m.getText());
        cv.put(COLUMN_ADDRESSCONTEXT, m.getAdressContext());
        cv.put(COLUMN_BEHAVIOUR, m.getBehaviour());

        return cv;
    }

    public Messages insert(Messages m) {
        ContentValues cv = messageToContentValues(m);

        long id = db.insert(TABLE_MESSAGE, null, cv);
        m.setId((int) id);

        Messages check = (id != -1) ? m : null;
        return check;
    }

    public void delete(Messages m) {
        String whereClause = COLUMN_ID + " = " + m.getId();
        db.delete(TABLE_MESSAGE, whereClause, null);
    }
    public long update(Messages m) {
        ContentValues cv = messageToContentValues(m);

        String whereClause = COLUMN_ID + " = " + m.getId();
        long number = db.update(TABLE_MESSAGE, cv, whereClause, null);
        return number;
    }
    private Messages cursorToMessages(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        String text= c.getString(c.getColumnIndex(COLUMN_TEXT));
        int addressContext= c.getInt(c.getColumnIndex(COLUMN_ADDRESSCONTEXT));
        int behaviour= c.getInt(c.getColumnIndex(COLUMN_BEHAVIOUR));


        return new Messages(id,text,addressContext,behaviour);
    }
    public Messages[] getAllInContext(int addressContext){

        String whereClause = COLUMN_ADDRESSCONTEXT + " = " +addressContext;
        Cursor c = db.query(TABLE_MESSAGE,null, whereClause, null, null, null, null);

        int count = c.getCount();

        if(count > 0) {
            Messages[] messages = new Messages[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
               messages[i] = cursorToMessages(c);
            }
            return messages;
        }
        return null;
    }
}
