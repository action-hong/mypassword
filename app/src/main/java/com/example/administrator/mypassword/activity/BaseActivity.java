package com.example.administrator.mypassword.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mypassword.db.MyPasswordDB;

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public MyPasswordDB db;

    public boolean isFirst;//第一次使用该app
    public boolean isChangeLogin;//更改登录密码
    public String  loginPassword;

    public final static String IS_FIRST = "is_first";
    public final static String IS_CHANGE_LOGIN = "is_change_login";
    public final static String PASSWORD_TAG = "password_tag";
    public final static String DATA_OF_PASSWORD = "password_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        isFirst = pref.getBoolean(IS_FIRST,true);
        isChangeLogin = pref.getBoolean(IS_CHANGE_LOGIN,false);
        loginPassword = pref.getString(PASSWORD_TAG,"");
        db = MyPasswordDB.getInstance(this);
    }
}
