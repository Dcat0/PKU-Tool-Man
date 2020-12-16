package com.example.pkutoolman;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatActivity extends Activity {
    private ChatAdapter chatAdapter;
    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<ChatData> personChats = new ArrayList<ChatData>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;

                default:
                    break;
            }
        };
    };

    EditText et_chat_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_chat);
        /**
         * 虚拟4条发送方的消息
         */
        for (int i = 0; i <= 1; i++) {
            ChatData personChat = new ChatData();
            personChat.setMeSend(false);
            personChats.add(personChat);
        }

        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        Button btn_chat_message_send = (Button) findViewById(R.id.button_message_send);
        et_chat_message = (EditText) findViewById(R.id.et_chat_message);

        et_chat_message.addTextChangedListener(new ChatActivity.JumpTextWatcher());


        /**
         *setAdapter
         */
        chatAdapter = new ChatAdapter(this, personChats);
        lv_chat_dialog.setAdapter(chatAdapter);
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                send();
            }
        });
    }

    public void send(){
        if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
            Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatData personChat = new ChatData();
        //代表自己发送
        personChat.setMeSend(true);
        //得到发送内容
        personChat.setChatMessage(et_chat_message.getText().toString());
        //加入集合
        personChats.add(personChat);
        //清空输入框
        et_chat_message.setText("");
        //刷新ListView
        chatAdapter.notifyDataSetChanged();
        handler.sendEmptyMessage(1);
    }

    private class JumpTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.indexOf("\n") >= 0 | str.indexOf("\r") >= 0) {//发现输入回车符
                et_chat_message.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
                send();
            }

        }
    }

}
