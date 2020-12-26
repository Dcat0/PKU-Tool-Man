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


public class ForgetActivity extends AppCompatActivity {
    EditText pass;  //密码
    EditText pass_again; //再次输入密码
    EditText email;  //邮箱
    EditText phone;  //手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        email = (EditText) findViewById(R.id.forget_email);  //获取邮箱
        phone = (EditText) findViewById(R.id.forget_phone);  //获取手机号
        pass = (EditText) findViewById(R.id.forget_password);  //获取密码
        pass_again = (EditText) findViewById(R.id.forget_password_again);  //获取密码

        email.addTextChangedListener(new ForgetActivity.JumpTextWatcher_email());
        phone.addTextChangedListener(new ForgetActivity.JumpTextWatcher_phone());
        pass.addTextChangedListener(new ForgetActivity.JumpTextWatcher_pass());
        pass_again.addTextChangedListener(new ForgetActivity.JumpTextWatcher_pass_again());//输入回车符号则视为点击注册操作
    }

    //
    public void Forget(View v) throws JSONException {
        forget_check();
    }

    //注册验证
    public void forget_check() throws JSONException {
        String email_forget = email.getText().toString().trim();
        String phone_forget = phone.getText().toString().trim();
        String password_forget = pass.getText().toString().trim();
        String password_again_forget = pass_again.getText().toString().trim();


        if (TextUtils.isEmpty(pass.getText().toString())) {
            Toast.makeText(ForgetActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass_again.getText().toString())) {
            Toast.makeText(ForgetActivity.this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(ForgetActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(ForgetActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((email.getText().toString()).indexOf("@") == -1){
            Toast.makeText(ForgetActivity.this, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password_again_forget.equals(password_forget)){//判断两次数的密码是否相同
            //创建请求json
            String password_forget_md5 = MD5.encrypt(password_forget);

            String request_forget_json = "{" + "\"email\":" + "\"" + email_forget + "\"," + "\"phoneNum\":"
                    + "\"" + phone_forget + "\"," + "\"newPassword\":" + "\"" + password_forget_md5 + "\"" + "}";

            System.out.println(request_forget_json);

            JSONObject result_json = Post.post("http://121.196.103.2:8080/user/forget",request_forget_json);


            System.out.println("forget_result:");
            System.out.println(result_json);

            //断网情况下，会返回null
            if(result_json == null){
                Toast.makeText(this, "无网络连接，请重试", Toast.LENGTH_SHORT).show();
                return;
            }

            System.out.println(result_json.getString("code"));

            String code = (result_json.getString("code")).toString();
            String message = (result_json.getString("message")).toString();

            if(code.equals("200")){
                Toast.makeText(this, "重置成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(ForgetActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else if(code.equals("500") && message.equals("email not exist")){
                    Toast.makeText(this, "此邮箱未被注册", Toast.LENGTH_SHORT).show();
            }
            else if(code.equals("500") && message.equals("phoneNum wrong")){
                Toast.makeText(this, "手机与邮箱不匹配", Toast.LENGTH_SHORT).show();
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
                try {
                    forget_check();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void  ForgetBack(View v){
        Intent intent = new Intent();
        intent.setClass(ForgetActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}