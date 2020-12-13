package com.example.pkutoolman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View;
import android.content.Intent;


public class RegisterActivity extends AppCompatActivity {
    EditText name;  //用户名
    EditText pass;  //密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
    }
    //登录验证
    public void  Register(View v) {
        EditText username;  //用户名
        EditText pass;  //密码
        EditText pass_again; //再次输入密码
        EditText name;  //姓名
        EditText student_id;  //学号

        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.password);  //获取密码
        pass_again = (EditText) findViewById(R.id.password_again);  //获取用户名
        name = (EditText) findViewById(R.id.name);  //获取密码
        student_id = (EditText) findViewById(R.id.student_id);  //获取用户名

        String username_register = username.getText().toString().trim();
        String password_register = pass.getText().toString().trim();
        String password_again_register = pass_again.getText().toString().trim();
        String name_register = name.getText().toString().trim();
        String student_id_register = student_id.getText().toString().trim();

        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }


}