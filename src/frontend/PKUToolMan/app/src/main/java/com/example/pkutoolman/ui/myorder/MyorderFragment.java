package com.example.pkutoolman.ui.myorder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.net.URLConnection;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyorderFragment extends Fragment {

    private MyorderViewModel myorderViewModel;
    private ListView mLv;
    private Button bt1,bt2;
    private SimpleAdapter saPublish, saReceive;
    private SwipeRefreshLayout mSrl;
    private ArrayList<Map<String, Object>> messageListPublish = new ArrayList<>(), messageListReceive = new ArrayList<>();

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("on create view");
        myorderViewModel = new ViewModelProvider(this).get(MyorderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myorder, container, false);
        mLv = root.findViewById(R.id.lv_myorder);
        bt1 = root.findViewById(R.id.bt_myorder_publish);
        bt2 = root.findViewById(R.id.bt_myorder_receive);
        //mSrl = root.findViewById(R.id.myorder_swipeLayout);

        refresh(); //建立视图的时候刷新数据

        mLv.setAdapter(saPublish);

       /* mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                mSrl.setRefreshing(false);
            }
        });*/

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLv.setAdapter(saPublish);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLv.setAdapter(saReceive);
            }
        });


        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id)
            {
                System.out.println(position);
                // 每个Item跳转的时候需要用Navigate,并通过Buddle向orderInfo的Fragment中传递信息
                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    public void refresh() {

        //刷新数据信息
        // 异步获取数据 分为两个部分订单
        ArrayList<Order> publishOrderList = new ArrayList<>(), receiveOrderList = new ArrayList<>();
        GetMyOrder.getMyOrder(123, publishOrderList, receiveOrderList);
        messageListPublish.clear();
        messageListReceive.clear();
        // 准备放到页面中
        for (Order o : publishOrderList) {
            Map<String, Object> m = new HashMap<>();
            m.put("uid", o.id);
            m.put("ddtime", "2020-12-14");
            m.put("class", "拿快递");
            if (o.state == 0) { //未被接受
                m.put("state", "未被接收");
                m.put("img", R.drawable.baseline_update_black_24dp);
            } else if (o.state == 1) { //已完成
                m.put("state", "已完成");
                m.put("img", R.drawable.baseline_check_circle_green_700_24dp);
            } else { //已被接收
                m.put("state", "已被接受");
                m.put("img", R.drawable.baseline_history_green_a700_24dp);
            }
            messageListPublish.add(m);
        }
        for (Order o : receiveOrderList) {
            Map<String, Object> m = new HashMap<>();
            m.put("uid", o.id);
            m.put("ddtime", "2020-12-14");
            m.put("class", "拿快递");
            m.put("name", o.userID);
            if (o.state == 0) { //未被接受
                m.put("state", "不可能");
                m.put("img", R.drawable.baseline_update_black_24dp);
            } else if (o.state == 1) { //已完成
                m.put("state", "已完成");
                m.put("img", R.drawable.baseline_check_circle_green_700_24dp);
            } else { //已被接收
                m.put("state", "正在进行");
                m.put("img", R.drawable.baseline_history_green_a700_24dp);
            }
            messageListReceive.add(m);
        }
        // 获取数据后加载到SimpleAdapter中 第一次进入是CreateView中 以后的调用是下拉刷新

        if (saPublish == null)
            saPublish = new SimpleAdapter(getContext(),
                messageListPublish,
                R.layout.myorder_published,
                new String[] {"uid", "img", "state", "ddtime", "class"},
                new int[] {R.id.publish_order_uid, R.id.publish_order_ztimg, R.id.publish_order_state,
                        R.id.publish_order_ddtime, R.id.publish_order_class}
        );

        if (saReceive == null)
            saReceive = new SimpleAdapter(getContext(),
                messageListReceive,
                R.layout.myorder_received,
                new String[] {"uid", "name", "img", "state", "ddtime", "class"},
                new int[] {R.id.receive_order_uid, R.id.receive_order_name, R.id.receive_order_ztimg, R.id.receive_order_state,
                        R.id.receive_order_ddtime, R.id.publish_order_class}
        );

        saReceive.notifyDataSetChanged();
        saPublish.notifyDataSetChanged();

    }


}