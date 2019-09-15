package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_name = null;
    private Button bt_test = null;
    private TextView tv_test2 = null;
    private EditText et_test = null;
    private CheckBox cb_test = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //相关联
        tv_name = findViewById(R.id.tv_username);
        bt_test = findViewById(R.id.bt_test);
        tv_test2 = findViewById(R.id.tv_test2);
        et_test = findViewById(R.id.et_test);
        bt_test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_name.setText("click");

                //从et_test获取文本
                String text = et_test.getText().toString();
                //将获取的文本给tv_test2
                tv_test2.setText(text);
            }
        });
    }
}
