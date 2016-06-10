package com.example.administrator.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.mypassword.R;
import com.example.administrator.mypassword.model.MyPassword;
import com.example.administrator.mypassword.util.Util;

public class AddActivity extends BaseActivity {

    /**********DECLARES*************/
    private EditText p_name;
    private EditText p_account;
    private EditText p_word;
    private TextView create_time;
    private TextView last_time;
    private EditText p_other;
    /*
	 * 是否是从点击进来的添加界面
	 * 0为添加，非零为更新数据
	 */
    private int isAdd;

    /*
     *创建时间
     */
    private String firstTime;

    private Intent intent;
    MyPassword intentMyPassword;
    /**********INITIALIZES*************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);
        bindView();
        initData();

    }

    public void bindView(){
        p_name = (EditText) findViewById(R.id.p_name);
        p_account = (EditText) findViewById(R.id.p_account);
        p_word = (EditText) findViewById(R.id.p_word);
        create_time = (TextView) findViewById(R.id.create_time);
        last_time = (TextView) findViewById(R.id.last_time);
        p_other = (EditText) findViewById(R.id.p_other);
    }

    public void initData(){
        intent = getIntent();
        intentMyPassword = (MyPassword) intent
                .getSerializableExtra(BaseActivity.DATA_OF_PASSWORD);
        if (intentMyPassword == null) {
            // 不然null的话后面的 .getName()方法会报错
            intentMyPassword = new MyPassword();
            // 如果为空，则表示是第一次，使用getTime()，否则使用firstTime作为创建时间
            firstTime = System.currentTimeMillis() + "";

        } else {
            // 如果不是点击进来的，id默认为0
            // 我真的是曹操曹操曹操曹操曹操从草草草！！！！！！！！！！！！！
            // 为什么删除键一直用不了，原来是是这个isAdd键，居然是这样取值，getIntExtra("id", 0);
            // 难怪isAdd一直是0，所以删除键一直永不了 擦擦擦擦擦擦擦 擦擦擦擦擦才啊
            isAdd = intent.getIntExtra("p_position", 0);

            // 初始时间取值
            firstTime = intentMyPassword.getCreateDate();

            // 将取出的数据放入空间
            p_name.setText(intentMyPassword.getName());
            p_account.setText(intentMyPassword.getAccount());
            p_word.setText(intentMyPassword.getPassword());
            p_other.setText(intentMyPassword.getOthers());

            // 显示时间不是数据库的直接提出来用，还要再多一点加工，显示不必要知道到毫秒的级别，但计算需要

            create_time.setText(Util.getTime(intentMyPassword.getCreateDate()));
            last_time.setText(Util.getTime(intentMyPassword
                    .getLastInterviewDate()));
        }
    }

    public void addAndUpdate(View v){
        intent = new Intent(AddActivity.this,ListActivity.class);
        MyPassword mPassword = new MyPassword(p_name.getText().toString(),p_account.getText().toString(),
                p_word.getText().toString(),firstTime,System.currentTimeMillis() + "",p_other.getText().toString());
        //序列化传递
        intent.putExtra("password_data", mPassword);
        if(isAdd != 0){
            //不为0则为更新数据的position
            intent.putExtra("p_position", isAdd);
        }
        setResult(RESULT_OK,intent);
        finish();
    }

}
