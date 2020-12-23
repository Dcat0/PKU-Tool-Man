package com.example.pkutoolman;

import com.example.pkutoolman.baseclass.Post;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username;  //用户名
    EditText password;  //密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        //隐藏标题栏
        getSupportActionBar().hide();
        username = (EditText) findViewById(R.id.username);  //获取用户名
        password = (EditText) findViewById(R.id.password);  //获取密码
        username.addTextChangedListener(new JumpTextWatcher_username()); //输入回车符号则跳至password文本框
        password.addTextChangedListener(new JumpTextWatcher_password()); //输入回车符号则视为登入
    }

    //override EditText的监听
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
                try {
                    login_check(user_in,password_md5);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

        }
    }

    //点击登陆按钮
    public void  Login(View v) throws JSONException {
        EditText username;  //用户名
        EditText pass;  //密码
        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.password);  //获取密码
        String user = username.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password_md5 = MD5.encrypt(password);
        login_check(user,password_md5);
    }

    //跳转到注册界面
    public void Register(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    //验证username和password
    private void login_check(String user_in,String password_in) throws JSONException {

        if (TextUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String default_name = "hello";
        String default_pass = "1234";

        //创建请求json
        String request_login_json = "{" + "\"username\":"+"\"" + user_in + "\"" + ","
                + "\"password\":" + "\"" + password_in + "\"" + "}";

        System.out.println(request_login_json);

        JSONObject result_json = Post.post("http://121.196.103.2:8080/user/login", request_login_json);

        System.out.println("login_result:");

        //断网情况下，会返回null
        if(result_json == null){
            Toast.makeText(this, "无网络连接，请重试", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(result_json.toString());

        String code = (result_json.getString("code")).toString();
        String message = (result_json.getString("message")).toString();

        System.out.println(code);
        System.out.println(code.length());

        if (code.equals("200")) {
            Toast.makeText(this, "欢迎登入", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(code.equals("500") && message.equals("password wrong!")){
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(code.equals("500") && message.equals("user not exist")){
            Toast.makeText(this, "该用户不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(this, "服务器错误，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}

