package com.example.administrator.mypassword.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MyPasswordOpenHeleper extends SQLiteOpenHelper{

    public static final String CREATE_PASSWORD = "create table Passwords ("
            + "id integer primary key autoincrement, "
            + "p_name text, "
            + "p_account text, "
            + "p_password text, "
            + "p_other text, "
            + "p_created_date text , "
            + "p_last_interview_date text)";

    public MyPasswordOpenHeleper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PASSWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
