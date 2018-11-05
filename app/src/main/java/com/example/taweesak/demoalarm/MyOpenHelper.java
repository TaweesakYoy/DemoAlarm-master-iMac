package com.example.taweesak.demoalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{

    private Context context;
    public static final String DATABASE_NAME = "DemoAlarm.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_SQL_TABLE = "create table alarmTABLE (" +
            "id Integer Primary Key," +
            "NotiTime text," +
            "Day test," +
            "Month text," +
            "Hour text," +
            "Minute text);";


    public MyOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
