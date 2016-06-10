package com.example.administrator.mypassword.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.mypassword.model.MyPassword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MyPasswordDB {

    public static final String DB_NAME = "my_password";

    public static final int VERSION = 1;

    private static MyPasswordDB myPasswordDB;

    private SQLiteDatabase db;

    private MyPasswordDB(Context context){
        MyPasswordOpenHeleper dbHelper = new MyPasswordOpenHeleper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /*
	 * 获取MyPasswordDB的实例
	 */
    public synchronized static MyPasswordDB getInstance(Context context) {
        if(myPasswordDB == null){
            myPasswordDB = new MyPasswordDB(context);
        }
        return myPasswordDB;
    }

    /**
     * 将MyPassword实例存储到数据库
     */
    public void saveMyPassword(MyPassword myPassword){
        if(myPassword != null){
            ContentValues values = new ContentValues();
            values.put("p_name", myPassword.getName());
            values.put("p_account", myPassword.getAccount());
            values.put("p_password", myPassword.getPassword());
            values.put("p_other", myPassword.getOthers());
            values.put("p_created_date", myPassword.getCreateDate());
            values.put("p_last_interview_date", myPassword.getLastInterviewDate());
            db.insert("Passwords", null, values);
        }
    }


    /**
     * 更新数据
     */
    public void updataMyPassword(MyPassword myPassword){
        ContentValues values = new ContentValues();
        values.put("p_name", myPassword.getName());
        values.put("p_account", myPassword.getAccount());
        values.put("p_password", myPassword.getPassword());
        values.put("p_other", myPassword.getOthers());
//		values.put("p_created_date text", myPassword.getCreateDate());不会变，所以不用加入
        values.put("p_last_interview_date", myPassword.getLastInterviewDate());
        db.update("Passwords", values, "p_created_date = ?", new String[] { myPassword.getCreateDate() });
    }


    /**
     * 删除数据
     */
    public void deleteMyPassword(MyPassword myPassword){
        db.delete("Passwords", "p_created_date = ?", new String[] { myPassword.getCreateDate() });

    }

    /**
     * 从数据库中读取所有的密码信息
     */
    public List<MyPassword> loadPsswords(){
        List<MyPassword> list = new ArrayList<MyPassword>();
        Cursor cursor = db.query("Passwords", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                MyPassword myPassword = new MyPassword();
                myPassword.setId(cursor.getInt(cursor.getColumnIndex("id")));
                myPassword.setName(cursor.getString(cursor.getColumnIndex("p_name")));
                myPassword.setAccount(cursor.getString(cursor.getColumnIndex("p_account")));
                myPassword.setPassword(cursor.getString(cursor.getColumnIndex("p_password")));
                myPassword.setOthers(cursor.getString(cursor.getColumnIndex("p_other")));
                myPassword.setCreateDate(cursor.getString(cursor.getColumnIndex("p_created_date")));
                myPassword.setLastInterviewDate(cursor.getString(cursor.getColumnIndex("p_last_interview_date")));
                list.add(myPassword);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }

    public Cursor getCursor(){
        return db.query("Passwords", null, null, null, null, null, null);

    }

}
