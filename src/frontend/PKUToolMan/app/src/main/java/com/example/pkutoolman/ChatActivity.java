package com.example.pkutoolman;

import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends Activity {
    int mCounter =0;
    int mTime =1000;

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
                    chatAdapter.notifyDataSetChanged();
                    lv_chat_dialog.setSelection(personChats.size());
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
            int orderID = Order.id;
            System.out.println("time");
            String request_chat_json = "{\"orderID\":"+ String.valueOf(1) + "}\"";
            JSONObject result_json= Post.post("http://121.196.103.2:8080/chat/query", request_chat_json);
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

                    boolean isInSql = check_message_in_sql(orderID,senderID,receiverID,send_time,message);
                    if (!isInSql && senderID != Data.getUserID()) {
                        boolean my_send = false;
                        ChatData personChat = new ChatData();
                        personChat.setChatMessage(message);
                        personChat.setMeSend(false);
                        personChat.setTime(send_time2);
                        personChats.add(personChat);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_chat);
        /**
         * 虚拟4条发送方的消息
         */
        long delay = 3000;
        long intevalPeriod = 20000;

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
        ChatDatabase_sqlite chat_content = new ChatDatabase_sqlite(ChatActivity.this);
        SQLiteDatabase db = chat_content.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from chat", null);

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
        int other_id = 1;

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

        String request_chat_json = "{\"orderID\":"+ Integer.toString(1) + "," + "\"senderID\":" + Integer.toString(Data.getUserID())
                + ",\"receiverID\":" + Integer.toString(other_id) + ",\"message\":" + "\"" + my_send_message + "\"" + "}\"";
        System.out.println("我发出请求");
        System.out.println(request_chat_json);
        JSONObject result_json= Post.post("http://121.196.103.2:8080/chat/query", request_chat_json);

        if(result_json == null){
            Toast.makeText(this, "网络未连接", Toast.LENGTH_SHORT).show();
            et_chat_message.setText(my_send_message);//清空输入框
        }
        else {
            String code = (result_json.getString("code")).toString();
            if (code.equals("200")) {
                //将自己的发送的消息加入数据库中
                ChatDatabase_sqlite chat_content = new ChatDatabase_sqlite(ChatActivity.this);
                SQLiteDatabase db = chat_content.getWritableDatabase();
                db.execSQL("insert into chat values(" + Integer.toString(other_id) + "," + Integer.toString(my_id) +
                        ",1,\"" + send_time + "\",\"" + my_send_message + "\")");

                personChat.setChatMessage(my_send_message);
                personChat.setTime(send_time2);

                personChats.add(personChat);//加入message集合
                et_chat_message.setText("");//清空输入框
                handler.sendEmptyMessage(1);
            } else {
                Toast.makeText(this, "出现错误，无法发送", Toast.LENGTH_SHORT).show();
                et_chat_message.setText(my_send_message);
            }
        }
    }

    public boolean check_message_in_sql(int orderID,int senderID,int receiverID,String send_time,String message){
        ChatDatabase_sqlite chat_content = new ChatDatabase_sqlite(ChatActivity.this);
        SQLiteDatabase db = chat_content.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from chat where " + "order_id=" + Integer.toString(orderID) + " and sender_id=" + Integer.toString(senderID)
                + " and receiver_id=" + Integer.toString(receiverID) + " and message_content=\"" + message + "\" and message_time=\"" + send_time + "\"",null);
        while (cursor.moveToNext()){
            return true;
        }
        db.execSQL("insert into chat values(" + Integer.toString(orderID) + "," + Integer.toString(senderID)
                + ","+ Integer.toString(receiverID) + ",\"" + send_time + "\",\"" + message + "\")");
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

}
