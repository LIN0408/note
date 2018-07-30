package com.example.administrator.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    static final String KEY_ID="_id";
    static final String KEY_DATE="date";
    static final String KEY_MEMO="memo";
    static final String KEY_REMINDER="reminder";
    static final String KEY_BACKGROUNDCOLOUR="backgroundColor";
    static final String DATABASE_NAME="todolist";
    static final String TABLE_NAME="todolistTable";
    static final int DB_VERSION=1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String DATABASE_CREATE=" CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                " (" + KEY_ID + " integer PRIMARY KEY autoincrement, " +
                    KEY_DATE + ","  +
                    KEY_MEMO + ","  +
                    KEY_REMINDER + "," +
                    KEY_BACKGROUNDCOLOUR +");";

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }
}
