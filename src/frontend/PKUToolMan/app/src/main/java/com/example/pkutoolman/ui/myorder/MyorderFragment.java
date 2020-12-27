package com.example.pkutoolman.ui.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pkutoolman.R;
import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;
import com.example.pkutoolman.ui.orderinfo.OrderinfoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyorderFragment extends Fragment {

    private MyorderViewModel myorderViewModel;
    private ListView mLv;
    private TextView hint;
    private Button bt1, bt2;
    private Spinner sn1, sn2;
    private SimpleAdapter saPublish, saReceive;
    //private SwipeRefreshLayout mSrl;
    private CopyOnWriteArrayList<Map<String, Object>> messageListPublish = new CopyOnWriteArrayList<>(), messageListReceive = new CopyOnWriteArrayList<>();
    volatile private String nowView, selectType;
    private static String[] _selectType = {"全部", "取快递", "购物", "带饭"};
    volatile private int selectStatus;
    private ArrayList<Order> publishOrderList = new ArrayList<>(), receiveOrderList = new ArrayList<>();
    private ArrayAdapter sn1Adp;
    private ArrayAdapter sn2AdpPub;
    private ArrayAdapter sn2AdpRec;
    private FloatingActionButton freshButton;
    private int jumpToOrderId = -1;

    private Timer timer = null;
    private TimerTask task = null;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    Bundle bundle = msg.getData();
                    updateOrderMessage(bundle.getInt("id"), bundle.getBoolean("changed"));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void startTask() {
        task = new TimerTask() {
            public void run() {
                CopyOnWriteArrayList<Map<String, Object>> test;
                boolean changed = false;
                //查询所有当前显示的订单中是否有消息记录变化的
                if (nowView == "publish") test = messageListPublish;
                else test = messageListReceive;
                for (Map<String, Object> m : test) {
                    boolean bool = getNewMessage((int) m.get("uid"), Data.getUserID());
                    if (bool != (m.get("message") == null ? false : true)) { //如果消息状态发生了变化
                        Message message = new Message();
                        message.what = 5;
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", (int) m.get("uid"));
                        bundle.putBoolean("changed", bool);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            }
        };
    }
    private void stopTask() {
        task.cancel();
    }
    private void startTimer() {
        timer = new Timer();
    }
    private void stopTimer() {
        timer.cancel();
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
        stopTimer();
        stopTask();
    }

    @Override
    public void onDestroyView() {
        System.out.println("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        System.out.println("Resume");
        super.onResume();
        if (jumpToOrderId != -1) { //确保是从我的订单详情返回的
            System.out.println("return from orderInfo");
            // 返回之后看一下订单消息是否发生变化
            refresh(true);
            updateOrderMessage(jumpToOrderId, getNewMessage(jumpToOrderId, Data.getUserID()));
            startTimer();
            startTask();
            timer.schedule(task, 30000, 30000);
        }
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
        freshButton = root.findViewById(R.id.myorder_refresh_button);
        sn1 = root.findViewById(R.id.order_type_selector);
        sn2 = root.findViewById(R.id.order_status_selector);
        hint = root.findViewById(R.id.hint_no_order);

        sn1Adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"全部", "取快递", "购物", "带饭"});
        sn2AdpPub = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"全部", "未被接收", "已被接受", "已完成", "已取消"});
        sn2AdpRec = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"全部", "正在进行", "已完成"});


        nowView = "publish";
        refresh(true); //建立视图的时候刷新数据 true表示需要从后端拉取数据

        mLv.setAdapter(saPublish);
        sn1.setAdapter(sn1Adp);
        sn2.setAdapter(sn2AdpPub);

        sn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = _selectType[position];
                refresh(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectStatus = position - (nowView=="publish"?1:0);
                refresh(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(true);
                mSrl.setRefreshing(false);
            }
        });*/
        freshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("refresh");
                refresh(true);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowView == "receive") {
                    nowView = "publish";
                    mLv.setAdapter(saPublish);
                    sn1.setAdapter(sn1Adp);
                    sn2.setAdapter(sn2AdpPub);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (nowView == "publish") {
                    nowView = "receive";
                    mLv.setAdapter(saReceive);
                    sn1.setAdapter(sn1Adp);
                    sn2.setAdapter(sn2AdpRec);
                }
            }
        });


        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id)
            {
                System.out.println(position);
                //记录跳转的时候的Id
                if (nowView == "publish")
                    jumpToOrderId = Integer.valueOf( ((TextView)view.findViewById(R.id.publish_order_uid)).getText().toString());
                    else jumpToOrderId = Integer.valueOf( ((TextView)view.findViewById(R.id.receive_order_uid)).getText().toString());
                // 每个Item跳转的时候需要用Navigate,并通过Buddle向orderInfo的Fragment中传递信息
                Intent intent = new Intent();
                intent.putExtra("orderID", jumpToOrderId);
                intent.setClass(getActivity(), OrderinfoActivity.class);
                System.out.println("tack cancel");
                timer.cancel();
                task.cancel();
                startActivity(intent);
            }
        });
        startTimer();
        startTask();
        timer.schedule(task, 10000, 30000);
        return root;
    }

    public boolean getNewMessage(int orderID, int userID) {
        String jsonSend = "{\"orderID\":" + String.valueOf(orderID) + ",\"userID\":" + String.valueOf(userID) + "}";
        System.out.println("queryNewMessage");
        System.out.println(jsonSend);
        JSONObject obj = Post.post("http://121.196.103.2:8080/chat/check", jsonSend);
        System.out.println(obj);
        if (obj == null) {
            Toast.makeText(getContext(), "网络连接出错", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            if (obj.getInt("code") != 200)
                switch (obj.getInt("code")) {
                    case 401:
                        Toast.makeText(getContext(), "权限不足", Toast.LENGTH_SHORT).show();
                        return false;
                    case 500:
                        Toast.makeText(getContext(), "服务端未响应", Toast.LENGTH_SHORT).show();
                        return false;
                    default:
                        Toast.makeText(getContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        return false;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            boolean bool = obj.getJSONObject("data").getBoolean("checkresult");
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void refresh(boolean get) {


        //刷新数据信息 如果get=true要从后端拉去
        //此处的数据信息要根据两个下拉框的内容来筛选
        if (get) {
            publishOrderList.clear();
            receiveOrderList.clear();
            GetMyOrder.getMyOrder(getContext(), Data.getUserID(), publishOrderList, receiveOrderList);
        }

        messageListPublish.clear();
        messageListReceive.clear();
        // 准备放到页面中
        if (nowView == "publish") {
            for (Order o : publishOrderList)
            if (("取快递" == selectType || selectType == "全部") && (selectStatus == -1 || selectStatus == o.state))
            {
                Map<String, Object> m = new HashMap<>();
                m.put("uid", o.id);
                m.put("ddtime", o.endTime);
                m.put("class", o.type);
                m.put("start", o.place);
                m.put("dest", o.destination);
                //if (getNewMessage(o.id, o.userID)) m.put("message", R.drawable.ic_chat_red);
                //    else m.put("message", null);
                if (o.state == 0) { //未被接受
                    m.put("state", "未被接收");
                    m.put("img", R.drawable.baseline_update_black_24dp);
                } else if (o.state == 1) { //已完成
                    m.put("state", "已被接受");
                    m.put("img", R.drawable.baseline_history_green_a700_24dp);
                } else if (o.state == 2) { //已被接收
                    m.put("state", "已完成");
                    m.put("img", R.drawable.baseline_check_circle_green_700_24dp);
                } else // 已取消
                {
                    m.put("state", "已取消");
                    m.put("img", R.drawable.baseline_https_red_700_24dp);
                }
                messageListPublish.add(m);
            }
            if (messageListPublish.size() == 0) hint.setVisibility(View.VISIBLE);
              else hint.setVisibility(View.GONE);
        }
        if (nowView == "receive") {
            for (Order o : receiveOrderList)
            if (("取快递" == selectType || selectType == "全部") && (selectStatus == 0 || selectStatus == o.state))
            {
                Map<String, Object> m = new HashMap<>();
                m.put("uid", o.id);
                m.put("ddtime", o.endTime);
                m.put("class", o.type);
                System.out.println(o.id);
                System.out.println(o.type);
                m.put("name", o.userID);
                m.put("start", o.place);
                m.put("dest", o.destination);
                //if (getNewMessage(o.id, o.toolmanID)) m.put("message", R.drawable.ic_chat_red);
                //    else m.put("message", null);
                if (o.state == 2) { //已完成
                    m.put("state", "已完成");
                    m.put("img", R.drawable.baseline_check_circle_green_700_24dp);
                } else if (o.state == 1) { //已被接收
                    m.put("state", "正在进行");
                    m.put("img", R.drawable.baseline_history_green_a700_24dp);
                }
                messageListReceive.add(m);
            }
            if (messageListReceive.size() == 0) hint.setVisibility(View.VISIBLE);
              else hint.setVisibility(View.GONE);
        }
        //  第一次进入refresh()就是切换到我的订单界面 此时saPublish和saReceive都为空 需要初始化

        if (saPublish == null)
            saPublish = new SimpleAdapter(getContext(),
                messageListPublish,
                R.layout.myorder_published,
                new String[] {"uid", "img", "state", "ddtime", "class", "start", "dest", "message"},
                new int[] {R.id.publish_order_uid, R.id.publish_order_ztimg, R.id.publish_order_state,
                        R.id.publish_order_ddtime, R.id.publish_order_class, R.id.publish_order_start, R.id.publish_order_dest, R.id.publish_order_message}
        );

        if (saReceive == null)
            saReceive = new SimpleAdapter(getContext(),
                messageListReceive,
                R.layout.myorder_received,
                new String[] {"uid", "name", "img", "state", "ddtime", "class", "start", "dest", "message"},
                new int[] {R.id.receive_order_uid, R.id.receive_order_name, R.id.receive_order_ztimg, R.id.receive_order_state,
                        R.id.receive_order_ddtime, R.id.receive_order_class, R.id.receive_order_start, R.id.receive_order_dest, R.id.receive_order_message}
        );

        if (nowView == "receive") {
            saReceive.notifyDataSetChanged();
        } else {
            saPublish.notifyDataSetChanged();
        }

    }

    private void updateOrderMessage(int orderId, boolean newMessageStatus) {
        CopyOnWriteArrayList<Map<String, Object>> test;
        if (nowView == "publish") test = messageListPublish;else test=messageListReceive;
        for (Map<String, Object> m : test)
            if ((int)m.get("uid") == orderId)
                m.put("message", newMessageStatus==false?null:R.drawable.ic_chat_red);
        if (nowView == "publish") saPublish.notifyDataSetChanged(); else saReceive.notifyDataSetChanged();
    }
}