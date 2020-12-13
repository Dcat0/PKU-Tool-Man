package com.example.pkutoolman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    EditText username;  //用户名
    EditText password;  //密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        username = (EditText) findViewById(R.id.username);  //获取用户名
        password = (EditText) findViewById(R.id.password);  //获取密码
        username.addTextChangedListener(new JumpTextWatcher_username());
        password.addTextChangedListener(new JumpTextWatcher_password());
    }

    private class JumpTextWatcher_username implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.indexOf("\r") >= 0 || str.indexOf("\n") >= 0) {//发现输入回车符或换行符
                username.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                password.requestFocus();//让editText2获取焦点
                password.setSelection(password.getText().length());//若editText2有内容就将光标移动到文本末尾
            }

        }
    }

    private class JumpTextWatcher_password implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        //如果在password框输入
        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.indexOf("\r") >= 0 || str.indexOf("\n") >= 0) {//发现输入回车符或换行符
                password.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符号
                String user_in = username.getText().toString().trim();
                String password_in = password.getText().toString().trim();
                String password_md5 = MD5.encrypt(password_in);
                Login_check(user_in,password_md5);
            }

        }
    }

    //点击登陆按钮
    private void  Login(View v) {
        EditText username;  //用户名
        EditText pass;  //密码
        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.password);  //获取密码
        String user = username.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password_md5 = MD5.encrypt(password);
        Login_check(user,password_md5);
    }

    public void Register(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    //验证username和password
    private void Login_check(String user_in,String password_in){
        String default_name = "hello";
        String default_pass = "1234";

        String user_post = ("user".concat(user_in)).concat("&");
        String password_post = "password".concat(password_in);
        String total_post = user_post.concat(password_post);

        String result = Post.sendPost("http://localhost:6144/user/login", total_post);

        result = "200";

        if (result == "200") {
            Toast.makeText(this, "恭喜，通过", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(result == "401"){
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "服务器错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}

