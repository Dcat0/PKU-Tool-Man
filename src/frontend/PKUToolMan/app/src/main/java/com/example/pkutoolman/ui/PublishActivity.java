package com.example.pkutoolman.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pkutoolman.R;

public class PublishActivity extends AppCompatActivity {
    public EditText orderPlace;
    public EditText orderDestination;
    public EditText orderDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_publish_order);
        //隐藏标题栏
        getSupportActionBar().hide();
        orderPlace = (EditText) findViewById(R.id.order_place);
        orderDestination = (EditText) findViewById(R.id.order_destination);
        orderDescription = (EditText) findViewById(R.id.order_description);


    }

    //点击返回按钮的响应
    public void back(View v){
        //可考虑加入提示
        finish();
    }

    //点击发布按钮的响应
    public void publish(View v){
        Toast toast = Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        finish();
    }
}
