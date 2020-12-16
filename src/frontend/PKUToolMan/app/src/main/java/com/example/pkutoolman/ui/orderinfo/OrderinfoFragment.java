package com.example.pkutoolman.ui.orderinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.ui.myorder.MyorderViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderinfoFragment extends Fragment {

    private OrderinfoViewModel orderinfoViewModel;
    //控件
    private ImageView btnBack;
    private Button btnChat, btnReport, btnMap;
    //buttons for order operation
    private Button btnOp1, btnOp2;
    //信息显示组件
    private TextView userName, orderState;
    private TextView orderID, publishTime, endTime, place, destination, description;

    //信息
    private Order currOrder;
    private Data publisher, receiver;
    private int myRole;     // 0-publisher 1-receiver

    //****后续可能需要在此进行状态保留****
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroyOrderInfo");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyOrderInfoView");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        System.out.println("on create view");
        orderinfoViewModel = new ViewModelProvider(this).get(OrderinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orderinfo, container, false);
        //获得组件
        btnBack = root.findViewById(R.id.btn_back);
        btnChat = root.findViewById(R.id.btn_chat);
        btnReport = root.findViewById(R.id.btn_report);
        btnMap = root.findViewById(R.id.btn_map);
        btnOp1 = root.findViewById(R.id.btn_order_op1);
        btnOp2 = root.findViewById(R.id.btn_order_op2);
        userName = root.findViewById(R.id.user_name);
        orderState = root.findViewById(R.id.order_state_info);
        orderID = root.findViewById(R.id.order_id);
        publishTime = root.findViewById(R.id.publish_time);
        endTime = root.findViewById(R.id.end_time);
        place = root.findViewById(R.id.place);
        destination = root.findViewById(R.id.destination);
        description = root.findViewById(R.id.description);

        //接受传入的信息
        //Bundle bun = getArguments();
        //currOrder = bun.get("……");
        //……
        //*************************以下仅供演示界面效果**********************************
        currOrder = new Order(12345678, 0, 1,
                "未名湖底", "博雅塔顶", "摸一条鱼");
        currOrder.state = 1;
        myRole = 0;
        //*************************以上仅供演示效果************************************

        //显示信息
        setOrderState();
        setUserName();
        setOrderInfo();
        //设置用户可进行的订单操作
        setOrderOp();
        //设置响应
        setButtonListener();

        return root;
    }

    private void setOrderState(){
        switch(currOrder.state) {
            case 0:     //未被接受
                orderState.setText(getResources().getString(R.string.order_not_received));
                break;
            case 1:     //已完成
                orderState.setText(getResources().getString(R.string.order_complete));
                break;
            case 2:     //进行中
                orderState.setText(getResources().getString(R.string.order_received));
                break;
        }
    }

    private void setUserName(){
        int currState = currOrder.state;
        //未被接受
        if (currState == 0){
            //“我”是发布者
            if (myRole == 0)
                userName.setText("");
            else
                userName.setText(publisher.getNickName());
        }
        else{
            String name = (myRole == 0) ? receiver.getNickName() : publisher.getNickName();
            userName.setText(name);
        }
    }

    private void setOrderInfo(){
        orderID.setText(String.valueOf(currOrder.id));
        publishTime.setText("2020-12-12");              //待实现
        destination.setText(currOrder.destination);
        description.setText(currOrder.description);

        //未被接受
        if (currOrder.state == 0){
            endTime.setText("");
            place.setText("");
        }
        //已完成
        else if (currOrder.state == 1){
            endTime.setText("2020-12-21");
            place.setText(currOrder.place);
        }
        else{
            endTime.setText("");
            place.setText(currOrder.place);
        }
    }

    private void setOrderOp(){
        //订单已完成，不进行其余操作
        if (currOrder.state == 1){
            btnOp1.setVisibility(View.GONE);
            btnOp2.setVisibility(View.GONE);
            return;
        }
        //否则，若当前用户为发布者
        if (myRole == 0) {
            btnOp1.setText("取消订单");
            btnOp2.setText("完成订单");
            return;
        }
        //当前用户为接收者
        //订单未被接收
        if (currOrder.state == 0){
            btnOp1.setVisibility(View.GONE);
            btnOp2.setText("接收订单");
        }
        //订单进行中
        else{
            btnOp1.setText("取消接收");
            btnOp2.setVisibility(View.GONE);
        }
    }

    private void setButtonListener(){
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //返回上一界面
                Navigation.findNavController(v).navigateUp();
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到聊天界面
                //Navigation.……
                Toast.makeText(getContext(), "开启聊天框", Toast.LENGTH_LONG).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到举报界面
                //Navigatin.……
                Toast.makeText(getContext(), "进入举报", Toast.LENGTH_LONG).show();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到地图界面
                //Navigation.……
                Toast.makeText(getContext(), "打开地图", Toast.LENGTH_LONG).show();
            }
        });

        btnOp1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (currOrder.state != 1) {
                    //取消订单
                    if (myRole == 0) {
                        //向后端发送取消请求
                        Toast.makeText(getContext(), "取消订单", Toast.LENGTH_LONG).show();
                    }
                    //取消接收
                    else if (currOrder.state == 2) {
                        //向后端发送取消接收请求
                        Toast.makeText(getContext(), "取消接收", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnOp2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (currOrder.state != 1) {
                    //完成订单
                    if (myRole == 0) {
                        //向后端发送完成请求
                        Toast.makeText(getContext(), "完成订单", Toast.LENGTH_LONG).show();
                    }
                    //接收订单
                    else if (currOrder.state == 0) {
                        //向后端发送接收请求
                        Toast.makeText(getContext(), "接收订单", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}