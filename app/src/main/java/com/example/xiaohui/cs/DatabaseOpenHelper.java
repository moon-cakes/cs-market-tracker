package com.example.xiaohui.cs;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Xiaohui on 4/30/2016. References code from
 * http://stackoverflow.com/questions/513084/how-to-ship-an-android-application-with-a-database/11803319#11803319
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static DatabaseOpenHelper sInstance;

/*
    public static synchronized DatabaseOpenHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }
*/


    /*
    To facilitate a database upgrade, increment the version number that you pass to your SQLiteAssetHelper constructor:
      private static final int DATABASE_VERSION = 2;
     */
    private static final String DATABASE_NAME = "csgo.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}