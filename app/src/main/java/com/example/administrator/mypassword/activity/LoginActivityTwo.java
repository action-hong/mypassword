package com.example.administrator.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mypassword.R;

public class LoginActivityTwo extends BaseActivity {

    private EditText edit;

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity_two);
        bindView();
    }

    public void bindView(){
        edit = (EditText)findViewById(R.id.input_password3);

    }

    public void loginFromTwo(View v){
        password = edit.getText().toString();
        if(password.equals(loginPassword)){
            if(isChangeLogin){//是否是更改登录密码
                startActivity(new Intent(this,LoginActivityOne.class));
            }
            else
                startActivity(new Intent(this,ListActivity.class));
            finish();
        }else{
            Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
            edit.setText("");
        }
    }

}
