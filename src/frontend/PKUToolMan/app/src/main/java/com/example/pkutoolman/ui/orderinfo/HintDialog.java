package com.example.pkutoolman.ui.orderinfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.Display;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pkutoolman.R;

public class HintDialog extends Dialog implements View.OnClickListener{
    //组件
    private TextView hintTitle,hintMessage;
    private Button btnCancel,btnConfirm;
    //组件内容
    private String title, message;

    //设置组件内容
    public void setMessage(String msg){
        message = msg;
    }

    public HintDialog(@NonNull Context context){
        super(context);
    }
    public HintDialog(@NonNull Context context, int themeResId){
        super(context, themeResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint_dialog);

        //设置弹窗宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p =getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x * 0.8);//是dialog的宽度为app界面的80%
        getWindow().setAttributes(p);

        //获得组件
        hintTitle = findViewById(R.id.hint_title);
        hintMessage = findViewById(R.id.hint_message);
        btnCancel = findViewById(R.id.bt_cancel);
        btnConfirm = findViewById(R.id.bt_confirm);

        //设置组件内容
        if (!TextUtils.isEmpty(title)){
            hintTitle.setText(title);
        }
        if (!TextUtils.isEmpty(message)){
            hintMessage.setText(message);
        }

        //添加组件点击事件
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:
                Toast.makeText(getContext(), "取消成功", Toast.LENGTH_LONG).show();
                dismiss();
                break;
            case R.id.bt_confirm:
                Toast.makeText(getContext(), "确认成功", Toast.LENGTH_LONG).show();
                dismiss();
                break;
        }
    }

}
