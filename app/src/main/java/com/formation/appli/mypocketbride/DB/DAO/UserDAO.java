package com.formation.appli.mypocketbride.DB.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.formation.appli.mypocketbride.DB.DbHelper;
import com.formation.appli.mypocketbride.User;

/**
 * Created by student on 11-07-17.
 */

public class UserDAO {
    public static final String TABLE_USER = "user";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PSWD = "psw";
    public static final String COLUMN_DATEOFBIRTH="dateOfBirth";
    public static final String COLUMN_SEX="sex";

    public static final String CREATE_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MAIL + " TEXT NOT NULL, "
                    + COLUMN_NICKNAME + " TEXT NOT NULL, "
                    + COLUMN_PSWD + " TEXT NOT NULL, "
                    + COLUMN_DATEOFBIRTH + " TEXT NOT NULL, "
                    + COLUMN_SEX+" INTEGER NOT NULL"
                    + " );";
    public static final String DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TABLE_USER + ";";


    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        this.context = context;
    }

    public UserDAO openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public UserDAO openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues userToContentValues(User user) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NICKNAME, user.getNickname());
        cv.put(COLUMN_MAIL, user.getMail());
        cv.put(COLUMN_PSWD, user.getMail());
        cv.put(COLUMN_DATEOFBIRTH, user.getDateOfBirth());
        cv.put(COLUMN_SEX, user.getSex());

        return cv;
    }

    public User insert(User u) {
        ContentValues cv = userToContentValues(u);

        long id = db.insert(TABLE_USER, null, cv);
        u.setId((int) id);

        User check = (id != -1) ? u : null;
        return check;
    }

    public void delete(User u) {
        String whereClause = COLUMN_ID + " = " + u.getId();
        db.delete(TABLE_USER, whereClause, null);
    }
    public long update(User u) {
        ContentValues cv = userToContentValues(u);

        String whereClause = COLUMN_ID + " = " + u.getId();
        long number = db.update(TABLE_USER, cv, whereClause,null);
        return  number;
    }

    private User cursorToUser(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        String nickname = c.getString(c.getColumnIndex(COLUMN_NICKNAME));
        String mail= c.getString(c.getColumnIndex(COLUMN_MAIL));
        String psw= c.getString(c.getColumnIndex(COLUMN_PSWD));
        String dateOfBirth= c.getString(c.getColumnIndex(COLUMN_DATEOFBIRTH));
        int sex= c.getInt(c.getColumnIndex(COLUMN_SEX));

        return new User(id,mail,psw,dateOfBirth,nickname,sex);
    }
    public User[] getUser(int mail){

        String whereClause = COLUMN_MAIL + " = " +mail;
        Cursor c = db.query(TABLE_USER,null, whereClause, null, null, null, null);

        int count = c.getCount();

        if(count > 0) {
            User[] user = new User[count];

            for(int i = 0; i < count; i++) {
                c.moveToPosition(i);
                user[i] = cursorToUser(c);
            }
            return user;
        }
        return null;
    }

}
