package com.example.pkutoolman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

    }
    //登录验证
    public void  Login(View v) {
        EditText username;  //用户名
        EditText pass;  //密码
        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.password);  //获取密码

        String default_name = "hello";
        String default_pass = "1234";

        String user = username.getText().toString().trim();
        String password = pass.getText().toString().trim();

        String user_post = ("user".concat(user)).concat("&");
        String password_post = "password".concat(password);
        String total_post = user_post.concat(password_post);

        String result = Post.sendPost("http://localhost:6144/user/login", total_post);

        if (result == "200") {
            Toast.makeText(this, "恭喜，通过", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(result == "401"){
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "服务器错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }


    public void Register(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}

