package com.example.pkutoolman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.view.View;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.pkutoolman.baseclass.Post;


public class RegisterActivity extends AppCompatActivity {
    EditText username;  //用户名
    EditText pass;  //密码
    EditText pass_again; //再次输入密码
    EditText email;  //邮箱
    EditText phone;  //手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.password);  //获取密码
        pass_again = (EditText) findViewById(R.id.password_again);  //获取密码
        email = (EditText) findViewById(R.id.email);  //获取邮箱
        phone = (EditText) findViewById(R.id.phone);  //获取用户名

        username.addTextChangedListener(new RegisterActivity.JumpTextWatcher_username()); //输入回车符号则跳至password文本框
        pass.addTextChangedListener(new RegisterActivity.JumpTextWatcher_pass());
        pass_again.addTextChangedListener(new RegisterActivity.JumpTextWatcher_pass_again());
        email.addTextChangedListener(new RegisterActivity.JumpTextWatcher_email());
        phone.addTextChangedListener(new RegisterActivity.JumpTextWatcher_phone());//输入回车符号则视为点击注册操作
    }

    //注册
    public void  Register(View v) throws JSONException {
        register_check();
    }

    //注册验证
    public void register_check() throws JSONException {
        String username_register = username.getText().toString().trim();
        String password_register = pass.getText().toString().trim();
        String password_again_register = pass_again.getText().toString().trim();
        String email_register = email.getText().toString().trim();
        String phone_register = phone.getText().toString().trim();

        if (TextUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass_again.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((email.getText().toString()).indexOf("@") == -1){
            Toast.makeText(RegisterActivity.this, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password_again_register.equals(password_register)){//判断两次数的密码是否相同
            //创建请求json
            String request_register_json = "{" + "\"nickname\":" + "\"" + username_register + "\"" + ","
                    + "\"password\":" + "\"" + MD5.encrypt(password_register) + "\"" + ","
                    + "\"email\":" + "\"" + email_register + "\"" + ","
                    + "\"phoneNum\":" + "\"" + phone_register + "\""
                    + "}";

            System.out.println(request_register_json);

            JSONObject result_json = Post.post("http://121.196.103.2:8080/user/register",request_register_json);


            System.out.println("register_result:");
            System.out.println(result_json);

            //断网情况下，会返回null
            if(result_json == null){
                Toast.makeText(this, "无网络连接，请重试", Toast.LENGTH_SHORT).show();
                return;
            }

            System.out.println(result_json.getString("code"));

            String result = (result_json.getString("code")).toString();

            if(result.equals("200")){
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "服务器错误，请重试", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
        }
    }

    //override EditText监听
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
                pass.requestFocus();//让pass获取焦点
                pass.setSelection(pass.getText().length());//若pass有内容就将光标移动到文本末尾
            }

        }
    }

    private class JumpTextWatcher_pass implements TextWatcher {
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
                pass.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                pass_again.requestFocus();//让pass_again获取焦点
                pass_again.setSelection(pass_again.getText().length());//若pass_again有内容就将光标移动到文本末尾
            }

        }
    }

    private class JumpTextWatcher_pass_again implements TextWatcher {
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
                pass_again.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                email.requestFocus();//让email获取焦点
                email.setSelection(email.getText().length());//若email有内容就将光标移动到文本末尾
            }

        }
    }

    private class JumpTextWatcher_email implements TextWatcher {
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
                email.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                phone.requestFocus();//让pass获取焦点
                phone.setSelection(phone.getText().length());//若pass有内容就将光标移动到文本末尾
            }

        }
    }

    private class JumpTextWatcher_phone implements TextWatcher {
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
                phone.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                try {
                    register_check();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}