package com.example.nasrf.githubtask.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nasrf on 4/1/2018.
 */

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper (Context context){ super(context, "task", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table data(id integer primary key autoincrement,name text,repo text,desc text,fork text,source text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table data");
        onCreate(db);
    }
}
