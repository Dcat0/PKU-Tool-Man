package com.example.pkutoolman.ui.myorder;

import android.content.Context;
import android.util.JsonReader;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;

public class GetMyOrder {
    static int a=0;
    static public void getMyOrder(Context context, int userID, ArrayList<Order> publishList, ArrayList<Order> receiveList) {
        // 测试内容
        /*a++;
        System.out.printf("a=%d\n", a);


        for (int i=0; i<10; ++i) {
            publishList.add(new Order(123, 321, 123, "45乙", "45", "123"));
            receiveList.add(new Order(123, 321, 123, "45乙", "45", "123"));
        }*/


        //ArrayList<Order> orderList = new ArrayList<>();
        JSONObject obj = Post.post("http://121.196.103.2:8080/order/myorderlist", "{\"userID\":"+String.valueOf(userID)+"}");
        System.out.println(obj);
        if (obj == null) {
            Toast.makeText(context, "网络连接出错", Toast.LENGTH_SHORT);
            return;
        }

        try {
            if (obj.getInt("code") != 200)
            switch (obj.getInt("code")) {
                case 401:
                    Toast.makeText(context, "权限不足", Toast.LENGTH_SHORT).show();
                    return;
                case 500:
                    Toast.makeText(context, "服务端未响应", Toast.LENGTH_SHORT).show();
                    return;
                default:
                    Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
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
                        o.getString("place"),
                        o.getString("destination"),
                        o.getString("startTime"),
                        o.getString("endTime"),
                        o.getString("description"),
                        o.getInt("state"),
                        o.getString("type")
                );
                if (order.toolmanID == userID) receiveList.add(order);
                if (order.userID == userID) publishList.add(order);
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
        receiveList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.id < o2.id ? 1:-1;
            }
        });

    }



}
