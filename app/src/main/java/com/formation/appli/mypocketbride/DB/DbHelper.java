package com.formation.appli.mypocketbride.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.formation.appli.mypocketbride.DB.DAO.UserDAO;

/**
 * Created by student on 11-07-17.
 */

public class DbHelper extends SQLiteOpenHelper {

        public static final String DB_NAME = "com.formation.appli.mypocketbride.database";
        public static final int DB_VERSION = 1;

        public DbHelper(Context context) {
            super(context,DB_NAME,null,DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(UserDAO.CREATE_REQUEST);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(UserDAO.DELETE_REQUEST);
            db.execSQL(UserDAO.CREATE_REQUEST);
        }

}
