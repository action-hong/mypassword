package com.example.administrator.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mypassword.R;

public class LoginActivityOne extends BaseActivity {

    private String password_1;
    private String password_2;

    private EditText edit_1;
    private EditText edit_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity_one);
        bindView();
    }

    public void bindView(){
        edit_1 = (EditText) findViewById(R.id.input_password);
        edit_2 = (EditText) findViewById(R.id.input_password2);
    }

    public void login(View v){
        password_1 = edit_1.getText().toString();
        password_2 = edit_2.getText().toString();
        Intent intent;
        if(password_1.equals(password_2)){
            editor.putString(BaseActivity.PASSWORD_TAG,password_1);
            editor.commit();
            intent = new Intent(this,ListActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,R.string.error,Toast.LENGTH_SHORT).show();
            edit_1.setText("");
            edit_2.setText("");
        }
    }
}
