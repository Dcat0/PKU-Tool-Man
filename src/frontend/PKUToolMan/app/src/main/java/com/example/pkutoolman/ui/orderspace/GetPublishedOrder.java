package com.example.pkutoolman.ui.orderspace;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class GetPublishedOrder {
    static int a=0;
    static public void getOrder(Context context, int userID, ArrayList<Order> publishList) {


        JSONObject obj = Post.post(Data.getBaseURL() + "/order/orderlist", "{\"userID\":" + String.valueOf(userID) + "}");
        System.out.println(obj);
        if (obj == null) {
            Looper.prepare();
            Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT).show();
            Looper.loop();
            return;
        }

        try {
            if (obj.getInt("code") != 200)
                switch (obj.getInt("code")) {
                    case 401:
                        Looper.prepare();
                        Toast.makeText(context, "权限不足", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        return;
                    case 500:
                        Looper.prepare();
                        Toast.makeText(context, "服务端未响应", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        return;
                    default:
                        Looper.prepare();
                        Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        return;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray data = obj.getJSONObject("data").getJSONArray("orders");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject o = data.getJSONObject(i);
                Order order = new Order(o.getInt("orderId"),
                        o.getInt("userId"),
                        o.getInt("toolManId"),
                        o.getString("type"),
                        o.getString("place"),
                        o.getString("destination"),
                        o.getString("startTime"),
                        o.getString("endTime"),
                        o.getString("description"),
                        o.getInt("state")
                );
                publishList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        publishList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.id < o2.id ? 1:-1;
            }
        });
    }
}
