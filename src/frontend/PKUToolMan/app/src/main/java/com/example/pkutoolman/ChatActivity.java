package com.example.pkutoolman;

import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;
import com.example.pkutoolman.ui.orderinfo.OrderinfoViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends Activity {
    ChatDatabase_sqlite chat_content;
    SQLiteDatabase db;

    private ChatAdapter chatAdapter;
    private ListView lv_chat_dialog;
    private List<ChatData> personChats = new ArrayList<ChatData>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    chatAdapter.notifyDataSetChanged();
                    lv_chat_dialog.setSelection(personChats.size()-1);
                    break;

                default:
                    break;
            }
        };
    };

    EditText et_chat_message;

    Timer timer = new  Timer();   //定义全局变量
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            int orderID = Data.getOrderID();
            System.out.println("time");
            String request_chat_json = "{\"orderID\":"+ String.valueOf(Data.getOrderID()) + ",\"userID\":" + String.valueOf(Data.getUserID()) +"}";
            System.out.println(request_chat_json);
            JSONObject result_json= Post.post("http://121.196.103.2:8080/chat/query", request_chat_json);
            if(result_json == null){
                Looper.prepare();
                Toast.makeText(ChatActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else{
                System.out.println(result_json.toString());
                JSONArray chat = null;
                try {
                    chat = (result_json.getJSONObject("data")).getJSONArray("chats");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int length = chat.length();
                System.out.println("length");
                System.out.println(length);
                for(int i = 0 ; i < length; i ++) {
                    try {
                        JSONObject row = chat.getJSONObject(i);
                        //System.out.println(row.get("senderId"));
                        //System.out.println(row.get("sendTime"));
                        //System.out.println(row.get("message"));
                        int senderID = Integer.parseInt(row.getString("senderId").toString());
                        int receiverID = Integer.parseInt(row.getString("receiverId").toString());
                        String send_time = row.getString("sendTime").toString();
                        String message = row.getString("message").toString();
                        send_time = send_time.replace("T"," ");

                        //显示用时
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = simpleDateFormat.parse(send_time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String send_time2 = sdf.format(date);

                        boolean isInSql = checkMessageInSql(orderID,senderID,receiverID,send_time,message);
                        if (!isInSql && receiverID == Data.getUserID() && senderID == Data.getChatID()) {
                            boolean my_send = false;
                            ChatData personChat = new ChatData();
                            personChat.setChatMessage(message);
                            personChat.setMeSend(false);
                            personChat.setTime(send_time2);
                            personChats.add(personChat);
                        }
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(length != 0) {
                    handler.sendEmptyMessage(1);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_chat);

        TextView text;
        text = (TextView) findViewById(R.id.to_whom);
        text.setText(Data.getOtherName());//显示聊天对象

        long delay = 1000;
        long intevalPeriod = 2000;

        int order_id = Data.getOrderID();
        int my_id = Data.getUserID();
        int other_id = Data.getChatID();

        System.out.println("订单ID");
        System.out.println(order_id);
        System.out.println("我的ID");
        System.out.println(my_id);
        System.out.println("对方ID");
        System.out.println(other_id);

        /*
        for (int i = 0; i <= 1; i++) {
            ChatData personChat = new ChatData();
            personChat.setChatMessage("我到了");
            personChat.setMeSend(false);
            personChat.setTime(test_send_time);
            personChats.add(personChat);
        }
         */

        //加载数据库中信息
        chat_content = new ChatDatabase_sqlite(ChatActivity.this);
        db = chat_content.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from chat where order_id =" + Integer.toString(order_id), null);

        while (cursor.moveToNext()){
            System.out.println("success2");
            int sender_id = cursor.getInt(cursor.getColumnIndex("sender_id"));
            String message_content = cursor.getString(cursor.getColumnIndex("message_content"));
            String message_time = cursor.getString(cursor.getColumnIndex("message_time"));
            System.out.println(message_content);
            //代表自己发送
            int is_my_send = 0;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(message_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String message_time2 = sdf.format(date);

            //通过判断sender_id和当前账户的id，判断是否为自己发送
            System.out.println(Data.getUserID());
            System.out.println(sender_id);
            if(sender_id == Data.getUserID()){
                is_my_send = 1;
            }
            else{
                is_my_send = 0;
            }

            System.out.println(is_my_send);

            ChatData personChat = new ChatData();

            if(is_my_send == 1){
                personChat.setMeSend(true);
            }
            else{
                personChat.setMeSend(false);
            }

            //添加发送内容
            personChat.setChatMessage(message_content);
            //添加时间
            personChat.setTime(message_time2);
            //加入集合
            personChats.add(personChat);
        }
        cursor.close();
        handler.sendEmptyMessage(1);
        
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
        timer.scheduleAtFixedRate(task, delay,intevalPeriod);

        btn_chat_message_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    send();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void send() throws JSONException {
        if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
            Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        int my_id = Data.getUserID();
        int other_id = Data.getChatID();

        ChatData personChat = new ChatData();
        //代表自己发送
        personChat.setMeSend(true);
        //得到发送内容
        String my_send_message = et_chat_message.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式，记录用时
        String send_time = df.format(new Date());

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式,显示用时
        String send_time2 = df2.format(new Date());
        System.out.println(send_time);// new Date()为获取当前系统时间

        String request_chat_json = "{\"orderID\":"+ Integer.toString(Data.getOrderID()) + "," + "\"senderID\":" + Integer.toString(Data.getUserID())
                + ",\"receiverID\":" + Integer.toString(Data.getChatID()) + ",\"message\":" + "\"" + my_send_message + "\"" + "}\"";
        System.out.println("我发出请求");
        System.out.println(request_chat_json);
        JSONObject result_json= Post.post("http://121.196.103.2:8080/chat/update", request_chat_json);

        if(result_json == null){
            Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
            et_chat_message.setText(my_send_message);//复原输入框
        }
        else {
            String code = (result_json.getString("code")).toString();
            if (code.equals("200")) {
                //将自己的发送的消息加入数据库中
                db.execSQL("insert into chat values(" + Integer.toString(Data.getOrderID()) + "," + Integer.toString(Data.getUserID()) +
                        "," + Integer.toString(Data.getChatID()) + ",\"" + send_time + "\",\"" + my_send_message + "\")");

                personChat.setChatMessage(my_send_message);
                personChat.setTime(send_time2);

                personChats.add(personChat);//加入message集合
                et_chat_message.setText("");//清空输入框
                handler.sendEmptyMessage(1);
                lv_chat_dialog.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                lv_chat_dialog.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
            } else {
                Toast.makeText(this, "出现错误，无法发送", Toast.LENGTH_SHORT).show();
                et_chat_message.setText(my_send_message);
            }
        }
    }

    public boolean checkMessageInSql(int orderID,int senderID,int receiverID,String send_time,String message){
        Cursor cursor = db.rawQuery("select * from chat where " + "order_id=" + Integer.toString(orderID) + " and sender_id=" + Integer.toString(senderID)
                + " and receiver_id=" + Integer.toString(receiverID) + " and message_time=\"" + send_time + "\"" + " and message_content=\"" + message + "\"" ,null);
        while (cursor.moveToNext()){
            cursor.close();
            return true;
        }
        db.execSQL("insert into chat values(" + Integer.toString(orderID) + "," + Integer.toString(senderID)
                + ","+ Integer.toString(receiverID) + ",\"" + send_time + "\",\"" + message + "\")");
        cursor.close();
        return false;
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
                try {
                    send();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void ChatBack(View v) {
        timer.cancel();
        task.cancel();
        finish();
    }

}
