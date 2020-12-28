package com.example.pkutoolman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.pkutoolman.baseclass.Post;

import java.util.regex.Pattern;


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
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        username = (EditText) findViewById(R.id.username);  //获取用户名
        pass = (EditText) findViewById(R.id.register_password);  //获取密码
        pass_again = (EditText) findViewById(R.id.register_password_again);  //获取密码
        email = (EditText) findViewById(R.id.register_email);  //获取邮箱
        phone = (EditText) findViewById(R.id.register_phone);  //获取用户名

        username.addTextChangedListener(new RegisterActivity.JumpTextWatcher_username()); //输入回车符号则跳至password文本框
        pass.addTextChangedListener(new RegisterActivity.JumpTextWatcher_pass());
        pass_again.addTextChangedListener(new RegisterActivity.JumpTextWatcher_pass_again());
        email.addTextChangedListener(new RegisterActivity.JumpTextWatcher_email());
        phone.addTextChangedListener(new RegisterActivity.JumpTextWatcher_phone());//输入回车符号则视为点击注册操作
    }

    //注册
    public void  Register(View v) throws JSONException {
        registerCheck();
    }

    public boolean Check_email(String email){
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return Pattern.matches(pattern,email);
    }
    public boolean Check_phone(String phone){
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        return Pattern.matches(pattern,phone);
    }
    public boolean Check_pass(String pass){
        String pattern = "\\w{4,17}$";
        return Pattern.matches(pattern,pass);
    }

    //注册验证
    public void registerCheck() throws JSONException {
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
        if (!Check_email(email.getText().toString())){
            Toast.makeText(RegisterActivity.this, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Check_phone(phone.getText().toString())){
            Toast.makeText(RegisterActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Check_pass(pass.getText().toString())){
            Toast.makeText(RegisterActivity.this, "请输入有效密码，长度6-18位\n仅包含数字、字母、下划线", Toast.LENGTH_SHORT).show();
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

            String code = (result_json.getString("code")).toString();

            String message = (result_json.get("message")).toString();

            if(code.equals("200")){
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else if(code.equals("500") && message.equals("email used!")){
                Toast.makeText(this, "该邮箱已被注册", Toast.LENGTH_SHORT).show();
            }
            else if(code.equals("500") && message.equals("phoneNum used!")) {
                Toast.makeText(this, "该手机已被注册", Toast.LENGTH_SHORT).show();
            }
            else if(code.equals("500") && message.equals("nickname used!")){
                Toast.makeText(this, "该昵称已被使用", Toast.LENGTH_SHORT).show();
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
                    registerCheck();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void  Back(View v){
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}