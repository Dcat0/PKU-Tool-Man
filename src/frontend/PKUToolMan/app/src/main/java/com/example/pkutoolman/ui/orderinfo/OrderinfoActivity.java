package com.example.pkutoolman.ui.orderinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import com.example.pkutoolman.ChatActivity;
import com.example.pkutoolman.LoginActivity;
import com.example.pkutoolman.MainActivity;
import com.example.pkutoolman.R;
import com.example.pkutoolman.RegisterActivity;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Post;
import com.example.pkutoolman.ui.myorder.MyorderViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderinfoActivity extends AppCompatActivity {

    private OrderinfoViewModel orderinfoViewModel;
    //控件
    private ImageView btnBack;
    private Button btnChat, btnReport, btnMap;
    //buttons for order operation
    private Button btnOp;
    //信息显示组件
    private TextView userName, orderState;
    private TextView orderID, publishTime, endTime, place, destination, description;

    //信息
    private Order currOrder;
    int currState;
    private int publisherID, receiverID;
    String otherName;
    int otherID, otherCredit;
    private int myRole;     // 0-publisher 1-receiver

    //****后续可能需要在此进行状态保留****
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroyOrderInfo");
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_orderinfo);
        //隐藏标题栏
        getSupportActionBar().hide();
        //获得组件
        btnBack = findViewById(R.id.btn_back);
        btnChat = findViewById(R.id.btn_chat);
        btnReport = findViewById(R.id.btn_report);
        btnMap = findViewById(R.id.btn_map);
        btnOp = findViewById(R.id.btn_order_op);
        userName = findViewById(R.id.user_name);
        orderState = findViewById(R.id.order_state_info);
        orderID = findViewById(R.id.order_id);
        publishTime = findViewById(R.id.publish_time);
        endTime = findViewById(R.id.end_time);
        place = findViewById(R.id.place);
        destination = findViewById(R.id.destination);
        description = findViewById(R.id.description);

        //接受传入的信息
        Intent intent = getIntent();
        int orderID = intent.getIntExtra("orderID", -1);
        System.out.println(orderID);
        //向后端获取order信息
        try{ getOrderInfo(orderID); }
        catch(JSONException ex){
            ex.printStackTrace();
        }
        //向后端获取对方用户信息
        try {
            getOtherInfo(otherID);
        } catch(JSONException ex) {
            ex.printStackTrace();
        }

        //判断当前用户是否有权限查看订单详情
        //订单已被取消
        if (currState == 3){
            Toast toast = Toast.makeText(this, "订单已被取消！", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            //退出界面
            finish();
        }
        //当前用户为接收方，且订单不是未被接收的状态，但用户不是订单的接收者
        else if ((myRole == 1) && (currState != 0) && (Data.getUserID() != receiverID)){
            Toast toast = Toast.makeText(this, "订单已被接收！", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            //退出界面
            finish();
        }

        //……
        //*************************以下仅供演示界面效果**********************************
        /*
        currOrder = new Order(12345678, 0, 1,
                "未名湖底", "博雅塔顶", "摸一条鱼");
        currOrder.state = 1;
        myRole = 0;
        */
        //*************************以上仅供演示效果************************************

        //显示信息
        setOrderState();
        setUserName();
        setOrderInfo();
        //设置用户可进行的订单操作
        setOrderOp();
        //设置响应
        setButtonListener();
    }

    private void getOrderInfo(int id) throws JSONException {
        String request_order_json = "{" + "\"orderID\":"+"\"" + orderID + "\"" + "}";
        System.out.println(request_order_json);
        JSONObject result_json = Post.post("http://121.196.103.2:8080/order/query", request_order_json);
        System.out.println("login_result:");
        //断网
        if(result_json == null){
            Toast.makeText(this, "无网络连接，请重试", Toast.LENGTH_SHORT).show();
            finish();
        }
        System.out.println(result_json.toString());

        //获取状态码与执行信息
        String code = (result_json.getString("code")).toString();
        String message = (result_json.getString("message")).toString();
        //获取订单信息
        JSONObject json_data = result_json.getJSONObject("data");
        JSONObject order_data = json_data.getJSONObject("order");

        publisherID = order_data.getInt("userID");
        receiverID = order_data.getInt("toolmanID");
        String place = order_data.getString("place");
        String destination = order_data.getString("desztination");
        String descripiton = order_data.getString("description");
        int state = order_data.getInt("state");
        String endTime = order_data.getString("endtime");

        currOrder = new Order(id, publisherID, receiverID, place, destination, descripiton);
        //***********待修改
        currState = state;

        //确定当前用户是接收方或发送方
        if (Data.getUserID() == publisherID)
            myRole = 0;
        else myRole = 1;

        //获得对方信息
        //先确定对方的用户ID
        if (myRole == 0){
            if (state == 0 || state == 3)
                otherID = -1;
            else
                otherID = receiverID;
        }
        else
            otherID = publisherID;
    }

    private void getOtherInfo(int id) throws JSONException {
        if (otherID == -1) {
            otherName = "";
            otherCredit = 0;
            return;
        }

        String request_user_json = "{" + "\"userID\":"+"\"" + otherID + "\"" + "}";
        System.out.println(request_user_json);
        JSONObject result_json = Post.post("http://121.196.103.2:8080/user/query", request_user_json);
        System.out.println("login_result:");
        //断网
        if(result_json == null){
            Toast.makeText(this, "无网络连接，请重试", Toast.LENGTH_SHORT).show();
            finish();
        }
        System.out.println(result_json.toString());

        //获取状态码与执行信息
        String code = (result_json.getString("code")).toString();
        String message = (result_json.getString("message")).toString();
        //***********获取失败的处理*******************
        //获取对方信息
        JSONObject json_data = result_json.getJSONObject("data");
        JSONObject user_data = json_data.getJSONObject("user");

        otherName = user_data.getString("nickname");
    }

    private void setOrderState(){
        switch(currOrder.state) {
            case 0:     //未被接受
                orderState.setText(getResources().getString(R.string.order_not_received));
                break;
            case 1:     //进行中
                orderState.setText(getResources().getString(R.string.order_received));
                break;
            case 2:     //已完成
                orderState.setText(getResources().getString(R.string.order_complete));
                break;
            case 3:     //被取消
                orderState.setText(getResources().getString(R.string.order_canceled));
        }
    }

    private void setUserName(){
        userName.setText(otherName);
    }

    private void setOrderInfo(){
        orderID.setText(String.valueOf(currOrder.id));
        publishTime.setText("2020-12-12");              //待实现
        destination.setText(currOrder.destination);
        description.setText(currOrder.description);
        //endTime.setText()
        place.setText(currOrder.place);
    }

    private void setOrderOp(){
        //订单已完成，不进行其余操作
        if (currState == 2){
            btnOp.setVisibility(View.GONE);
            return;
        }
        //否则，若当前订单未被接收，隐藏聊天与举报功能
        if (currState == 0)
            findViewById(R.id.user_op).setVisibility(View.GONE);
        //若当前用户为发布者
        if (myRole == 0){
            //未被接收，设置取消订单
            if (currState == 0)
                btnOp.setText("取消订单");
            //被接收，设置完成订单
            else
                btnOp.setText("完成订单");
        }
        //接收者
        else {
            //未被接收，设置接收功能
            if (currState == 0)
                btnOp.setText("接收订单");
            //被接受，设置取消接收功能
            else
                btnOp.setText("取消接收");
        }
    }

    private void setButtonListener(){
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //返回上一界面
                finish();
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(OrderinfoActivity.this, ChatActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "开启聊天框", Toast.LENGTH_LONG).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到举报界面
                //Navigatin.……
                Toast.makeText(getApplicationContext(), "进入举报", Toast.LENGTH_LONG).show();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到地图界面
                //Navigation.……
                Toast.makeText(getApplicationContext(), "打开地图", Toast.LENGTH_LONG).show();
            }
        });

        btnOp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (currOrder.state == 0) {
                    //取消订单
                    if (myRole == 0) {
                        //向后端发送取消请求
                        Toast.makeText(getApplicationContext(), "取消订单", Toast.LENGTH_LONG).show();
                    }
                    //接收订单
                    else {
                        //向后端发送接收请求
                        Toast.makeText(getApplicationContext(), "接收订单", Toast.LENGTH_LONG).show();
                    }
                }
                else if (currState == 1){
                    //完成订单
                    if (myRole == 0){
                        Toast.makeText(getApplicationContext(), "完成订单", Toast.LENGTH_LONG).show();
                    }
                    //取消接收
                    else {
                        Toast.makeText(getApplicationContext(), "取消接收 ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}