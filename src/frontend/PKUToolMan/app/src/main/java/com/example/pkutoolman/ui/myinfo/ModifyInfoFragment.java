package com.example.pkutoolman.ui.myinfo;

import androidx.lifecycle.ViewModelProvider;

import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pkutoolman.MD5;
import com.example.pkutoolman.MainActivity;
import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Post;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class ModifyInfoFragment extends Fragment {

    ModifyInfoViewModel mViewModel;
    Button btModify,btReturn;
    EditText newemail,newnickname,newphone;
    EditText oldpass,newpass,repass;

    public static ModifyInfoFragment newInstance() {
        return new ModifyInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modifymyinfo, container, false);

        newnickname = (EditText) root.findViewById(R.id.student_nickname);
        newnickname.setHint(Data.getNickName());
        newemail = (EditText) root.findViewById(R.id.student_newemail);
        newemail.setHint(Data.getEmail());
        newphone = (EditText) root.findViewById(R.id.student_newphone);
        newphone.setHint(Data.getPhoneNum());

        oldpass = (EditText) root.findViewById(R.id.student_oldpassword);
        newpass = (EditText) root.findViewById(R.id.student_newpassword);
        repass = (EditText) root.findViewById(R.id.student_repassword);

        newnickname.addTextChangedListener(new JumpTextWatcher_newnickname());
        newemail.addTextChangedListener(new JumpTextWatcher_newemail());
        newphone.addTextChangedListener(new JumpTextWatcher_newphone());
        oldpass.addTextChangedListener(new JumpTextWatcher_oldpass());
        newpass.addTextChangedListener(new JumpTextWatcher_newpass());
        repass.addTextChangedListener(new JumpTextWatcher_repass());

        btModify = root.findViewById(R.id.modifyinfo_button);
        btReturn = root.findViewById(R.id.returnfrommodify_button);
        Fragment ptThis = this;
        btModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Try_Modify();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //NavController controller = Navigation.findNavController(v);
                //controller.navigate(R.id.navigation_myinfo);
            }
        });

        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.navigation_myinfo);
            }
        });

        return root;
    }
    private class JumpTextWatcher_newnickname implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                newnickname.setText(str.replace("\r","").replace("\n",""));
                newemail.requestFocus();
                newemail.setSelection(newemail.getText().length());
            }
        }
    }
    private class JumpTextWatcher_newemail implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                newemail.setText(str.replace("\r","").replace("\n",""));
                newphone.requestFocus();
                newphone.setSelection(newphone.getText().length());
            }
        }
    }
    private class JumpTextWatcher_newphone implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                newphone.setText(str.replace("\r","").replace("\n",""));
                oldpass.requestFocus();
                oldpass.setSelection(oldpass.getText().length());
            }
        }
    }
    private class JumpTextWatcher_oldpass implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                oldpass.setText(str.replace("\r","").replace("\n",""));
                newpass.requestFocus();
                newpass.setSelection(newpass.getText().length());
            }
        }
    }
    private class JumpTextWatcher_newpass implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                newpass.setText(str.replace("\r","").replace("\n",""));
                repass.requestFocus();
                repass.setSelection(repass.getText().length());
            }
        }
    }
    private class JumpTextWatcher_repass implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s,int start,int before,int count) {}

        @Override
        public void afterTextChanged(Editable s){
            String str = s.toString();
            if (str.contains("\r") || str.contains("\n")) {
                repass.setText(str.replace("\r","").replace("\n",""));
                try {
                    Try_Modify();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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


    public void Try_Modify() throws JSONException {
        String newnickname_s = newnickname.getText().toString().trim();
        String newemail_s = newemail.getText().toString().trim();
        String newphone_s = newphone.getText().toString().trim();
        String oldpass_s = oldpass.getText().toString();
        String newpass_s = newpass.getText().toString();
        String repass_s = repass.getText().toString();

        if (TextUtils.isEmpty(oldpass.getText().toString())){
            Toast.makeText(getContext(),"原密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newpass_s.equals(repass_s)){
            Toast.makeText(getContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newnickname.getText().toString())){
            newnickname_s = Data.getNickName();
        }
        if (TextUtils.isEmpty(newemail.getText().toString())){
            newemail_s = Data.getEmail();
        }
        if (TextUtils.isEmpty(newphone.getText().toString())){
            newphone_s = Data.getPhoneNum();
        }
        if (!Check_email(newemail_s)) {
            Toast.makeText(getContext(),"请输入有效的邮箱",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Check_phone(newphone_s)) {
            Toast.makeText(getContext(),"请输入有效的手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(newpass.getText().toString())){
            if (!Check_pass(newpass_s)) {
                Toast.makeText(getContext(),"请输入有效密码，长度6-18位\n仅包含数字、字母、下划线",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else newpass_s = oldpass_s;

        System.out.println("Modify Check Pass");

        String request_modify_json = "{" + "\"nickname\":" + "\"" + newnickname_s + "\"" + ","
                + "\"password\":" + "\"" + MD5.encrypt(oldpass_s) + "\"" + ","
                + "\"newPassword\":" + "\"" + MD5.encrypt(newpass_s) + "\"" + ","
                + "\"email\":" + "\"" + newemail_s + "\"" + ","
                + "\"phoneNum\":" + "\"" + newphone_s + "\""+","
                + "\"id\":" + "\"" + Data.getUserID() + "\""
                + "}";

        System.out.println(request_modify_json);

        JSONObject result_json = Post.post("http://121.196.103.2:8080/user/modify",request_modify_json,Data.getToken());

        System.out.println("modify_result:");
        System.out.println(result_json);

        if (result_json==null){
            Toast.makeText(getContext(),"无网络连接，请重试",Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(result_json.getString("code"));
        String result = (result_json.getString("code")).toString();

        if (result.equals("200")){
            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
            Data.setNickName(newnickname_s);
            Data.setEmail(newemail_s);
            Data.setPhoneNum(newphone_s);
        }
        else {
            Toast.makeText(getContext(),"服务器错误，请重试",Toast.LENGTH_SHORT).show();
        }
    }


}