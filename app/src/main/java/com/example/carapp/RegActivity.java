package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {

    private EditText et_username = null;
    private EditText et_passwd1 = null;
    private EditText et_passwd2 = null;
    private EditText et_email = null;
    private EditText et_phone = null;
    private EditText et_idCard = null;
    private CheckBox cb_isDriver = null;
    private Button bt_submit = null;

    boolean isDriver = false;
    String Tag = "Activity";

    protected void initUI() {
        et_username = findViewById(R.id.et_username_reg);
        et_passwd1 = findViewById(R.id.et_passwd1_reg);
        et_passwd2 = findViewById(R.id.et_passwd2_reg);
        et_email = findViewById(R.id.et_email_reg);
        et_phone = findViewById(R.id.et_phone_reg);
        et_idCard = findViewById(R.id.et_idcard_reg);
        cb_isDriver = findViewById(R.id.cb_isDriver);
        bt_submit = findViewById(R.id.bt_submit_reg);
    }
    protected void bindUIEvent() {
        //set listener + new listener

        //checkbox + submit
        cb_isDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    isDriver = true;
                }
                else{
                    isDriver = false;
                }
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean reg_result = true;

                //getText()
                String username = et_username.getText().toString();
                String passwd1 = et_passwd1.getText().toString();
                String passwd2 = et_passwd2.getText().toString();
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();
                String idCard = et_idCard.getText().toString();

                //判断是否为空
                if (username.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"Username can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd1.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"Password can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd2.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"Please confirm your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passwd1.equals(passwd2)){
                    Toast.makeText(getApplicationContext(),"Your passwords don't match",Toast.LENGTH_SHORT).show();
                }
                if (email.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"email can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"phone number can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idCard.isEmpty() == true){
                    Toast.makeText(getApplicationContext(),"id card number can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                //将全部信息发送给远程服务器 从服务器得到一个 放在reg_result
                Intent intent = new Intent();
                if(reg_result == true){
                    //注册成功
                    Log.e(Tag,"register succeed"+username+",passwd="+passwd1+"email="+email+"phone="+phone+"idCard="+idCard);
                    Log.e(Tag,"isDriver="+isDriver);

                    if (isDriver == true){
                        intent.setClass(RegActivity.this,DriverActivity.class);
                    }
                    else{
                        intent.setClass(RegActivity.this,PassengerActivity.class);
                    }
                    startActivity(intent); //不要忘了startActivity(intent)
                }
                else{
                    //注册失败
                    Log.e(Tag,"register fail!"+username+",passwd="+passwd1+"email="+email+"phone="+phone+"idCard="+idCard);
                    Log.e(Tag,"isDriver="+isDriver);
                }
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initUI();
        bindUIEvent();
    }
    protected void onStart(){
        //调用父类
        super.onStart();
        Log.e(Tag,"onStart()...");

    }


}
