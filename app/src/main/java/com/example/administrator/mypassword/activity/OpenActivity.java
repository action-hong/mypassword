package com.example.administrator.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;

public class OpenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if(isFirst)
            intent = new Intent(this,LoginActivityOne.class);
        else
            intent = new Intent(this,LoginActivityTwo.class);
        startActivity(intent);
        finish();
    }

}
