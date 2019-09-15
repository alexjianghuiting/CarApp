package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    private TextView tv_username1 = null;
    private TextView tv_passwd = null;
    private EditText et_username = null;
    private EditText et_passwd = null;
    private CheckBox cb_isDriver = null;
    private Button bt_login = null;
    private Button bt_reg = null;
    private boolean isDriver = false;

    //定义一个attv控件
    private AutoCompleteTextView attv_username = null;

    String Tag = "LoginActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(Tag,"onCreate()...");
        setContentView(R.layout.activity_main2);
        initUI();
        //给登录按钮绑定事件
        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //调用一个C++方法
                OBOJNI.getInstance().hello_jni();
                boolean login_res = false;
                //获取用户名和密码
                String username = et_username.getText().toString();
                String passwd = et_passwd.getText().toString();

                if(username.isEmpty() == true){
                    //日志
                    Log.e(Tag,"Username is empty");
                    //弹出错误
                    Toast.makeText(getApplicationContext(),"Username is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(passwd.isEmpty() == true){
                    Log.e(Tag,"Password is empty");
                    Toast.makeText(getApplicationContext(),"Password is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                login_res = true;
                //checkbox绑定事件
                cb_isDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //创建isDriver = false
                        if (isChecked == true){
                            isDriver = true;
                        }
                        else {
                            isDriver = false;
                        }
                    }
                });
                //将用户名和密码发送给远程服务器 得到一个登陆的结果
                login_res = OBOJNI.getInstance().login(username,passwd,isDriver);

                if(login_res == true){
                    //登录成功
                    Log.e(Tag,"Login succeed username = "+ username + ", passwd = "+ passwd);
                    Log.e(Tag, "Type of user:"+ ((isDriver==true)?"Driver":"Customer")); //加括号

                    Intent intent = new Intent();
                    if(isDriver == true){
                        //需要跳转到司机界面
                        //给动作设置起始界面和目的界面
                        intent.setClass(Main2Activity.this, DriverActivity.class);
                    }
                    else{
                        //需要跳转到乘客界面
                        //给动作设置起始界面和目的界面
                        intent.setClass(Main2Activity.this, PassengerActivity.class);

                    }
                    //启动跳转
                    startActivity(intent);
                }
                else{
                    //登录失败
                    Log.e(Tag,"Login fail username = "+ username + ", passwd = "+ passwd);
                }
            }
        });

        //给注册按钮绑定一个事件
        bt_reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //跳转到注册界面
                //定义一个跳转对象
                Intent intent = new Intent();
                //给动作设置起始界面和目的界面
                intent.setClass(Main2Activity.this, RegActivity.class);
                //启动跳转
                startActivity(intent);
            }
        });

        //给attv控件设置一个阀值
        attv_username.setThreshold(1);
        //给autocompleteTextView绑定自动补齐
        attv_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当文本内容发生改变的时候 调用此回调函数
                //应该从服务器获取能够匹配的单词集合
                ArrayList<String> usernameList = new ArrayList<String>();

                usernameList.add("gailun");
                usernameList.add("gailun1");
                usernameList.add("gailun2");
                usernameList.add("gailun3");
                usernameList.add("gailun4");
                usernameList.add("gailun5");

                //给autoCompleteTextview设置一个适配器Adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,usernameList);
                //将Adapter和autoCompleteTextview相关联
                attv_username.setAdapter(adapter);
                //触发Adapter 触发控件显示 单词集合
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void initUI() {
        tv_username1 = findViewById(R.id.tv_username);
        tv_passwd = findViewById(R.id.tv_passwd);
        et_username = findViewById(R.id.et_username);
        et_passwd = findViewById(R.id.et_passwd);
        cb_isDriver = findViewById(R.id.cb_isDriver);
        bt_login = findViewById(R.id.bt_login);
        bt_reg = findViewById(R.id.bt_reg);
        attv_username = findViewById(R.id.attv_username);
    }
    protected void onStart(){
        super.onStart();
        Log.e(Tag,"onStart()...");
    }
    protected void onResume(){
        super.onResume();
        Log.e(Tag,"onResume()...");
    }
    protected void onRestart(){
        super.onRestart();
        Log.e(Tag,"onRestart()...");
    }
    protected void onStop(){
        super.onStop();
        Log.e(Tag,"onStop()...");
    }
    protected void onPause(){
        super.onPause();
        Log.e(Tag,"onPause()...");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.e(Tag,"onDestroy()...");
    }

}
