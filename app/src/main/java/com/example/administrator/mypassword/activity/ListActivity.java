package com.example.administrator.mypassword.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.mypassword.R;
import com.example.administrator.mypassword.model.MyPassword;
import com.example.administrator.mypassword.util.Util;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.List;

public class ListActivity extends BaseActivity implements SlideAndDragListView.OnListItemLongClickListener,
        SlideAndDragListView.OnDragListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener,
        SlideAndDragListView.OnItemDeleteListener {

    private List<MyPassword> myPasswordList;
    private Toolbar toolbar;

    private com.yydcdut.sdlv.Menu mMenu;
    /*
     * 开源项目 listview 可露出删除键
     */
    private SlideAndDragListView<MyPassword> mListView;

    /*
  	 * 点击添加按钮进入存储活动界面
  	 */
    public static final int CLICK_ADDBUTTON_INTO_ADD = 1;
    /*
     * 点击item进入存储活动界面，可编辑，删除
     */
    public static final int CLICK_ITEM_INTO_ADD = 2;
    /*
     * 实例一个密码
     */
    private MyPassword myPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bindView();
        initMenu();
        initUiAndListener();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 从intent中取出MyPassword对象
        if(data == null)
            return;
        MyPassword fromAddMyPassword = (MyPassword) data
                .getSerializableExtra("password_data");
        if (fromAddMyPassword != null) {
            // 设定一下，不然一直各种nullpointException
            switch (requestCode) {
                case CLICK_ADDBUTTON_INTO_ADD:
                    if (resultCode == RESULT_OK && !fromAddMyPassword.isEmpty()) {
                        // 存入数据库
                        db.saveMyPassword(fromAddMyPassword);
                        myPasswordList.add(fromAddMyPassword);

                    }
                    break;
                case CLICK_ITEM_INTO_ADD:
                    if (resultCode == RESULT_OK) {
                        //更新的数据在list的position
                        int position = data.getIntExtra("p_position", 0);
                        db.updataMyPassword(fromAddMyPassword);
                        //替换,根据position替换,不用重新加载所有数据
                        myPasswordList.set(position, fromAddMyPassword);

                    }
                default:
                    break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initMenu() {
        mMenu = new com.yydcdut.sdlv.Menu(true, true,0);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img))
                .setBackground(new ColorDrawable(Color.RED))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.txt_size))
                .build());

    }

    private void initUiAndListener(){
        mListView = (SlideAndDragListView) findViewById(R.id.lv_edit);
        mListView.setMenu(mMenu);
        mListView.setAdapter(mAdapter);
        mListView.setOnDragListener(this, myPasswordList);
        mListView.setOnListItemClickListener(this);//点击
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);//删除
    }


    /**
     *第一次使用app,初始化点数据
     */
    public void initData(){
        String[] name = {"微博","微信","QQ","人人","支付宝"};
        //之前没有给myPassword一个引用，难怪一直报错
        myPassword = new MyPassword();
        for (int i = 0; i < name.length; i++) {
            myPassword.setName(name[i]);
            myPassword.setCreateDate(System.currentTimeMillis() + i + "");
            myPassword.setLastInterviewDate(myPassword.getCreateDate());
            db.saveMyPassword(myPassword);
        }
    }

    //适配器
    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.password_item, null);
                viewHolder.password_name = (TextView) convertView.findViewById(R.id.password_name);
                viewHolder.password_time = (TextView) convertView.findViewById(R.id.password_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MyPassword item = (MyPassword) getItem(position);
            Log.d("aaaa",item.getName());
            viewHolder.password_name.setText(item.getName());
            //时间只显示到日，后面的时分秒不要
            viewHolder.password_time.setText(Util.getTime(item.getLastInterviewDate()).substring(0, 10));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return myPasswordList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return myPasswordList.size();
        }

        class ViewHolder{
            TextView password_name;
            TextView password_time;
        }
    };

    //初始化部件
    public void bindView(){
        if(isFirst){
            initData();
            editor.putBoolean(IS_FIRST,false);
            editor.commit();
        }
        myPasswordList = db.loadPsswords();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // App Logo
        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle("密码管家");
        // Sub Title
//        toolbar.setSubtitle("Sub title");

        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add:
//                        Toast.makeText(ListActivity.this,"1",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ListActivity.this, AddActivity.class);
                        startActivityForResult(intent, CLICK_ADDBUTTON_INTO_ADD);
                        break;
                    case R.id.action_change:
                        Intent intent2 = new Intent(ListActivity.this, LoginActivityTwo.class);
                        editor.putBoolean(IS_CHANGE_LOGIN,true);
                        editor.commit();
                        startActivity(intent2);
                        break;
                    case R.id.action_out:
                        Util.sendPassword(ListActivity.this);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }



    @Override
    public void onDragViewStart(int position) {

    }

    @Override
    public void onDragViewMoving(int position) {

    }

    @Override
    public void onDragViewDown(int position) {

    }

    //删除
    @Override
    public void onItemDelete(View view, int position) {
        myPassword = myPasswordList.get(position - mListView.getHeaderViewsCount());
        db.deleteMyPassword(myPassword);
        myPasswordList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    //点击
    @Override
    public void onListItemClick(View v, int position) {
        // 根据id进入界面，更改界面也是通过id
        myPassword = myPasswordList.get(position);
        //点击，即把当前时间输入给最后访问的时间
        myPassword.setLastInterviewDate(System.currentTimeMillis() + "");
        Intent intent = new Intent(ListActivity.this, AddActivity.class);
		/*
		 * 传送数据
		 */
        intent.putExtra("password_data", myPassword);
		/*
		 * 传送position，便于更新之后list的数据更新
		 */
        intent.putExtra("p_position", position);

        //通过点击单位进入AddPassword活动
        startActivityForResult(intent, CLICK_ITEM_INTO_ADD);
    }

    @Override
    public void onListItemLongClick(View view, int position) {

    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                break;
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
//                    case 0:
//                        return Menu.ITEM_SCROLL_BACK;
                    case 0:
                        return com.yydcdut.sdlv.Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                }
        }
        return com.yydcdut.sdlv.Menu.ITEM_NOTHING;
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {

    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {

    }
}
